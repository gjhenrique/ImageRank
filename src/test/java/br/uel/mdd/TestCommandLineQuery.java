package br.uel.mdd;

import br.uel.mdd.dao.QueriesDao;
import br.uel.mdd.dao.QueryResultsDao;
import br.uel.mdd.main.Main;
import br.uel.mdd.module.AppModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.operation.Operation;
import org.junit.Before;
import org.junit.Test;

import static com.ninja_squad.dbsetup.operation.CompositeOperation.sequenceOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestCommandLineQuery {

    @Inject
    private CommonOperations commonOperations;

    @Inject
    private QueriesDao queriesDao;

    @Inject
    private QueryResultsDao queryResultsDao;

    @Before
    public void prepareDatabase() {
        Guice.createInjector(new AppModule()).injectMembers(this);

        Operation operation = sequenceOf(CommonOperations.DATASETS, CommonOperations.CLASS_IMAGE,
                CommonOperations.DATASET_CLASSES, CommonOperations.IMAGES, CommonOperations.EXTRACTIONS);

        DbSetup setup = commonOperations.createDbSetup(operation);
        setup.launch();
    }

    @Test
    public void testQueriesCreation() throws Exception {
        String commandLineArgument = "--knn-queries --all-extractions --all-distance-functions --max-k 2 --rate-k 1";

        new Main(commandLineArgument.split(" "));

        assertThat(queriesDao.findAll().size(), equalTo(8));
        assertThat(queryResultsDao.findAll().size(), equalTo(12));
    }


}
