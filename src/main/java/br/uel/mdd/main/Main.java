package br.uel.mdd.main;

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
import br.uel.mdd.module.FeatureExtractionLoaderFactory;
import br.uel.mdd.module.QueryLoaderFactory;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static br.uel.mdd.db.tables.DistanceFunctions.DISTANCE_FUNCTIONS;
import static br.uel.mdd.db.tables.Extractions.EXTRACTIONS;

public class Main {

    private Injector injector = Guice.createInjector(new AppModule());

    CommandLineValues commandLineValues;

    private final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String args[]) {
//        args = "--image-extraction --images-path /home/guilherme/Documents/imgs/Pulmao".split(" ");
      args = "--feature-extraction -all-ext".split(" ");
//        args = "--knn-queries --all-extractions --all-distance-functions".split(" ");
        new Main(args);
    }

    public Main(String[] args) {
        commandLineValues = new CommandLineValues(args);
        processImageExtraction();
        processFeatureExtractions();
        processQueryLoader();
    }


    private void processImageExtraction() {
        if (commandLineValues.isExtractImage()) {
            ImageLoader loader = injector.getInstance(ImageLoader.class);
            loader.loadFilesFromFolder(commandLineValues.getPathImage());
        }
    }

    private void processFeatureExtractions() {

        if (commandLineValues.isExtractFeatures()) {

            ImagesDao imagesDao = injector.getInstance(ImagesDao.class);

            List<Images> images = imagesDao.findAll();
            List<Extractors> extractors = fetchExtractors();

            int currentExtraction = 0;
            int totalExtractions = images.size() * extractors.size();

            for (Images image : imagesDao.findAll()) {
                for (Extractors extractorsIt : extractors) {
                    logger.info("Extraction {} of {}", ++currentExtraction, totalExtractions);
                    extractFeature(image, extractorsIt);
                }
            }
        }
    }

    private List<Extractors> fetchExtractors() {

        ExtractorsDao extractorsDao = injector.getInstance(ExtractorsDao.class);

        List<Extractors> extractors = new ArrayList<>();
        if (commandLineValues.isAllExtractors()) {

            extractors = extractorsDao.findAll();

        } else if (commandLineValues.getExtractorFeatureId() != CommandLineValues.INVALID_ID) {

            int extractorId = commandLineValues.getExtractorFeatureId();
            Extractors extractor = extractorsDao.findById(extractorId);

            if (extractor == null)
                throw new RuntimeException("Id " + extractorId + " of extractor not found in the database");

            extractors.add(extractor);
        }

        return extractors;
    }

    private void extractFeature(Images image, Extractors extractor) {
        FeatureExtractionLoaderFactory factory = injector.getInstance(FeatureExtractionLoaderFactory.class);

        FeatureExtractionLoader loader = factory.create(extractor);
        loader.extractFeatures(image);
    }

    private void processQueryLoader() {
        if (commandLineValues.isKnnQueries()) {
            List<DistanceFunctions> distanceFunctions = fetchDistanceFunctions();
            List<Extractions> extractions = fetchExtractions();

            int maxK = commandLineValues.getMaxK();
            int rateK = commandLineValues.getRateK();

            int currentQuery = 0;
            int totalQueries = distanceFunctions.size() * extractions.size() * (maxK / rateK);

            for (DistanceFunctions distanceFunction : distanceFunctions) {
                for (Extractions extraction : extractions) {

                    QueryLoaderFactory factory = injector.getInstance(QueryLoaderFactory.class);
                    QueryLoader queryLoader = factory.create(distanceFunction);

                    for (int i = rateK; i <= maxK; i += rateK) {
                        logger.info("Query {} / {}", ++currentQuery, totalQueries);
                        queryLoader.knn(extraction, i);
                    }
                }
            }
        }
    }

    private List<DistanceFunctions> fetchDistanceFunctions() {
        DistanceFunctionsDao distanceFunctionsDao = injector.getInstance(DistanceFunctionsDao.class);
        List<DistanceFunctions> distanceFunctions = null;

        if (commandLineValues.isAllDistanceFunctions())
            distanceFunctions = distanceFunctionsDao.findAll();
        else if (commandLineValues.getDistanceFunctionId() != CommandLineValues.INVALID_ID)
            distanceFunctions = distanceFunctionsDao.fetch(DISTANCE_FUNCTIONS.ID,
                    commandLineValues.getDistanceFunctionId());

        return distanceFunctions;
    }

    private List<Extractions> fetchExtractions() {
        ExtractionsDao extractionsDao = injector.getInstance(ExtractionsDao.class);
        List<Extractions> extractions = null;

        if (commandLineValues.isAllExtractionsQuery())
            extractions = extractionsDao.findAll();
        else if (commandLineValues.getExtractorQueryId() != CommandLineValues.INVALID_ID)
            extractionsDao.fetch(EXTRACTIONS.EXTRACTOR_ID, commandLineValues.getExtractorQueryId());

        return extractions;
    }


}
