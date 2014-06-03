package br.uel.mdd.dao;

import br.uel.mdd.db.tables.pojos.ClassImage;
import br.uel.mdd.db.tables.pojos.DatasetClasses;
import br.uel.mdd.db.tables.pojos.Datasets;
import br.uel.mdd.db.tables.records.DatasetClassesRecord;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.impl.DAOImpl;
import org.jooq.impl.DSL;

import static br.uel.mdd.db.Sequences.DATASET_CLASSES_ID_SEQ;
import static br.uel.mdd.db.tables.DatasetClasses.DATASET_CLASSES;

/**
 * @author ${user}
 * @TODO Auto-generated comment
 * <p/>
 * Created by pedro on 30/05/14.
 */
public class DatasetClassesDao extends DAOImpl<DatasetClassesRecord, br.uel.mdd.db.tables.pojos.DatasetClasses, Integer>{


    public DatasetClassesDao(Configuration configuration) {
        super(DATASET_CLASSES, br.uel.mdd.db.tables.pojos.DatasetClasses.class, configuration);
    }

    @Override
    protected Integer getId(br.uel.mdd.db.tables.pojos.DatasetClasses object) {
        return object.getId();
    }

    public DatasetClasses insert(Datasets dataset, ClassImage classImage){
        DatasetClasses dc = new DatasetClasses();
        dc.setDatasetId(dataset.getId());
        dc.setClassId(classImage.getId());
        this.insertNullPk(dc);
        return dc;
    }

    public void insertNullPk(DatasetClasses datasetClasses){
        long id = DSL.using(this.configuration()).nextval(DATASET_CLASSES_ID_SEQ);
        datasetClasses.setId((int) id);
        this.insert(datasetClasses);
    }

    public DatasetClasses fetchByClassAndDataset(ClassImage iclass, Datasets datasets) {
        DSLContext create = DSL.using(this.configuration());
        return create.select(DATASET_CLASSES.fields())
                .from(DATASET_CLASSES)
                .where(DATASET_CLASSES.CLASS_ID.equal(iclass.getId())
                                .and(DATASET_CLASSES.DATASET_ID.equal(datasets.getId()))
                )
                .fetchOneInto(DatasetClasses.class);
    }
}
