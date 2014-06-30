package br.uel.mdd;

import br.uel.mdd.dao.ClassImageDao;
import br.uel.mdd.dao.DatasetsDao;
import br.uel.mdd.dao.ImagesDao;
import br.uel.mdd.db.tables.pojos.ClassImage;
import br.uel.mdd.db.tables.pojos.Datasets;
import br.uel.mdd.db.tables.pojos.Images;
import br.uel.mdd.io.loading.ImageLoader;
import br.uel.mdd.module.AppModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.DbSetupTracker;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static br.uel.mdd.db.tables.ClassImage.CLASS_IMAGE;
import static br.uel.mdd.db.tables.Images.IMAGES;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestImageLoader {

    @Inject
    private ImagesDao imagesDao;

    @Inject
    private DatasetsDao datasetsDao;

    @Inject
    private ClassImageDao classImageDao;

    @Inject
    private ImageLoader imageLoader;

    @Inject
    private CommonOperations commonOperations;

    private static DbSetupTracker dbSetupTracker = new DbSetupTracker();

    private File imagesDirectory;
    private int imagesDirectorySize;

    @Before
    public void prepareDatabase() {
        Guice.createInjector(new AppModule()).injectMembers(this);

        String path = getClass().getResource("/imgs/dicom/Pulmao").getFile();

        DbSetup setup = commonOperations.createDbSetup();
        dbSetupTracker.launchIfNecessary(setup);

        imagesDirectory = new File(path);
        imagesDirectorySize = imagesDirectory.listFiles().length;
        imageLoader.loadFilesFromFolder(imagesDirectory);
    }

    @Test
    public void testImageImageInsertion() throws Exception {
        dbSetupTracker.skipNextLaunch();
        List<Images> images = imagesDao.findAll();
        assertThat(images.size(), equalTo(imagesDirectorySize));
    }

    @Test
    public void testFindByMimeType() {
        dbSetupTracker.skipNextLaunch();

        List<Images> images = imagesDao.fetch(IMAGES.MIME_TYPE, "application/dicom");
        assertThat(images.size(), equalTo(imagesDirectorySize));
    }

    @Test
    public void testDatasetsInsertion() {
        dbSetupTracker.skipNextLaunch();

        List<Datasets> datasets = datasetsDao.findAll();
        assertThat(datasets.size(), equalTo(1));
        assertThat(datasets.get(0).getName(), equalTo("Pulmao"));
    }

    @Test
    public void testClassesInsertion() throws Exception {
        dbSetupTracker.skipNextLaunch();

        List<ClassImage> classes = classImageDao.findAll();
        assertThat(classes.size(), equalTo(3));

        classes = classImageDao.fetch(CLASS_IMAGE.NAME, "Consolidacao");
        assertThat(classes.size(), equalTo(1));
    }

    @Test
    public void testFindByFileName() throws Exception {
        dbSetupTracker.skipNextLaunch();

        List<Images> images = imagesDao.fetch(IMAGES.FILE_NAME, "Consolidacao0002685C_08.dcm");
        assertThat(images.size(), equalTo(1));
    }

}
