package br.uel.mdd;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.File;

public class CommandLineValues {

    @Option(name = "-img", aliases = {"--image-extraction"}, usage = "Extract the images from path")
    private boolean extractImage;

    @Option(name = "-imgspath", aliases = {"--images-path"}, usage = "Directories of images")
    private File pathImage;

    public CommandLineValues(String... values) {
        CmdLineParser parser = new CmdLineParser(this);
        parser.setUsageWidth(80);

        try {
            parser.parseArgument(values);

            if (extractImage) {
                if (!pathImage.isDirectory()) {
                    throw new CmdLineException(parser, pathImage + " is not a valid directory");
                }
            }
        } catch (CmdLineException e) {
            e.printStackTrace();
            parser.printUsage(System.err);
            System.exit(1);
        }
    }

    public boolean isExtractImage() {
        return extractImage;
    }

    public File getPathImage() {
        return pathImage;
    }
}
