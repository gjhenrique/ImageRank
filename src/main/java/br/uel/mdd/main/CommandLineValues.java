package br.uel.mdd.main;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.File;

public class CommandLineValues {

    public static final int INVALID_ID = -1;

    @Option(name = "--image-extraction", usage = "Extract the images from path")
    private boolean extractImage;

    @Option(name = "-imgspath", aliases = {"--images-path"}, usage = "Directory of images")
    private File pathImage;

    @Option(name = "--feature-extraction", usage = "Extract the features from image")
    private boolean extractFeatures;

    @Option(name = "-all-ext", aliases = {"--all-extractors"}, usage = "Extract features with ALL the extractors of the database")
    private boolean allExtractors;

    @Option(name = "--extractor-feature-id", usage = "extractFeatures")
    private int extractorFeatureId = INVALID_ID;

    @Option(name = "--knn-queries", usage = "Perform knn queries in the extractions")
    private boolean knnQueries;

    @Option(name = "--all-extractions", usage = "Knn with all extractions")
    private boolean allExtractionsQuery;

    @Option(name = "--extractor-query-id", usage = "Query with all the extractions from this extractor")
    private int extractorQueryId = INVALID_ID;

    @Option(name = "--all-distance-functions", usage = "Knn with all the distance functions from the dataset")
    private boolean allDistanceFunctions;

    @Option(name = "--distance-function-id", usage = "Query with the distance of this dataset")
    private int distanceFunctionId = INVALID_ID;

    @Option(name = "--max-k", usage = "Boundary of the value of k")
    private int maxK = 100;

    @Option(name = "--rate-k", usage = "Ratio used in each iteration of k")
    private int rateK = 5;

    @Option(name = "-pr", aliases = {"--precision-recall"}, usage = "Print chart of precision recall")
    private boolean precisionRecall;

    @Option(name = "-pr-df-id", aliases = {"--precision-recall-distance-id"}, usage = "Distance function id")
    private int distanceIdPrecisionRecall;

    @Option(name = "-pr-e", aliases = {"--precision-recall-extractor-id"}, usage = "Ids of extractors separated by comma")
    private String extractorsPrecisionRecall;

    public CommandLineValues(String... args) {

        CmdLineParser parser = new CmdLineParser(this);
        parser.setUsageWidth(80);

        try {
            parser.parseArgument(args);

            if (args.length == 0)
                throw new CmdLineException(parser, "No argument is given");

            validateArguments(parser);

        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            parser.printUsage(System.err);
            System.exit(-1);
        }
    }

    private void validateArguments(CmdLineParser parser) throws CmdLineException {

        if (extractImage) {
            if (!pathImage.isDirectory()) {
                throw new CmdLineException(parser, pathImage + " is not a valid directory");
            }
        }

        if (extractFeatures) {
            if (extractorFeatureId == INVALID_ID && !(allExtractors)) {
                throw new CmdLineException(parser, "No extractor selected!!!");
            }
        }

        if (knnQueries) {
            if (extractorQueryId == INVALID_ID && !(allExtractionsQuery)) {
                throw new CmdLineException(parser, "No extractions selected!!!");
            }

            if (distanceFunctionId == INVALID_ID && !(allDistanceFunctions)) {
                throw new CmdLineException(parser, "No distance function selected!!!");
            }
        }
    }

    public boolean isExtractImage() {
        return extractImage;
    }

    public File getPathImage() {
        return pathImage;
    }

    public boolean isExtractFeatures() {
        return extractFeatures;
    }

    public boolean isAllExtractors() {
        return allExtractors;
    }

    public int getExtractorFeatureId() {
        return extractorFeatureId;
    }

    public boolean isKnnQueries() {
        return knnQueries;
    }

    public boolean isAllExtractionsQuery() {
        return allExtractionsQuery;
    }

    public int getExtractorQueryId() {
        return extractorQueryId;
    }

    public boolean isAllDistanceFunctions() {
        return allDistanceFunctions;
    }

    public int getDistanceFunctionId() {
        return distanceFunctionId;
    }

    public int getMaxK() {
        return maxK;
    }

    public int getRateK() {
        return rateK;
    }

    public boolean isPrecisionRecall() {
        return precisionRecall;
    }

    public int getDistanceIdPrecisionRecall() {
        return distanceIdPrecisionRecall;
    }

    public Integer[] getExtractorsPrecisionRecall() {
        String[] extractors = extractorsPrecisionRecall.split(",");
        Integer[] extractorsId = new Integer[extractors.length];
        for (int i = 0; i < extractors.length; i++) {
            extractorsId[i] = Integer.parseInt(extractors[i]);
        }

        return extractorsId;
    }

}
