package br.uel.mdd;

import br.uel.mdd.dao.ExtractorsDao;
import br.uel.mdd.dao.ImagesDao;
import br.uel.mdd.db.tables.pojos.Extractors;
import br.uel.mdd.db.tables.pojos.Images;
import br.uel.mdd.io.loading.FeatureExtractionLoader;
import br.uel.mdd.module.AppModule;
import br.uel.mdd.module.ExtractorModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

import java.util.List;

/**
 * @author ${user}
 * @TODO Auto-generated comment
 * <p/>
 * Created by pedro on 28/05/14.
 */
public class Main {

    public static void main(String args[]){

        Injector injector = Guice.createInjector(new AppModule());
        ExtractorsDao dao = injector.getInstance(ExtractorsDao.class);
        Extractors extractor = dao.findById(3);

        ImagesDao imagesDao = injector.getInstance(ImagesDao.class);
        List<Images> images = imagesDao.findAll();

        injector = Guice.createInjector(new ExtractorModule(extractor));
        FeatureExtractionLoader efd = injector.getInstance(FeatureExtractionLoader.class);

        efd.extractFeatures(images);
    }
}
