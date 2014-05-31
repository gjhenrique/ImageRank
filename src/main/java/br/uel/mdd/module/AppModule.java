package br.uel.mdd.module;

import br.uel.mdd.dao.ExtractionsDao;
import br.uel.mdd.db.jdbc.PostgresConnectionFactory;
import com.google.inject.AbstractModule;
import org.jooq.Configuration;
import org.jooq.SQLDialect;
import org.jooq.impl.DefaultConfiguration;

import java.sql.Connection;

public class AppModule extends AbstractModule {

    @Override
    protected void configure() {
        Connection connection = new PostgresConnectionFactory().getConnection();
        Configuration configuration = new DefaultConfiguration().set(connection).set(SQLDialect.POSTGRES);

        bind(ExtractionsDao.class).toInstance(new ExtractionsDao(configuration));

    }
}
