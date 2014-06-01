package br.uel.mdd;

import br.uel.mdd.dao.ExtractorsDao;
import br.uel.mdd.dao.ImagesDao;
import br.uel.mdd.db.jdbc.PostgresConnectionFactory;
import br.uel.mdd.db.tables.pojos.Extractors;
import br.uel.mdd.db.tables.pojos.Images;
import br.uel.mdd.io.loading.FeatureExtractionLoader;
import br.uel.mdd.module.ExtractorModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.jooq.Configuration;
import org.jooq.SQLDialect;
import org.jooq.impl.DefaultConfiguration;

import java.sql.Connection;
import java.util.List;

/**
 * @author ${user}
 * @TODO Auto-generated comment
 * <p/>
 * Created by pedro on 28/05/14.
 */
public class Main {

    public static void main(String args[]){

        Connection connection = new PostgresConnectionFactory().getConnection();
        Configuration configuration = new DefaultConfiguration().set(connection).set(SQLDialect.POSTGRES);

        ExtractorsDao dao = new ExtractorsDao(configuration);
        Extractors extractor = dao.findById(3);

        ImagesDao imagesDao = new ImagesDao(configuration);
        List<Images> images = imagesDao.findAll();

        Injector injector = Guice.createInjector(new ExtractorModule(extractor));
        FeatureExtractionLoader efd = injector.getInstance(FeatureExtractionLoader.class);

        efd.extractFeatures(images);
    }
}
