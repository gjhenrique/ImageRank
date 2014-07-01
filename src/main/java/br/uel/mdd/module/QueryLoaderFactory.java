package br.uel.mdd.module;

import br.uel.mdd.db.tables.pojos.DistanceFunctions;
import br.uel.mdd.io.loading.QueryLoaderImpl;
import br.uel.mdd.metric.MetricEvaluator;

public interface QueryLoaderFactory {
    public QueryLoaderImpl create(MetricEvaluator metricEvaluator, DistanceFunctions distanceFunction);
}
