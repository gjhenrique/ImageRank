package br.uel.mdd;

import br.uel.mdd.dao.ExtractorsDao;
import br.uel.mdd.dao.ImagesDao;
import br.uel.mdd.db.tables.QueryResults;
import br.uel.mdd.db.tables.pojos.Extractors;
import br.uel.mdd.db.tables.pojos.Images;
import br.uel.mdd.io.loading.FeatureExtractionLoader;
import br.uel.mdd.io.loading.ImageLoader;
import br.uel.mdd.module.AppModule;
import br.uel.mdd.module.ExtractorModule;
import br.uel.mdd.result.ResultPair;
import br.uel.mdd.result.TreeResult;
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

    public static void main(String args[]) {

        int i = 1;

//        Loading Images
        if (i == 0) {

            Injector injector = Guice.createInjector(new AppModule());
            ImageLoader lil = injector.getInstance(ImageLoader.class);

            lil.loadFilesFromFolder(args[0]);
        }
//        Loading extractions
        else if (i == 1) {

            Injector injector = Guice.createInjector(new AppModule());
            ExtractorsDao dao = injector.getInstance(ExtractorsDao.class);

            List<Extractors> extractors = dao.findAll();

            ImagesDao imagesDao = injector.getInstance(ImagesDao.class);
            List<Images> images = imagesDao.findAll();

            for (Extractors extractor : extractors) {
                injector = Guice.createInjector(new ExtractorModule(extractor));
                FeatureExtractionLoader efd = injector.getInstance(FeatureExtractionLoader.class);
                efd.extractFeatures(images);
            }
            TreeResult<QueryResults> result = new TreeResult<QueryResults>(null, 10, false);

            for(ResultPair results : result.getPairs()) {

            }
        }
    }
}
