package br.uel.mdd.main;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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

    @Option(name = "-extractor-id", usage = "extractFeatures")
    private int extractorId = INVALID_ID;


    @Argument
    private List<String> arguments = new ArrayList<String>();

    public CommandLineValues(String... args) {

        CmdLineParser parser = new CmdLineParser(this);
        parser.setUsageWidth(80);

        try {
            parser.parseArgument(args);

            if (arguments.isEmpty())
                throw new CmdLineException(parser, "No argument is given");

            validateArguments(parser);

        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            parser.printUsage(System.err);
            System.err.println();
            System.exit(-1);
        }
    }

    private void validateArguments(CmdLineParser parser) throws CmdLineException {

        if (extractImage) {
            if (!pathImage.isDirectory()) {
                throw new CmdLineException(parser, pathImage + " is not a valid directory");
            }
        }

        if(extractFeatures) {

            if(extractorId == INVALID_ID && !(allExtractors)) {
                throw new CmdLineException(parser, "No extractor selected!!!" +
                        "\n Select all-ext for all extractors or ext-id for one extractor only");
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

    public int getExtractorId() {
        return extractorId;
    }
}
