package br.uel.mdd.dao;

import br.uel.mdd.db.tables.records.DatasetClassesRecord;
import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;

/**
 * @author ${user}
 * @TODO Auto-generated comment
 * <p/>
 * Created by pedro on 30/05/14.
 */
public class DatasetClassesDao extends DAOImpl<DatasetClassesRecord, br.uel.mdd.db.tables.pojos.DatasetClasses, Integer>{


    protected DatasetClassesDao(Configuration configuration) {
        super(br.uel.mdd.db.tables.DatasetClasses.DATASET_CLASSES, br.uel.mdd.db.tables.pojos.DatasetClasses.class, configuration);
    }

    @Override
    protected Integer getId(br.uel.mdd.db.tables.pojos.DatasetClasses object) {
        return object.getId();
    }
}
