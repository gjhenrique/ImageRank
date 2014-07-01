package br.uel.mdd.utils;

import br.uel.mdd.db.tables.pojos.DistanceFunctions;
import br.uel.mdd.metric.MetricEvaluator;

public class DistanceFunctionUtils {

    public static MetricEvaluator getMetricEvaluatorFromDistanceFunction(DistanceFunctions distanceFunctions) {

        Class clazz = ReflectionUtils.findClassByName("br.uel.mdd.metric", distanceFunctions.getClassName());
        try {
            return (MetricEvaluator) clazz.newInstance();
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }
}
