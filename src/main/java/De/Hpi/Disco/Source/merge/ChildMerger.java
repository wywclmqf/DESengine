package De.Hpi.Disco.Source.merge;

import De.Hpi.Disco.Source.aggregation.*;
import De.Hpi.Disco.Source.utility.DistributedChildSlicer;
import De.Hpi.Disco.Source.utility.DistributedUtils;
import De.Hpi.Disco.Source.utility.Event;
import de.tub.dima.scotty.core.AggregateWindow;
import de.tub.dima.scotty.core.WindowAggregateId;
import de.tub.dima.scotty.core.windowFunction.AggregateFunction;
import de.tub.dima.scotty.core.windowType.SessionWindow;
import de.tub.dima.scotty.core.windowType.Window;
import de.tub.dima.scotty.slicing.slice.Slice;
import de.tub.dima.scotty.slicing.state.AggregateState;
import de.tub.dima.scotty.slicing.state.AggregateWindowState;
import de.tub.dima.scotty.state.memory.MemoryStateFactory;

import java.util.*;
import java.util.stream.Collectors;

public class ChildMerger {
    private final int childId;
    private final Map<Integer, DistributedChildSlicer<Double>> slicerPerKey;
    private final Map<Long, Map<Integer, Long>> sessionLastTimestamps;
    private final Map<Long, Long> sessionGaps;
    private final Map<WindowFunctionKey, List<FunctionWindowAggregateId>> newSessionStarts;
    private final LocalHolisticWindowMerger localHolisticWindowMerger;

    private final List<AggregateFunction> sliceAggFns;
    private final List<Window> windows;

    private long lastWatermark;

    public ChildMerger(List<Window> timedWindows, List<AggregateFunction> functions, int childId) {
        this(new HashMap<>(), timedWindows, functions, childId);
    }

    public ChildMerger(Map<Integer, DistributedChildSlicer<Double>> slicerPerKey,
            List<Window> timedWindows, List<AggregateFunction> functions, int childId) {
        this.childId = childId;
        this.slicerPerKey = slicerPerKey;
        this.windows = timedWindows;

        int numStreams = slicerPerKey.size();
        this.sliceAggFns = functions.stream()
                .map(aggFn -> aggFn instanceof HolisticAggregateFunction ? new HolisticAggregateHelper<>() : aggFn)
                .collect(Collectors.toList());

        this.localHolisticWindowMerger = new LocalHolisticWindowMerger(numStreams, timedWindows);

        this.sessionLastTimestamps = new HashMap<>();
        this.newSessionStarts = new HashMap<>();
        this.sessionGaps = new HashMap<>();
        for (Window window : windows) {
            if (window instanceof SessionWindow) {
                long windowId = window.getWindowId();
                sessionGaps.put(windowId, ((SessionWindow) window).getGap());
                sessionLastTimestamps.put(windowId, new HashMap<>());
            }
        }
    }

    public void processElement(Double eventValue, long eventTimestamp, int key) {
//        System.out.println(childId + " - PROCESSING: " + eventTimestamp);
        DistributedChildSlicer<Double> perKeySlicer = this.slicerPerKey.computeIfAbsent(key,
                k -> new DistributedChildSlicer<>(this.windows, this.sliceAggFns));
        perKeySlicer.processElement(eventValue, eventTimestamp);

        if (sessionGaps.isEmpty()) {
            // There are no session windows, so we don't care about session starts
            return;
        }

        for (Map.Entry<Long, Map<Integer, Long>> lastTimestamps : sessionLastTimestamps.entrySet()) {
            final long windowId = lastTimestamps.getKey();
            Map<Integer, Long> keyedSessionEnds = lastTimestamps.getValue();

            final long sessionGap = sessionGaps.get(windowId);
            final long lastEvent = keyedSessionEnds.getOrDefault(key, -1L);

            if (lastEvent == -1L || lastEvent + sessionGap < eventTimestamp) {
                WindowAggregateId windowAggId = new WindowAggregateId(windowId, eventTimestamp, eventTimestamp);
                FunctionWindowAggregateId sessionStartId = new FunctionWindowAggregateId(windowAggId, 0, childId, key);
                WindowFunctionKey windowKey = new WindowFunctionKey(windowId, key);
                newSessionStarts.computeIfAbsent(windowKey, id -> new ArrayList<>()).add(sessionStartId);
//                System.out.println(childId + " - NEW SESSION: " + eventTimestamp);
            }
            keyedSessionEnds.put(key, eventTimestamp);
        }
    }

    public void processElement(Event event) {
        processElement(event.getValue(), event.getTimestamp(), event.getKey());
    }

    public List<DistributedAggregateWindowState> processWatermarkedWindows(long watermarkTimestamp) {
        List<DistributedAggregateWindowState> resultWindows = this.slicerPerKey.entrySet().stream()
                // Guarantee order of processing
                .sorted(Comparator.comparingInt(Map.Entry::getKey))
                .flatMap(slicerWithKey -> {
                    final int key = slicerWithKey.getKey();
                    DistributedChildSlicer<Double> slicer = slicerWithKey.getValue();
                    List<AggregateWindow> preAggregatedWindows = slicer.processWatermark(watermarkTimestamp);
                    List<DistributedAggregateWindowState> finalWindows =
                            this.finalizeStreamWindows(preAggregatedWindows, key);
                    return finalWindows.stream();
                }).collect(Collectors.toList());

        this.lastWatermark = watermarkTimestamp;
        return resultWindows;
    }

    private List<DistributedAggregateWindowState> finalizeStreamWindows(List<AggregateWindow> preAggregatedWindows, int key) {
        List<DistributedAggregateWindowState> finalPreAggregateWindows = new ArrayList<>(preAggregatedWindows.size());

        preAggregatedWindows.sort(Comparator.comparingLong(AggregateWindow::getStart));
        for (AggregateWindow preAggWindow : preAggregatedWindows) {
            List<DistributedAggregateWindowState> aggregateWindows = convertPreAggWindow((AggregateWindowState) preAggWindow, key);
            finalPreAggregateWindows.addAll(aggregateWindows);
        }

        return finalPreAggregateWindows;
    }

    private List<DistributedAggregateWindowState> convertPreAggWindow(AggregateWindowState preAggWindow, int key) {
        List<DistributedAggregateWindowState> finalPreAggregateWindows = new ArrayList<>();

        WindowAggregateId windowId = preAggWindow.getWindowAggregateId();
        if (windowId.getWindowEndTimestamp() <= this.lastWatermark) {
            // Can be ignored, window was triggered already
            return new ArrayList<>();
        }

        List<AggregateFunction> aggregateFunctions = preAggWindow.getAggregateFunctions();
        final List aggValues = preAggWindow.getAggValues();

        for (int functionId = 0; functionId < aggregateFunctions.size(); functionId++) {
            final AggregateFunction aggregateFunction = aggregateFunctions.get(functionId);
            final boolean hasValue = functionId < aggValues.size();
            FunctionWindowAggregateId functionWindowId =
                    new FunctionWindowAggregateId(windowId, functionId, this.childId, key);

            Map<Integer, Long> keyedSessionEnds = sessionLastTimestamps.get(windowId.getWindowId());
            if (keyedSessionEnds != null) {
                // Is session window, so set correct end timestamp
                keyedSessionEnds.put(key, windowId.getWindowEndTimestamp());
            }

            DistributedAggregateWindowState finalPreAggregateWindow;
            List<AggregateFunction> stateAggFn =
                    DistributedUtils.convertAggregateFunctions(Collections.singletonList(aggregateFunction));
            if (aggregateFunction instanceof DistributiveAggregateFunction) {
                AggregateState<Double> aggState = new AggregateState<>(new MemoryStateFactory(), stateAggFn);
                Double partialAggregate = hasValue ? (Double) aggValues.get(functionId) : null;
                aggState.addElement(partialAggregate);
                finalPreAggregateWindow = new DistributedAggregateWindowState<>(functionWindowId, aggState);
            } else if (aggregateFunction instanceof AlgebraicAggregateFunction) {
                AggregateState<AlgebraicPartial> aggState = new AggregateState<>(new MemoryStateFactory(), stateAggFn);
                AlgebraicPartial partial = hasValue ? (AlgebraicPartial) aggValues.get(functionId) : null;
                aggState.addElement(partial);
                finalPreAggregateWindow = new DistributedAggregateWindowState<>(functionWindowId, aggState);
            } else if (aggregateFunction instanceof HolisticAggregateHelper) {
                List<Slice> slices = preAggWindow.getSlices();
                this.localHolisticWindowMerger.processPreAggregate(slices, functionWindowId);
                finalPreAggregateWindow = this.localHolisticWindowMerger.triggerFinalWindow(functionWindowId).get(0);
            } else {
                throw new RuntimeException("Unsupported aggregate function: " + aggregateFunction);
            }

            finalPreAggregateWindows.add(finalPreAggregateWindow);
        }

        return finalPreAggregateWindows;
    }

    public Optional<FunctionWindowAggregateId> getNextSessionStart(FunctionWindowAggregateId lastSession) {
        long windowId = lastSession.getWindowId().getWindowId();
        if (sessionGaps.isEmpty() || !sessionGaps.containsKey(windowId)) {
            // There are no session windows or this isn't a session, so we don't care about session starts.
            return Optional.empty();
        }

        WindowFunctionKey windowKey = new WindowFunctionKey(windowId, lastSession.getKey());
        long lastSessionEnd = lastSession.getWindowId().getWindowEndTimestamp();

        List<FunctionWindowAggregateId> sessionStarts = newSessionStarts.get(windowKey);
        return DistributedUtils.getNextSessionStart(sessionStarts, lastSessionEnd);
    }
}
