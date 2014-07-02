package br.uel.mdd.io.loading;

import br.uel.mdd.dao.*;
import br.uel.mdd.db.tables.pojos.*;
import br.uel.mdd.metric.MetricEvaluator;
import br.uel.mdd.result.ResultPair;
import br.uel.mdd.result.TreeResult;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

public class QueryLoaderImpl implements QueryLoader {

    private MetricEvaluator metricEvaluator;

    private ExtractionsDao extractionsDao;

    private ImagesDao imagesDao;

    private DatasetsDao datasetsDao;

    private DistanceFunctions distanceFunction;

    private QueriesDao queriesDao;

    private QueryResultsDao queryResultsDao;

    private final Logger logger = LoggerFactory.getLogger(QueryLoaderImpl.class);

    //    Warning!!! Too many dependencies
    @Inject

    public QueryLoaderImpl(@Assisted MetricEvaluator metricEvaluator, ExtractionsDao extractionsDao, ImagesDao imagesDao, DatasetsDao datasetsDao,
                           @Assisted DistanceFunctions distanceFunction, QueriesDao queriesDao, QueryResultsDao queryResultsDao) {
        this.metricEvaluator = metricEvaluator;
        this.extractionsDao = extractionsDao;
        this.imagesDao = imagesDao;
        this.datasetsDao = datasetsDao;
        this.distanceFunction = distanceFunction;
        this.queriesDao = queriesDao;
        this.queryResultsDao = queryResultsDao;
    }

    @Override
    public void knn(Extractions extractionQuery, int k) {

        if(k < extractionsDao.count()) {
            logger.warn("K-nn is bigger than the number of extractions. Try to decrease the number of k");
            return;
        }

        Queries query = buildQuery(extractionQuery);

        logger.info("Starting new {}-nn query with distance function {} and extraction {}", k, distanceFunction.getId(), Arrays.toString(extractionQuery.getExtractionData()));

        long start = System.nanoTime();

        TreeResult<QueryResults> result = performKnn(extractionQuery, query, k);

        long end = System.nanoTime() - start;

        logger.debug("Result of knn with {} elements", result.getNumberOfEntries());

        int i = 0;
        for (ResultPair<QueryResults> resultPair : result.getPairs()) {
            QueryResults queryResults = resultPair.getObject();
            queryResults.setReturnOrder(i++);
            queryResultsDao.insertNullPk(queryResults);
        }

        query.setQueryDuration(end);
        queriesDao.update(query);
    }

    private TreeResult<QueryResults> performKnn(Extractions extractionQuery, Queries query, int k) {

        TreeResult<QueryResults> result = new TreeResult<>(extractionQuery.getExtractionData(), k);
        Datasets dataset = datasetsDao.fetchByExtractionId(extractionQuery.getId());

        List<Extractions> extractions = extractionsDao.fetchByDatasetIdAndExtractorId(dataset.getId(), extractionQuery.getExtractorId());
        for (Extractions extraction : extractions) {

            QueryResults queryResult = new QueryResults();
            double distance = metricEvaluator.getDistance(extractionQuery.getExtractionData(), extraction.getExtractionData());
            queryResult.setDistance((float) distance);
            queryResult.setImageId(extraction.getImageId());
            queryResult.setQueryId(query.getId());
            result.addPair(queryResult, distance);

        }

        result.cut(k);

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
