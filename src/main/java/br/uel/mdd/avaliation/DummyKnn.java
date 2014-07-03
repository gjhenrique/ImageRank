package br.uel.mdd.avaliation;

import br.uel.mdd.dao.DatasetsDao;
import br.uel.mdd.dao.ExtractionsDao;
import br.uel.mdd.db.tables.pojos.Datasets;
import br.uel.mdd.db.tables.pojos.Extractions;
import br.uel.mdd.db.tables.pojos.QueryResults;
import br.uel.mdd.metric.MetricEvaluator;
import br.uel.mdd.result.TreeResult;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import java.util.List;

public class DummyKnn implements KnnOperation {

    private DatasetsDao datasetsDao;

    private ExtractionsDao extractionsDao;

    private MetricEvaluator metricEvaluator;

    @Inject
    public DummyKnn(@Assisted MetricEvaluator metricEvaluator, DatasetsDao datasetsDao, ExtractionsDao extractionsDao) {
        this.metricEvaluator = metricEvaluator;
        this.datasetsDao = datasetsDao;
        this.extractionsDao = extractionsDao;
    }

    public TreeResult<QueryResults> performKnn(Extractions extractionQuery, int k) {

        TreeResult<QueryResults> result = new TreeResult<>(extractionQuery.getExtractionData(), k);

        Datasets dataset = datasetsDao.fetchByExtractionId(extractionQuery.getId());

        List<Extractions> extractions = extractionsDao.fetchByDatasetIdAndExtractorId(dataset.getId(), extractionQuery.getExtractorId());

//        Adding all the extractions in the result
        for (Extractions extraction : extractions) {

            QueryResults queryResult = new QueryResults();
            double distance = metricEvaluator.getDistance(extractionQuery.getExtractionData(), extraction.getExtractionData());
            queryResult.setDistance((float) distance);
            queryResult.setExtractionId(extraction.getId());

            result.addPair(queryResult, distance);
        }

//        Removing all the results that exceed k
        result.cut(k);

        int i = 0;
        for (QueryResults queryResults : result.getObjects()) {
            queryResults.setReturnOrder(i++);
        }

        return result;
    }
}
