package br.uel.mdd.dao;

import br.uel.mdd.db.tables.pojos.Datasets;
import br.uel.mdd.db.tables.records.DatasetsRecord;
import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;
import org.jooq.impl.DSL;

import static br.uel.mdd.db.Sequences.DATASETS_ID_SEQ;

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

    public void insertNullPk(Datasets dataset){
        long id = DSL.using(this.configuration()).nextval(DATASETS_ID_SEQ);
        dataset.setId((int) id);
        this.insert(dataset);
    }

}
