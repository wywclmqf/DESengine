package De.Hpi.Disco.Source.aggregation;

import de.tub.dima.scotty.core.windowFunction.AggregateFunction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Used in child to process single events.
public class HolisticAggregateHelper<InputType> implements AggregateFunction<InputType, List<InputType>, List<InputType>> {
    @Override
    public List<InputType> lift(InputType inputTuple) {
        return new ArrayList<>(Collections.singleton(inputTuple));
    }

    @Override
    public List<InputType> combine(List<InputType> partialAggregate1, List<InputType> partialAggregate2) {
        List<InputType> copy = new ArrayList<>(partialAggregate1);
        copy.addAll(partialAggregate2);
        return copy;
    }

    @Override
    public List<InputType> lower(List<InputType> aggregate) {
        return aggregate;
    }
}

