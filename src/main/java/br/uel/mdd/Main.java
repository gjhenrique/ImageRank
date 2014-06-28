package br.uel.mdd;

import br.uel.mdd.io.loading.ImageLoader;
import br.uel.mdd.module.AppModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * Main class that peforms Knn-queries
 */
public class Main {

    private CommandLineValues commandLineValues;

    private Injector appInjector = Guice.createInjector(new AppModule());

//    private Injector queryInjector = Guice.createInjector(new QueryModule(null));
//    private Injector extractorInjector = Guice.createInjector(new ExtractorModule(null));

    public static void main(String args[]) {
        new Main("-img -imgspath /home/guilherme/Documents/imgs/Pulmao".split(" "));
    }

    public Main(String[] args) {

        commandLineValues = new CommandLineValues(args);
        if (commandLineValues.isExtractImage()) {
            extractImages();
        }
    }

    private void extractImages() {
        ImageLoader imageLoader = appInjector.getInstance(ImageLoader.class);
        imageLoader.loadFilesFromFolder(commandLineValues.getPathImage());
    }

}
