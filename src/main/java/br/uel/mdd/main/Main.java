package br.uel.mdd.main;

import br.uel.mdd.dao.ExtractorsDao;
import br.uel.mdd.dao.ImagesDao;
import br.uel.mdd.db.tables.pojos.Extractors;
import br.uel.mdd.db.tables.pojos.Images;
import br.uel.mdd.extractor.FeatureExtractor;
import br.uel.mdd.io.loading.FeatureExtractionLoader;
import br.uel.mdd.io.loading.ImageLoader;
import br.uel.mdd.module.AppModule;
import br.uel.mdd.module.FeatureExtractionLoaderFactory;
import br.uel.mdd.utils.ExtractorUtils;
import com.google.inject.Guice;
import com.google.inject.Injector;

import java.util.ArrayList;
import java.util.List;

public class Main {

    private Injector injector = Guice.createInjector(new AppModule());

    CommandLineValues commandLineValues;

    public static void main(String args[]) {
        new Main(args);
    }

    public Main(String[] args) {
        commandLineValues = new CommandLineValues(args);
        processImageExtraction();
        processFeatureExtractions();

    }

    private void processImageExtraction() {
        if (commandLineValues.isExtractImage()) {
            ImageLoader loader = injector.getInstance(ImageLoader.class);
            loader.loadFilesFromFolder(commandLineValues.getPathImage());
        }
    }

    private void processFeatureExtractions() {

        ImagesDao imagesDao = injector.getInstance(ImagesDao.class);

        if (commandLineValues.isExtractFeatures()) {
            List<Extractors> extractors = fetchExtractors();

            for (Images image : imagesDao.findAll()) {
                for (Extractors extractorsIt : extractors)
                    extractFeature(image, extractorsIt);
            }
        }
    }


    private List<Extractors> fetchExtractors() {

        ExtractorsDao extractorsDao = injector.getInstance(ExtractorsDao.class);

        List<Extractors> extractors = new ArrayList<>();
        if (commandLineValues.isAllExtractors()) {

            extractors = extractorsDao.findAll();

        } else if (commandLineValues.getExtractorId() != CommandLineValues.INVALID_ID) {

            int extractorId = commandLineValues.getExtractorId();
            Extractors extractor = extractorsDao.findById(extractorId);

            if (extractor == null)
                throw new RuntimeException("Id " + extractorId + " of extractor not found in the database");

            extractors.add(extractor);
        }

        return extractors;
    }

    private void extractFeature(Images image, Extractors extractor) {
        FeatureExtractionLoaderFactory factory = injector.getInstance(FeatureExtractionLoaderFactory.class);
        FeatureExtractor featureExtractionLoader = ExtractorUtils.getFeatureExtractorImplementation(extractor);

        FeatureExtractionLoader loader = factory.create(extractor, featureExtractionLoader);
        loader.extractFeatures(image);
    }

}
