package br.uel.mdd.io.loading;

import br.uel.mdd.dao.*;
import br.uel.mdd.db.tables.pojos.*;
import br.uel.mdd.metric.MetricEvaluator;
import br.uel.mdd.result.ResultPair;
import br.uel.mdd.result.TreeResult;
import com.google.inject.Inject;

import java.util.List;

import static br.uel.mdd.db.tables.Extractions.EXTRACTIONS;


/**
 * @author ${user}
 * @TODO Auto-generated comment
 * <p/>
 * Created by pedro on 02/06/14.
 */
public class QueryLoader {

    @Inject
    MetricEvaluator metricEvaluator;

    @Inject
    ExtractionsDao extractionsDao;

    @Inject
    ImagesDao imagesDao;

    @Inject
    DatasetsDao datasetsDao;

    @Inject
    DistanceFunctions distanceFunction;

    @Inject
    QueriesDao queriesDao;

    @Inject
    QueryResultsDao queryResultsDao;



    public void bulkKnn(Images image, int steps){
        List<Extractions> extractions = extractionsDao.fetch(EXTRACTIONS.IMAGE_ID, image.getId());
        for (Extractions extraction : extractions) {
            bulkKnn(extraction, steps);
        }

    }


    public void bulkKnn(Extractions extractions, int steps){
        Datasets dataset = datasetsDao.fetchByImageId(extractions.getImageId());
        long numberOfImages = imagesDao.getCountByDataset(dataset);
        int stepSize = (int) (numberOfImages/steps);
        for (int k = stepSize; k < numberOfImages; k+=stepSize) {
            knn(extractions, k);
        }
    }

    public void knn(Extractions extractionQuery, int k){
        Queries query = buildQuery(extractionQuery);

        long start = System.nanoTime();

        TreeResult<QueryResults> result = performKnn(extractionQuery, query, k);

        result.cut(k);

        int i = 0;
        for(ResultPair<QueryResults> resultPair : result.getPairs()) {
            QueryResults queryResults = resultPair.getObject();
            queryResults.setReturnOrder(i++);
            queryResultsDao.insertNullPk(queryResults);
        }

        long end = System.nanoTime() - start;

        query.setQueryDuration(end);
        queriesDao.update(query);
    }

    private TreeResult<QueryResults> performKnn(Extractions extractionQuery, Queries query, int k){

        TreeResult<QueryResults> result = new TreeResult<>(extractionQuery.getExtractionData(), k);
        Datasets dataset = datasetsDao.fetchByExtractionId(extractionQuery.getId());

        List<Extractions> extractions = extractionsDao.fetchByDatasetId(dataset.getId());
        for (Extractions extraction : extractions) {

            QueryResults queryResult = new QueryResults();
            double distance = metricEvaluator.getDistance(extractionQuery.getExtractionData(), extraction.getExtractionData());
            queryResult.setDistance((float) distance);
            queryResult.setImageId(extraction.getImageId());
            queryResult.setQueryId(query.getId());
            result.addPair(queryResult, distance);

        }
        return result;
    }
    private Queries buildQuery(Extractions extractionQuery) {
        Queries query = new Queries();
        query.setImageId(extractionQuery.getImageId());
        query.setExtractionId(extractionQuery.getId());
        query.setDistanceFunctionId(distanceFunction.getId());
        queriesDao.insertNullPk(query);
        return query;
    }


}
