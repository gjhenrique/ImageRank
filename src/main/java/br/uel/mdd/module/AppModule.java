package br.uel.mdd.module;

import br.uel.mdd.dao.*;
import br.uel.mdd.db.jdbc.ConnectionFactory;
import br.uel.mdd.db.jdbc.PostgresConnectionFactory;
import br.uel.mdd.io.loading.FeatureExtractionLoader;
import br.uel.mdd.io.loading.FeatureExtractionLoaderImpl;
import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import org.jooq.Configuration;
import org.jooq.SQLDialect;
import org.jooq.impl.DefaultConfiguration;

import java.sql.Connection;

public class AppModule extends AbstractModule {

    @Override
    protected void configure() {
        Connection connection = new PostgresConnectionFactory().getConnection();
        Configuration configuration = new DefaultConfiguration().set(connection).set(SQLDialect.POSTGRES);

        bind(ConnectionFactory.class).to(PostgresConnectionFactory.class);

        bind(ClassImageDao.class).toInstance(new ClassImageDao(configuration));
        bind(DatasetClassesDao.class).toInstance(new DatasetClassesDao(configuration));
        bind(DatasetsDao.class).toInstance(new DatasetsDao(configuration));
        bind(ExtractionsDao.class).toInstance(new ExtractionsDao(configuration));
        bind(ExtractorsDao.class).toInstance(new ExtractorsDao(configuration));
        bind(ImagesDao.class).toInstance(new ImagesDao(configuration));
        bind(QueriesDao.class).toInstance(new QueriesDao(configuration));
        bind(QueryResultsDao.class).toInstance(new QueryResultsDao(configuration));
        bind(DistanceFunctionsDao.class).toInstance(new DistanceFunctionsDao(configuration));

//        FeatureExtractionFactory Injection
        install(new FactoryModuleBuilder().
                implement(FeatureExtractionLoader.class, FeatureExtractionLoaderImpl.class).
                build(FeatureExtractionLoaderFactory.class));
    }
}
