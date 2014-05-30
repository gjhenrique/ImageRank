package br.uel.mdd.dao;

import br.uel.mdd.db.tables.pojos.Datasets;
import br.uel.mdd.db.tables.records.DatasetsRecord;
import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;

/**
 * @author ${user}
 * @TODO Auto-generated comment
 * <p/>
 * Created by pedro on 30/05/14.
 */
public class DatasetsDao extends DAOImpl<DatasetsRecord, Datasets, Integer> {

    public DatasetsDao(Configuration configuration) {
        super(br.uel.mdd.db.tables.Datasets.DATASETS, Datasets.class, configuration);
    }

    @Override
    protected Integer getId(Datasets object) {
        return object.getId();
    }
}
