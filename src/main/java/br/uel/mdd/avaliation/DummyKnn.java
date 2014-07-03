package br.uel.mdd.avaliation;

import br.uel.cross.parallel.mams.result.TreeResult;
import br.uel.mdd.dao.DatasetsDao;
import br.uel.mdd.dao.ExtractionsDao;
import br.uel.mdd.db.tables.pojos.Datasets;
import br.uel.mdd.db.tables.pojos.Extractions;
import br.uel.mdd.db.tables.pojos.QueryResults;
import br.uel.mdd.metric.MetricEvaluator;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import java.util.Collection;
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

    @Override
    public Collection<QueryResults> performKnn(Extractions extractionQuery, int k) {

        TreeResult<QueryResults> result = new TreeResult<>(k);

        Datasets dataset = datasetsDao.fetchByExtractionId(extractionQuery.getId());

        List<Extractions> extractions = extractionsDao.fetchByDatasetIdAndExtractorId(dataset.getId(), extractionQuery.getExtractorId());

        addFirstK(result, extractionQuery, extractions);
        addRemainingK(result, extractionQuery, extractions);

        return updateReturnOrder(result);
    }

    private void addFirstK(TreeResult<QueryResults> result, Extractions extractionQuery, List<Extractions> extractions) {
        for (int i = 0; i < result.getK(); i++) {
            Extractions extraction = extractions.get(i);

            double distance = metricEvaluator.getDistance(extractionQuery.getExtractionData(), extraction.getExtractionData());

            QueryResults queryResults = buildQueryResults(extraction, distance);
            result.addPair(queryResults, queryResults.getDistance());
        }
    }

    private void addRemainingK(TreeResult<QueryResults> result, Extractions extractionQuery, List<Extractions> extractions) {
        for (int i = result.getK(); i < extractions.size(); i++) {

            Extractions extraction = extractions.get(i);
            double distance = metricEvaluator.getDistance(extractionQuery.getExtractionData(), extraction.getExtractionData());

//            If the distance is bigger than the maximum distance, then query is not the selected k
            if (distance < result.getMaximumDistance()) {
                QueryResults queryResults = buildQueryResults(extraction, distance);
                result.addPair(queryResults, distance);
                result.knnCutOldResult();
            }
        }
    }

    private Collection<QueryResults> updateReturnOrder(TreeResult<QueryResults> result) {

        Collection<QueryResults> listResult = result.getObjects();
        int order = 0;
        for (QueryResults queryResults : listResult) {
            queryResults.setReturnOrder(order++);
        }

        return listResult;
    }


    public QueryResults buildQueryResults(Extractions extraction, double distance) {
        QueryResults queryResult = new QueryResults();
        queryResult.setDistance((float) distance);
        queryResult.setExtractionId(extraction.getId());
        return queryResult;
    }
}

