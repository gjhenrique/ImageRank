package br.uel.mdd.extractor;

import br.uel.mdd.dao.ExtractorsDao;
import br.uel.mdd.dao.ImagesDao;
import br.uel.mdd.db.tables.pojos.Extractors;
import br.uel.mdd.db.tables.pojos.Images;
import br.uel.mdd.io.loading.FeatureExtractionLoader;
import br.uel.mdd.module.ExtractorModule;
import br.uel.mdd.module.FeatureExtractionLoaderFactory;
import br.uel.mdd.utils.ExtractorUtils;
import com.google.inject.Guice;
import com.google.inject.Injector;

import java.util.List;

public class MainExtractor {

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new ExtractorModule());
        ExtractorsDao dao = injector.getInstance(ExtractorsDao.class);

//        Extractors extractor = dao.fetchOne(br.uel.mdd.db.tables.Extractors.EXTRACTORS.ID, 4);
        List<Extractors> extractors = dao.findAll();

        ImagesDao imagesDao = injector.getInstance(ImagesDao.class);
        List<Images> images = imagesDao.findAll();

//        for (Extractors extractor : extractors) {
//            injector = Guice.createInjector(new ExtractorModule(extractor));
        FeatureExtractionLoaderFactory factory = injector.getInstance(FeatureExtractionLoaderFactory.class);
        System.out.println(factory);

        FeatureExtractor fe = ExtractorUtils.getReflectionUtils(extractors.get(0));
        System.out.println(fe);
        FeatureExtractionLoader fel = factory.create(extractors.get(0), fe);
        System.out.println(fel);

//        FeatureExtractionLoaderF efd = injector.getInstance(FeatureExtractionLoaderFactory.class);
//        efd.extractFeatures(images);
//        }
    }
}
