package br.uel.mdd.main;

/**
 * Main class that peforms Knn-queries
 */
public class Main {

    private CommandLineImageLoader commandLineValuesImageLoader;

    public static void main(String args[]) {
        new Main("-img -imgspath /home/guilherme/Documents/imgs/Pulmao".split(" "));
    }

    public Main(String[] args) {
        commandLineValuesImageLoader = new CommandLineImageLoader(args);
        commandLineValuesImageLoader.processArguments();
    }



}
