package br.uel.mdd;

import br.uel.mdd.dao.ExtractionsDao;
import br.uel.mdd.dao.ExtractorsDao;
import br.uel.mdd.dao.ImagesDao;
import br.uel.mdd.db.tables.pojos.Extractions;
import br.uel.mdd.db.tables.pojos.Extractors;
import br.uel.mdd.io.loading.FeatureExtractionLoader;
import br.uel.mdd.module.AppModule;
import br.uel.mdd.module.FeatureExtractionLoaderFactory;
import com.google.inject.Guice;
import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.operation.Operation;
import org.junit.Before;
import org.junit.Test;

import javax.inject.Inject;
import java.io.File;
import java.util.List;

import static br.uel.mdd.db.tables.Extractions.EXTRACTIONS;
import static com.ninja_squad.dbsetup.operation.CompositeOperation.sequenceOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestFeatureExtractionLoader {

    @Inject
    private ExtractorsDao extractorsDao;

    @Inject
    private ExtractionsDao extractionsDao;

    @Inject
    private CommonOperations commonOperations;

    @Inject
    private ImagesDao imagesDao;

    @Inject
    private FeatureExtractionLoaderFactory featureExtractionLoaderFactory;

    @Before
    public void prepareDatabase() {
        Guice.createInjector(new AppModule(true)).injectMembers(this);

        Operation operation = sequenceOf(CommonOperations.DATASETS, CommonOperations.CLASS_IMAGE,
                CommonOperations.DATASET_CLASSES, CommonOperations.IMAGES);

        DbSetup setup = commonOperations.createDbSetup(operation);
        setup.launch();

        populateDatabase();
    }

    private void populateDatabase() {
        for (Extractors extractors : extractorsDao.findAll()) {
            FeatureExtractionLoader featureExtractionLoader = featureExtractionLoaderFactory.create(extractors);
            featureExtractionLoader.extractFeatures(imagesDao.findAll());
        }
    }

    @Test
    public void testFeaturesInsertionSize() throws Exception {
        List<Extractions> extractions = extractionsDao.findAll();
        assertThat(extractions.size(), equalTo(10));
    }

    @Test
    public void testFindByDataset() throws Exception {
        List<Extractions> extractions = extractionsDao.fetch(EXTRACTIONS.EXTRACTOR_ID, 1);
        assertThat(extractions.size(), equalTo(5));
    }

    @Test
    public void testFindByImageId() throws Exception {
        List<Extractions> extractions = extractionsDao.fetch(EXTRACTIONS.IMAGE_ID, 1);
        assertThat(extractions.size(), equalTo(2));
    }

}
