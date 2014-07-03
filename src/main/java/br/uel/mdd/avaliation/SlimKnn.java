package br.uel.mdd.avaliation;

import br.uel.cross.parallel.mams.SlimTree;
import br.uel.cross.parallel.mams.result.ResultPair;
import br.uel.cross.parallel.mams.result.TreeResult;
import br.uel.mdd.db.tables.pojos.Extractions;
import br.uel.mdd.db.tables.pojos.QueryResults;
import br.uel.mdd.metric.MetricEvaluator;
import br.uel.mdd.utils.PrimitiveUtils;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SlimKnn implements KnnOperation {

    private SlimTreeWrapper slimTreeWrapper;

    private MetricEvaluator metricEvaluator;

    @Inject
    public SlimKnn(@Assisted MetricEvaluator metricEvaluator, SlimTreeWrapper slimTreeWrapper) {
        this.metricEvaluator = metricEvaluator;
        this.slimTreeWrapper = slimTreeWrapper;
    }

    @Override
    public Collection<QueryResults> performKnn(Extractions extractionQuery, int k) {

        SlimTree slimTree = slimTreeWrapper.findSlimTree(extractionQuery, metricEvaluator);

        float[] features = PrimitiveUtils.castWrapperToPrimitiveFloat(extractionQuery.getExtractionData());

        TreeResult<Long> treeResult = slimTree.nearestQuery(features, k, false);

        int order = 0;

        List<QueryResults> result = new ArrayList<>();

        for (ResultPair<Long> resultPair : treeResult.getPairs()) {
            QueryResults queryResults = new QueryResults();

            long extractionId = resultPair.getObject();
            double distance = resultPair.getDistance();

            queryResults.setExtractionId((int) extractionId);
            queryResults.setDistance((float) distance);
            queryResults.setReturnOrder(order++);
            result.add(queryResults);
        }

        return result;
    }
}
