package br.uel.mdd;

import br.uel.mdd.dao.ExtractionsDao;
import br.uel.mdd.db.tables.pojos.Extractions;
import br.uel.mdd.main.Main;
import br.uel.mdd.module.AppModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.operation.Operation;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static com.ninja_squad.dbsetup.operation.CompositeOperation.sequenceOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestCommandLineExtractions {

    @Inject
    private CommonOperations commonOperations;

    @Inject
    private ExtractionsDao extractionsDao;

    @Before
    public void prepareDatabase() {
        Guice.createInjector(new AppModule(true)).injectMembers(this);

        Operation operation = sequenceOf(CommonOperations.DATASETS, CommonOperations.CLASS_IMAGE,
                CommonOperations.DATASET_CLASSES, CommonOperations.IMAGES);

        DbSetup setup = commonOperations.createDbSetup(operation);
        setup.launch();
    }

    @Test
    public void testImagesCreation() throws Exception {
        String commandLineArgument = "--feature-extraction -all-ext -no-threads";

        new Main(commandLineArgument.split(" "));

        List<Extractions> extractions = extractionsDao.findAll();
        assertThat(extractions.size(), equalTo(6));
    }

    @Test
    public void testImagesCreationWithExtractorId() throws Exception {

        String commandLineArgument = "--feature-extraction --extractor-feature-id 1";

        new Main(commandLineArgument.split(" "));

        List<Extractions> extractions = extractionsDao.findAll();
        assertThat(extractions.size(), equalTo(3));
    }

}
