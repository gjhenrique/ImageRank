package br.uel.mdd.main;

import br.uel.mdd.io.loading.ImageLoader;
import br.uel.mdd.module.AppModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.kohsuke.args4j.Option;

import java.io.File;

public class CommandLineImageLoader extends CommandLineValues {

    @Option(name = "-img", aliases = {"--image-extraction"}, usage = "Extract the images from path")
    private boolean extractImage;

    @Option(name = "-imgspath", aliases = {"--images-path"}, usage = "Directories of images")
    private File pathImage;

    @Option(name = "-feature", aliases = {"--feature-extraction"}, usage = "Extract the features from image")
    private boolean extractFeatures;

    private Injector injector = Guice.createInjector(new AppModule());

    public CommandLineImageLoader(String... args) {
        super(args);
        if (extractImage) {
            if (!pathImage.isDirectory()) {
                throw new RuntimeException(pathImage + " is not a valid directory");
            }
        }
    }

    @Override
    protected void processArguments() {
        ImageLoader imageLoader = injector.getInstance(ImageLoader.class);
        imageLoader.loadFilesFromFolder(pathImage);
    }
}
