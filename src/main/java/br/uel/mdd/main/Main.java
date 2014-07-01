package br.uel.mdd.main;

import br.uel.mdd.dao.DistanceFunctionsDao;
import br.uel.mdd.dao.ExtractionsDao;
import br.uel.mdd.dao.ExtractorsDao;
import br.uel.mdd.dao.ImagesDao;
import br.uel.mdd.db.tables.pojos.DistanceFunctions;
import br.uel.mdd.db.tables.pojos.Extractions;
import br.uel.mdd.db.tables.pojos.Extractors;
import br.uel.mdd.db.tables.pojos.Images;
import br.uel.mdd.extractor.FeatureExtractor;
import br.uel.mdd.io.loading.FeatureExtractionLoader;
import br.uel.mdd.io.loading.ImageLoader;
import br.uel.mdd.io.loading.QueryLoader;
import br.uel.mdd.metric.MetricEvaluator;
import br.uel.mdd.module.AppModule;
import br.uel.mdd.module.FeatureExtractionLoaderFactory;
import br.uel.mdd.module.QueryLoaderFactory;
import br.uel.mdd.utils.DistanceFunctionUtils;
import br.uel.mdd.utils.ExtractorUtils;
import com.google.inject.Guice;
import com.google.inject.Injector;

import java.util.ArrayList;
import java.util.List;

import static br.uel.mdd.db.tables.DistanceFunctions.DISTANCE_FUNCTIONS;
import static br.uel.mdd.db.tables.Extractions.EXTRACTIONS;

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
        processQueryLoader();
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
        FeatureExtractor featureExtractionLoader = ExtractorUtils.getFeatureExtractorImplementation(extractor);

        FeatureExtractionLoader loader = factory.create(extractor, featureExtractionLoader);
        loader.extractFeatures(image);
    }

    private void processQueryLoader() {
        if (commandLineValues.isKnnQueries()) {
            List<DistanceFunctions> distanceFunctions = fetchDistanceFunctions();
            List<Extractions> extractions = fetchExtractions();

            for (DistanceFunctions distanceFunction : distanceFunctions) {
                for (Extractions extraction : extractions) {

                    QueryLoaderFactory factory = injector.getInstance(QueryLoaderFactory.class);
                    MetricEvaluator metricEvaluator = DistanceFunctionUtils.getMetricEvaluatorFromDistanceFunction(distanceFunction);
                    QueryLoader queryLoader = factory.create(metricEvaluator, distanceFunction);

                    for (int i = commandLineValues.getRateK(); i <= commandLineValues.getMaxK(); i += commandLineValues.getRateK()) {
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
