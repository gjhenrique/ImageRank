package br.uel.mdd.io.loading;

import br.uel.mdd.avaliation.KnnOperation;
import br.uel.mdd.dao.ExtractionsDao;
import br.uel.mdd.dao.QueriesDao;
import br.uel.mdd.dao.QueryResultsDao;
import br.uel.mdd.db.tables.pojos.DistanceFunctions;
import br.uel.mdd.db.tables.pojos.Extractions;
import br.uel.mdd.db.tables.pojos.Queries;
import br.uel.mdd.db.tables.pojos.QueryResults;
import br.uel.mdd.metric.MetricEvaluator;
import br.uel.mdd.module.KnnOperationFactory;
import br.uel.mdd.utils.DistanceFunctionUtils;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import org.jooq.ConnectionProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import static br.uel.mdd.db.tables.QueryResults.QUERY_RESULTS;

public class QueryLoaderImpl implements QueryLoader {

    private MetricEvaluator metricEvaluator;

    private ExtractionsDao extractionsDao;

    private DistanceFunctions distanceFunction;

    private QueriesDao queriesDao;

    private QueryResultsDao queryResultsDao;

    private KnnOperationFactory knnOperationFactory;

    private final Logger logger = LoggerFactory.getLogger(QueryLoaderImpl.class);

    //    Warning!!! Too many dependencies
    @Inject
    public QueryLoaderImpl(ExtractionsDao extractionsDao,
                           @Assisted DistanceFunctions distanceFunction, QueriesDao queriesDao, QueryResultsDao queryResultsDao, KnnOperationFactory knnOperationFactory) {
        this.metricEvaluator = DistanceFunctionUtils.getMetricEvaluatorFromDistanceFunction(distanceFunction);
        this.extractionsDao = extractionsDao;
        this.distanceFunction = distanceFunction;
        this.queriesDao = queriesDao;
        this.queryResultsDao = queryResultsDao;
        this.knnOperationFactory = knnOperationFactory;
    }

    @Override
    public void knn(Extractions extractionQuery, int k) {

        if (k >= extractionsDao.count()) {
            logger.warn("K-nn is bigger than the number of extractions. Try to decrease the number of k");
            return;
        }

        logger.debug("Starting new {}-nn query with distance function {} and extraction {}", k, distanceFunction.getId(), extractionQuery.getId());

        ConnectionProvider provider = extractionsDao.configuration().connectionProvider();
        Connection connection = provider.acquire();

        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        eraseExistingQuery(extractionQuery, k);

        Queries query = buildQuery(extractionQuery, k);
        queriesDao.insertNullPk(query);

        KnnOperation knnOperation = knnOperationFactory.create(metricEvaluator);

        long start = System.nanoTime();
        Collection<QueryResults> result = knnOperation.performKnn(extractionQuery, k);
        long end = System.nanoTime() - start;

        for (QueryResults queryResults : result) {
            queryResults.setQueryId(query.getId());
        }

//        Watch out! Not populating the primary key of QueryResults
        queryResultsDao.insert(result);

        query.setQueryDuration(end);
        queriesDao.update(query);

        logger.debug("Result of knn with {} elements", result.size());

        try {
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        provider.release(connection);
    }

    /**
     * Check if the query already exists
     * If it already exists, erase the query_results and the query itself
     * The contents are deleted and the query is remade because new images may have been loaded in the database after the existing query
     */
    private void eraseExistingQuery(Extractions extractionQuery, int k) {
        Queries queries = queriesDao.fetchByExtractionIdAndDistanceFunctionIdAndK(extractionQuery.getId(),
                distanceFunction.getId(), k);

        if (queries != null) {
            List<QueryResults> queryResults = queryResultsDao.fetch(QUERY_RESULTS.QUERY_ID, queries.getId());
            queryResultsDao.delete(queryResults);
            queriesDao.delete(queries);
        }

    }

    private Queries buildQuery(Extractions extractionQuery, int k) {
        Queries query = new Queries();
        query.setImageId(extractionQuery.getImageId());
        query.setExtractionId(extractionQuery.getId());
        query.setDistanceFunctionId(distanceFunction.getId());
        query.setK(k);
        return query;
    }

}
