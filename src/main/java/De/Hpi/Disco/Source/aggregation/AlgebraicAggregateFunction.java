package De.Hpi.Disco.Source.aggregation;

import de.tub.dima.scotty.core.windowFunction.AggregateFunction;

public interface AlgebraicAggregateFunction<InputType, PartialAggregateType extends AlgebraicPartial> extends
        AggregateFunction<InputType, PartialAggregateType, PartialAggregateType> {
    PartialAggregateType partialFromString(String partialString);
}
