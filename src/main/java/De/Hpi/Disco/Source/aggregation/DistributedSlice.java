package De.Hpi.Disco.Source.aggregation;

import De.Hpi.Disco.Source.utility.Event;
import de.tub.dima.scotty.core.windowFunction.AggregateFunction;
import de.tub.dima.scotty.slicing.slice.Slice;
import de.tub.dima.scotty.slicing.state.AggregateState;
import de.tub.dima.scotty.state.memory.MemoryStateFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class DistributedSlice implements Slice {
    private final long tStart;
    private final long tEnd;
    private final List<Double> values;
    private final int key;

    public DistributedSlice(long tStart, long tEnd, List<Double> values) {
        this(tStart, tEnd, values, Event.NO_KEY);
    }

    public DistributedSlice(long tStart, long tEnd, Integer... values) {
        this(tStart, tEnd, Arrays.stream(values).map(Double::valueOf).collect(Collectors.toList()));
    }

    public DistributedSlice(long tStart, long tEnd, List<Double> values, int key) {
        this.tStart = tStart;
        this.tEnd = tEnd;
        this.values = values;
        this.key = key;
    }

    @Override
    public long getTStart() {
        return tStart;
    }

    @Override
    public long getTEnd() {
        return tEnd;
    }

    @Override
    public long getTLast() {
        return tEnd;
    }

    public int getKey() {
        return key;
    }

    @Override
    public AggregateState getAggState() {
        List<AggregateFunction> aggFn = Collections.singletonList(new HolisticMergeWrapper());
        AggregateState<List<Double>> state = new AggregateState<>(new MemoryStateFactory(), aggFn);
        state.addElement(this.values);
        return state;
    }

    public List<Double> getValues() {
        return values;
    }

    @Override
    public String toString() {
        return "DistributedSlice{" +
                "tStart=" + tStart +
                ", tEnd=" + tEnd +
                ", values=" + values +
                ", key=" + key +
                '}';
    }

    // Ignore from here on down

    @Override
    public long getTFirst() {
        return 0;
    }

    @Override
    public void setTStart(long tStart) {}

    @Override
    public void setTEnd(long tEnd) {}

    @Override
    public void merge(Slice otherSlice) {

    }

    @Override
    public Type getType() {
        return null;
    }

    @Override
    public void setType(Type type) {

    }

    @Override
    public void addElement(Object element, long ts) {

    }

    @Override
    public long getCStart() {
        return 0;
    }

    @Override
    public long getCLast() {
        return 0;
    }
}
