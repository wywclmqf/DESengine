package De.Hpi.Disco.Source.merge;

import De.Hpi.Disco.Source.aggregation.*;
import de.tub.dima.scotty.core.AggregateWindow;
import de.tub.dima.scotty.core.WindowAggregateId;
import de.tub.dima.scotty.core.windowFunction.AggregateFunction;
import de.tub.dima.scotty.core.windowType.Window;
import de.tub.dima.scotty.slicing.slice.EagerSlice;
import de.tub.dima.scotty.slicing.state.AggregateState;
import de.tub.dima.scotty.state.StateFactory;
import de.tub.dima.scotty.state.memory.MemoryStateFactory;

import java.util.*;

public class GlobalHolisticWindowMerger extends BaseWindowMerger<List<DistributedSlice>> {
    private final StateFactory stateFactory;
    private final Map<ChildKey, List<DistributedSlice>> childSlices;
    private final List<AggregateFunction> aggFns;

    public GlobalHolisticWindowMerger(int numChildren, List<Window> windows, List<AggregateFunction> aggFunctions) {
        super(numChildren, windows, aggFunctions);
        this.aggFns = aggFunctions;
        this.stateFactory = new MemoryStateFactory();
        this.childSlices = new HashMap<>();
    }

    @Override
    public void processPreAggregate(List<DistributedSlice> preAggregate, FunctionWindowAggregateId functionWindowAggId) {
        if (this.isSessionWindow(functionWindowAggId)) {
            processGlobalSession(preAggregate, functionWindowAggId);
            return;
        }

        ChildKey childKey = ChildKey.fromFunctionWindowId(functionWindowAggId);
        childSlices.putIfAbsent(childKey, new ArrayList<>());
        List<DistributedSlice> childSlicesPerKey = this.childSlices.get(childKey);
        childSlicesPerKey.addAll(preAggregate);
    }

    @Override
    public List<DistributedAggregateWindowState<List<DistributedSlice>>> triggerFinalWindow(FunctionWindowAggregateId functionWindowId) {
        List<DistributedAggregateWindowState<List<DistributedSlice>>> resultWindows = new ArrayList<>();

        if (this.isSessionWindow(functionWindowId)) {
            return super.triggerFinalWindow(functionWindowId);
        }

        WindowAggregateId windowId = functionWindowId.getWindowId();
        final long windowStart = windowId.getWindowStartTimestamp();
        final long windowEnd = windowId.getWindowEndTimestamp();

        Map<Integer, List<DistributedSlice>> finalSlices = new HashMap<>();
        for (Map.Entry<ChildKey, List<DistributedSlice>> keyedSlices : childSlices.entrySet()) {
            ChildKey childKey = keyedSlices.getKey();
            List<DistributedSlice> slices = keyedSlices.getValue();

            finalSlices.putIfAbsent(childKey.getKey(), new ArrayList<>());
            List<DistributedSlice> keyedFinalSlices = finalSlices.get(childKey.getKey());

            ListIterator<DistributedSlice> iterator = slices.listIterator(slices.size());
            while (iterator.hasPrevious()) {
                DistributedSlice slice = iterator.previous();
                if (slice.getTStart() >= windowStart) {
                    if (slice.getTEnd() <= windowEnd) {
                        keyedFinalSlices.add(slice);
                    }
                } else {
                    iterator.remove();
                }
            }
        }

        for (Map.Entry<Integer, List<DistributedSlice>> keyedSlices : finalSlices.entrySet()) {
            List<AggregateFunction> noOpFn = Collections.singletonList(this.aggFns.get(functionWindowId.getFunctionId()));
            AggregateState<List<DistributedSlice>> windowAgg = new AggregateState<>(this.stateFactory, noOpFn);
            windowAgg.addElement(keyedSlices.getValue());

            Integer key = keyedSlices.getKey();
            FunctionWindowAggregateId resultId =
                    new FunctionWindowAggregateId(functionWindowId, FunctionWindowAggregateId.NO_CHILD_ID, key);
            resultWindows.add(new DistributedAggregateWindowState<>(resultId, windowAgg));
        }

        return resultWindows;
    }

    @Override
    public Double lowerFinalValue(AggregateWindow finalWindow) {
        List aggValues = finalWindow.getAggValues();
        if (aggValues.isEmpty()) {
            throw new IllegalStateException("Cannot have empty slice list in holistic merge");
        }

        List<Double> allValues = new ArrayList<>();
        List untypedSlices = (List) aggValues.get(0);
        if (!untypedSlices.isEmpty() && untypedSlices.get(0) instanceof DistributedSlice) {
            List<DistributedSlice> slices = (List<DistributedSlice>) untypedSlices;
            int totalSize = slices.stream().map(slice -> slice.getValues().size()).reduce(0, Integer::sum);

            allValues = new ArrayList<>(totalSize);
            for (DistributedSlice slice : slices) {
                allValues.addAll(slice.getValues());
            }
        } else if (!untypedSlices.isEmpty() && untypedSlices.get(0) instanceof EagerSlice) {
            List<EagerSlice> slices = (List<EagerSlice>) untypedSlices;
            for (EagerSlice s : slices) {
                List<List<Double>> allSlices = s.getAggState().getValues();
                if (allSlices.isEmpty()) {
                    continue;
                }
                allValues.addAll(allSlices.get(0));
            }
        }

        HolisticMergeWrapper holisticMergeFunction;
        if (this.aggFns.size() == 1) {
            holisticMergeFunction = (HolisticMergeWrapper) this.aggFns.get(0);
        } else {
            holisticMergeFunction = (HolisticMergeWrapper) finalWindow.getAggregateFunctions().get(0);
        }
        HolisticAggregateFunction originalFn = holisticMergeFunction.getOriginalFn();
        return (Double) originalFn.lower(allValues);
    }

    @Override
    public List<AggregateFunction> getAggregateFunctions() {
        return new ArrayList<>(Collections.singletonList(new HolisticMergeWrapper()));
    }
}
