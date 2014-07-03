package br.uel.mdd.module;

import br.uel.mdd.avaliation.KnnOperation;
import br.uel.mdd.metric.MetricEvaluator;

public interface KnnOperationFactory {

    public KnnOperation create(MetricEvaluator metricEvaluator);

}
