package br.uel.mdd;

import br.uel.mdd.dao.DistanceFunctionsDao;
import br.uel.mdd.dao.ExtractionsDao;
import br.uel.mdd.dao.ExtractorsDao;
import br.uel.mdd.dao.ImagesDao;
import br.uel.mdd.db.tables.pojos.DistanceFunctions;
import br.uel.mdd.db.tables.pojos.Extractions;
import br.uel.mdd.db.tables.pojos.Extractors;
import br.uel.mdd.db.tables.pojos.Images;
import br.uel.mdd.io.loading.FeatureExtractionLoader;
import br.uel.mdd.io.loading.ImageLoader;
import br.uel.mdd.io.loading.QueryLoader;
import br.uel.mdd.module.AppModule;
import br.uel.mdd.module.ExtractorModule;
import br.uel.mdd.module.QueryModule;
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
        Injector injector = Guice.createInjector(new AppModule());
//        Loading Images
        if (i == 0) {

            injector = Guice.createInjector(new AppModule());
            ImageLoader lil = injector.getInstance(ImageLoader.class);

            lil.loadFilesFromFolder(args[0]);
        }
//        Loading extractions
        else if (i == 1) {


            ExtractorsDao dao = injector.getInstance(ExtractorsDao.class);

            Extractors extractor = dao.fetchOne(br.uel.mdd.db.tables.Extractors.EXTRACTORS.ID, 4);
//            List<Extractors> extractors = dao.findAll();

            ImagesDao imagesDao = injector.getInstance(ImagesDao.class);
            List<Images> images = imagesDao.findAll();

//            for (Extractors extractor : extractors) {
                injector = Guice.createInjector(new ExtractorModule(extractor));
                FeatureExtractionLoader efd = injector.getInstance(FeatureExtractionLoader.class);
                efd.extractFeatures(images);
//            }
        } else if (i == 2){
            ExtractionsDao edao = injector.getInstance(ExtractionsDao.class);
            Extractions extractions = edao.findById(1477);
            DistanceFunctionsDao dao = injector.getInstance(DistanceFunctionsDao.class);
            DistanceFunctions distanceFunction = dao.findById(1);
            injector = Guice.createInjector(new QueryModule(distanceFunction));
            QueryLoader queryLoader = injector.getInstance(QueryLoader.class);
            queryLoader.knn(extractions, 15);
        }
    }
}
