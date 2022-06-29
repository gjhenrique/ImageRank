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

    private File imagesDirectory;
    private int imagesDirectorySize;

    @Before
    public void prepareDatabase() {
        Guice.createInjector(new AppModule(true)).injectMembers(this);

        DbSetup setup = commonOperations.createDbSetup();
        setup.launch();

        populateDatabase();
    }

    private void populateDatabase() {
        String path = getClass().getResource("/imgs/Dogs").getFile();

        imagesDirectory = new File(path);
        imagesDirectorySize = imagesDirectory.listFiles().length;
        imageLoader.loadFilesFromFolder(imagesDirectory);
    }


    @Test
    public void testImageImageInsertion() throws Exception {
        List<Images> images = imagesDao.findAll();
        assertThat(images.size(), equalTo(imagesDirectorySize));
    }

    @Test
    public void testFindByMimeType() {
        List<Images> images = imagesDao.fetch(IMAGES.MIME_TYPE, "image/jpeg");
        assertThat(images.size(), equalTo(imagesDirectorySize));
    }

    @Test
    public void testDatasetsInsertion() {

        List<Datasets> datasets = datasetsDao.findAll();
        assertThat(datasets.size(), equalTo(1));
        assertThat(datasets.get(0).getName(), equalTo("Dogs"));
    }

    @Test
    public void testClassesInsertion() throws Exception {
        List<ClassImage> classes = classImageDao.findAll();
        assertThat(classes.size(), equalTo(3));

        classes = classImageDao.fetch(CLASS_IMAGE.NAME, "Saluki");
        assertThat(classes.size(), equalTo(1));
    }

    @Test
    public void testFindByFileName() throws Exception {
        List<Images> images = imagesDao.fetch(IMAGES.FILE_NAME, "Saluki_n02091831_97.jpg");
        assertThat(images.size(), equalTo(1));
    }

}
