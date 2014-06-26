package br.uel.mdd;

import br.uel.mdd.dao.DistanceFunctionsDao;
import br.uel.mdd.dao.ExtractionsDao;
import br.uel.mdd.dao.ExtractorsDao;
import br.uel.mdd.db.tables.pojos.DistanceFunctions;
import br.uel.mdd.db.tables.pojos.Extractions;
import br.uel.mdd.db.tables.pojos.Extractors;
import br.uel.mdd.io.loading.QueryLoader;
import br.uel.mdd.module.AppModule;
import br.uel.mdd.module.QueryModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

import java.util.List;

import static br.uel.mdd.db.tables.Extractions.EXTRACTIONS;

/**
 * Main class that peforms Knn-queries
 */
public class Main {
    public static void main(String args[]) {

        Injector injector = Guice.createInjector(new AppModule());
        ExtractionsDao edao = injector.getInstance(ExtractionsDao.class);
        ExtractorsDao extractorsDao = injector.getInstance(ExtractorsDao.class);

        // For each extractor
        for (Extractors extractor : extractorsDao.findAll()) {
            // Check if the extractor have been used to extract features
            if (extractorsDao.hasExtractions(extractor.getId())) {
                // If there is extractions
                List<Extractions> extractions = edao.fetch(EXTRACTIONS.EXTRACTOR_ID, extractor.getId());
                for (Extractions extraction : extractions) {
                    // For each extraction do a number of Knn's
                    DistanceFunctionsDao dao = injector.getInstance(DistanceFunctionsDao.class);
                    DistanceFunctions distanceFunction = dao.findById(2);

                    injector = Guice.createInjector(new QueryModule(distanceFunction));
                    QueryLoader queryLoader = injector.getInstance(QueryLoader.class);

                    for (int j = 10; j < 40; j++) {
                        queryLoader.knn(extraction, j);
                    }
                }

            }
        }
    }
}
