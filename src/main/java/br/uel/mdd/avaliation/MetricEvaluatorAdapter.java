package br.uel.mdd.avaliation;

import br.uel.mdd.metric.MetricEvaluator;
import br.uel.mdd.utils.PrimitiveUtils;

public class MetricEvaluatorAdapter implements br.uel.cross.parallel.mams.distance.MetricEvaluator {

    private MetricEvaluator metricEvaluator;

    public MetricEvaluatorAdapter(MetricEvaluator metricEvaluator) {
        this.metricEvaluator = metricEvaluator;
    }

    @Override
    public double getDistance(float[] obj1, float[] obj2) {
        Double[] doubleObj1 = PrimitiveUtils.castPrimitiveToWrapper(obj1);
        Double[] doubleObj2 = PrimitiveUtils.castPrimitiveToWrapper(obj1);

        return metricEvaluator.getDistance(doubleObj1, doubleObj2);
    }

}
