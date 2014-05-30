package br.uel.mdd;

import br.uel.mdd.dao.ExtractorsDao;
import br.uel.mdd.db.jdbc.PostgresConnectionFactory;
import br.uel.mdd.db.tables.pojos.Extractors;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.jooq.Configuration;
import org.jooq.SQLDialect;
import org.jooq.impl.DefaultConfiguration;

import java.sql.Connection;

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

        Injector injector = Guice.createInjector(new ExtractorModule(extractor));
        ExtractFeatureDatabase efd = injector.getInstance(ExtractFeatureDatabase.class);

        efd.extractFeatures();
    }
}
