package br.uel.mdd;

import br.uel.mdd.dao.ImagesDao;
import br.uel.mdd.db.tables.pojos.Images;
import br.uel.mdd.main.Main;
import br.uel.mdd.module.AppModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.ninja_squad.dbsetup.DbSetup;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;


public class TestCommandLineImages {

    @Inject
    private ImagesDao imagesDao;

    @Inject
    private CommonOperations commonOperations;

    private String path;

    @Before
    public void prepareDatabase() {
        Guice.createInjector(new AppModule(true)).injectMembers(this);
        path = getClass().getResource("/imgs/dicom/Pulmao").getFile();

        DbSetup setup = commonOperations.createDbSetup();
        setup.launch();
    }

    @Test
    public void testImagesCreation() throws Exception {
        String commandLineArgument = "--image-extraction --images-path " + path;

        new Main(commandLineArgument.split(" "));

        List<Images> images = imagesDao.findAll();
        assertThat(images.size(), equalTo(5));
    }
}
