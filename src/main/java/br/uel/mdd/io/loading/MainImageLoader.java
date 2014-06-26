package br.uel.mdd.io.loading;

import br.uel.mdd.module.AppModule;
import com.google.inject.Guice;
import com.google.inject.Injector;




public class MainImageLoader {

    public static void main(String[] args){
        Injector injector = Guice.createInjector(new AppModule());
        ImageLoader lil = injector.getInstance(ImageLoader.class);
        lil.loadFilesFromFolder(args[0]);
    }
}
