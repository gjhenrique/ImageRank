package br.uel.mdd;

import br.uel.mdd.dao.DistanceFunctionsDao;
import br.uel.mdd.dao.ExtractionsDao;
import br.uel.mdd.dao.QueriesDao;
import br.uel.mdd.dao.QueryResultsDao;
import br.uel.mdd.db.tables.pojos.DistanceFunctions;
import br.uel.mdd.db.tables.pojos.Extractions;
import br.uel.mdd.db.tables.pojos.Queries;
import br.uel.mdd.db.tables.pojos.QueryResults;
import br.uel.mdd.io.loading.QueryLoader;
import br.uel.mdd.module.AppModule;
import br.uel.mdd.module.QueryLoaderFactory;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.operation.Operation;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static com.ninja_squad.dbsetup.operation.CompositeOperation.sequenceOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestQueryLoader {

    @Inject
    private CommonOperations commonOperations;

    @Inject
    private ExtractionsDao extractionsDao;

    @Inject
    private QueryResultsDao queryResultsDao;

    @Inject
    private QueriesDao queriesDao;

    @Inject
    private DistanceFunctionsDao distanceFunctionsDao;

    private Injector injector;

    @Before
    public void prepareDatabase() {
        injector = Guice.createInjector(new AppModule(true));
        injector.injectMembers(this);

        Operation operation = sequenceOf(CommonOperations.DATASETS, CommonOperations.CLASS_IMAGE,
                CommonOperations.DATASET_CLASSES, CommonOperations.IMAGES, CommonOperations.EXTRACTIONS);

        DbSetup dbSetup = commonOperations.createDbSetup(operation);
        dbSetup.launch();

        populateDatabase();
    }

    private void populateDatabase() {
        List<Extractions> extractions = extractionsDao.findAll();
        for (Extractions extraction : extractions) {
            for (DistanceFunctions distanceFunctions : distanceFunctionsDao.findAll()) {
                QueryLoaderFactory factory = injector.getInstance(QueryLoaderFactory.class);

                QueryLoader queryLoader = factory.create(distanceFunctions);

                for (int i = 1; i <= 2; i++) {
                    queryLoader.knn(extraction, i);
                }
            }
        }
    }

    @Test
    public void testQueryInsertion() throws Exception {
        List<Queries> queries = queriesDao.findAll();
//        Extractions.findAll().size() * 2 * DistanceFunctions.findAll.size()
        assertThat(queries.size(), equalTo(8));
    }

    @Test
    public void testQueryResultsInsertion() throws Exception {
        List<QueryResults> queryResults = queryResultsDao.findAll();
//        (Extractions.findAll().size() * 2 * DistanceFunctions.findAll.size()) +
//          (Extractions.findAll().size() * DistanceFunctions.findAll.size())
        assertThat(queryResults.size(), equalTo(12));
    }
}
