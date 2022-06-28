package br.uel.mdd.main;

import br.uel.mdd.avaliation.PrecisionRecallEvaluator;
import br.uel.mdd.dao.DistanceFunctionsDao;
import br.uel.mdd.dao.ExtractionsDao;
import br.uel.mdd.dao.ExtractorsDao;
import br.uel.mdd.dao.ImagesDao;
import br.uel.mdd.db.tables.pojos.*;
import br.uel.mdd.io.loading.*;
import br.uel.mdd.module.AppModule;
import br.uel.mdd.module.FeatureExtractionLoaderFactory;
import br.uel.mdd.module.QueryLoaderFactory;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

import static br.uel.mdd.db.tables.DistanceFunctions.DISTANCE_FUNCTIONS;
import static br.uel.mdd.db.tables.Extractions.EXTRACTIONS;

public class Main {

    private Injector injector;

    CommandLineValues commandLineValues;

    private final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String args[]) {
//        args = ("--image-extraction --images-path " + args[0]).split(" ");
//      args = "--feature-extraction -all-ext".split(" ");
//        args = "--knn-queries --extractor-query-id 5 --all-distance-functions".split(" ");
//        args = "-pr -pr-df-id 3 -pr-e 5,6,7,8,101,102,110,111".split(" ");
        args = "--image-extraction --images-path src/test/resources/imgs/dicom/Pulmao --feature-extraction --all-extractors --all-extractions --all-distance-functions --precision-recall -pr-df-id 3 -pr-e 116,117,121".split(" ");

        new Main(args);
    }

    public Main(String[] args) {
        commandLineValues = new CommandLineValues(args);
        this.injector = Guice.createInjector(new AppModule(commandLineValues.isNoThreadExecutor()));
        processImageExtraction();
        processFeatureExtractions();
        processQueryLoader();
        processPrecisionRecall();
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

            FeatureExtractionLoaderFactory factory = injector.getInstance(FeatureExtractionLoaderFactory.class);

            for (Images image : imagesDao.findAll()) {
                for (Extractors extractorsIt : extractors) {
                    logger.info("Extraction {} of {}", ++currentExtraction, totalExtractions);

                    FeatureExtractionLoader loader = factory.create(extractorsIt);
                    loader.extractFeatures(image);
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

    private QueryLoaderDispatcher dispatcher;

    private void processQueryLoader() {
        if (commandLineValues.isKnnQueries()) {

            QueryLoaderFactory factory = injector.getInstance(QueryLoaderFactory.class);

            List<DistanceFunctions> distanceFunctions = fetchDistanceFunctions();
            List<Extractions> extractions = fetchExtractions();

            int maxK = commandLineValues.getMaxK();
            int rateK = commandLineValues.getRateK();
            final int totalQueries = distanceFunctions.size() * extractions.size() * (maxK / rateK);
            dispatcher = createQueryLoaderDispatcher(totalQueries);

            for (DistanceFunctions distanceFunction : distanceFunctions) {
                for (Extractions extraction : extractions) {
                    QueryLoader queryLoader = factory.create(distanceFunction);

                    for (int k = rateK; k <= maxK; k += rateK) {
                        dispatcher.runQuery(queryLoader, extraction, k);
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
            extractions = extractionsDao.fetch(EXTRACTIONS.EXTRACTOR_ID, commandLineValues.getExtractorQueryId());

        return extractions;
    }

    private QueryLoaderDispatcher createQueryLoaderDispatcher(final int totalQueries) {
        final AtomicInteger currentQuery = new AtomicInteger(0);

        ExecutorService executor = injector.getInstance(ExecutorService.class);

        return new QueryLoaderDispatcher(new QueryLoaderDispatcher.QueryLoaderListener() {
            @Override
            public void queryComplete() {
                int value = currentQuery.incrementAndGet();
                if (value % 1000 == 0 || value == totalQueries) {
                    logger.info("Query {} / {}", value, totalQueries);
                    System.out.println("Query " + value + " / " + totalQueries);
                    if(value == totalQueries)
                        dispatcher.shutdown();
                }
            }
        }, executor);
    }

    private void processPrecisionRecall() {
        if (commandLineValues.isPrecisionRecall()) {
            PrecisionRecallEvaluator evaluator = injector.getInstance(PrecisionRecallEvaluator.class);

            Integer[] distanceFunctionsId = fromStringToArray(commandLineValues.getDistanceIdPrecisionRecall());
            Integer[] extractorId = fromStringToArray(commandLineValues.getExtractorsPrecisionRecall());

            if(distanceFunctionsId.length == 1) {
                List<PrecisionRecall> precisionRecalls = evaluator.precisionRecallByExtractors(distanceFunctionsId[0], extractorId);
                evaluator.plotChartByExtractors(precisionRecalls);
            }
            else if(extractorId.length == 1) {
                List<PrecisionRecall> precisionRecalls = evaluator.precisionRecallByDistanceFunction(extractorId[0], distanceFunctionsId);
                evaluator.plotChartByDistanceFunction(precisionRecalls);
            }
        }
    }

    public Integer[] fromStringToArray (String ids) {
        if (ids != null) {
            String[] extractors = ids.split(",");
            Integer[] extractorsId = new Integer[extractors.length];
            for (int i = 0; i < extractors.length; i++) {
                extractorsId[i] = Integer.parseInt(extractors[i]);
            }

            return extractorsId;
        }
        return new Integer[0];
    }
}
