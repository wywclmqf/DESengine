package De.Hpi.Disco.Source.aggregation;

import de.tub.dima.scotty.core.windowFunction.ReduceAggregateFunction;

public class AlgebraicMergeFunction<PartialType extends AlgebraicPartial<PartialType, ResultType>, ResultType> implements ReduceAggregateFunction<PartialType> {
    private final AlgebraicAggregateFunction originalFn;

    public AlgebraicMergeFunction(AlgebraicAggregateFunction originalFn) {
        this.originalFn = originalFn;
    }

    public AlgebraicMergeFunction() {
        this(null);
    }

    public AlgebraicAggregateFunction getOriginalFn() {
        return originalFn;
    }

    @Override
    public PartialType combine(PartialType partialAggregate1, PartialType partialAggregate2) {
        return partialAggregate1.merge(partialAggregate2);
    }
}
