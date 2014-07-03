package br.uel.mdd.avaliation;

import br.uel.cross.parallel.mams.SlimTree;
import br.uel.cross.parallel.mams.result.ResultPair;
import br.uel.mdd.db.tables.pojos.Extractions;
import br.uel.mdd.db.tables.pojos.QueryResults;
import br.uel.mdd.metric.MetricEvaluator;
import br.uel.mdd.result.TreeResult;
import br.uel.mdd.utils.PrimitiveUtils;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

public class SlimKnn implements KnnOperation {

    private SlimTreeWrapper slimTreeWrapper;

    private MetricEvaluator metricEvaluator;

    @Inject
    public SlimKnn(@Assisted MetricEvaluator metricEvaluator, SlimTreeWrapper slimTreeWrapper) {
        this.metricEvaluator = metricEvaluator;
        this.slimTreeWrapper = slimTreeWrapper;
    }

    @Override
    public TreeResult<QueryResults> performKnn(Extractions extractionQuery, int k) {

        SlimTree slimTree = slimTreeWrapper.findSlimTree(extractionQuery, metricEvaluator);

        extractionQuery.getExtractorId();
        float[] features = PrimitiveUtils.castWrapperToPrimitiveFloat(extractionQuery.getExtractionData());

        br.uel.cross.parallel.mams.result.TreeResult treeResult = slimTree.nearestQuery(features, k, false);

        TreeResult<QueryResults> result = new TreeResult<QueryResults>(extractionQuery.getExtractionData(), k);

        int order = 0;

        for (ResultPair resultPair : treeResult.getPairs()) {
            QueryResults queryResults = new QueryResults();

            long extractionId = resultPair.getRowId();
            double distance = resultPair.getDistance();

            queryResults.setExtractionId((int) extractionId);
            queryResults.setDistance((float) distance);
            queryResults.setReturnOrder(order++);

            result.addPair(queryResults, distance);
        }

        return result;
    }
}
