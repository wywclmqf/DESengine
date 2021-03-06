package De.Hpi.Disco.Source.aggregation.functions;

import De.Hpi.Disco.Source.aggregation.HolisticAggregateFunction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MaxMedianAggregateFunction implements HolisticAggregateFunction<Long, List<Long>, Long> {
    @Override
    public List<Long> lift(Long inputTuple) {
        return new ArrayList<>(Collections.singletonList(inputTuple));
    }

    @Override
    public List<Long> combine(List<Long> partialAggregate1, List<Long> partialAggregate2) {
        partialAggregate1.addAll(partialAggregate2);
        return partialAggregate1;
    }

    @Override
    public Long lower(List<Long> aggregate) {
        if (aggregate.isEmpty()) {
            return null;
        }

        return Collections.max(aggregate);
    }
}
