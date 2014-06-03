package br.uel.mdd.dao;

import br.uel.mdd.db.tables.pojos.Datasets;
import br.uel.mdd.db.tables.records.DatasetsRecord;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.impl.DAOImpl;
import org.jooq.impl.DSL;

import static br.uel.mdd.db.Sequences.DATASETS_ID_SEQ;
import static br.uel.mdd.db.tables.DatasetClasses.DATASET_CLASSES;
import static br.uel.mdd.db.tables.Datasets.DATASETS;
import static br.uel.mdd.db.tables.Extractions.EXTRACTIONS;
import static br.uel.mdd.db.tables.Images.IMAGES;

/**
 * @author ${user}
 * @TODO Auto-generated comment
 * <p/>
 * Created by pedro on 30/05/14.
 */
public class DatasetsDao extends DAOImpl<DatasetsRecord, Datasets, Integer> {

    public DatasetsDao(Configuration configuration) {
        super(DATASETS, Datasets.class, configuration);
    }

    @Override
    protected Integer getId(Datasets object) {
        return object.getId();
    }

    public void insertNullPk(Datasets dataset) {
        long id = DSL.using(this.configuration()).nextval(DATASETS_ID_SEQ);
        dataset.setId((int) id);
        this.insert(dataset);
    }

    public Datasets fetchByImageId(Integer imageId) {

        DSLContext create = DSL.using(this.configuration());
        return create.select(DATASETS.fields())
                .from(DATASETS)
                .join(DATASET_CLASSES).onKey()
                .join(IMAGES).onKey()
                .where(
                        IMAGES.ID.equal(imageId)
                )
                .fetchOneInto(Datasets.class);
    }

    public Datasets fetchByExtractionId(Integer id) {

        DSLContext create = DSL.using(this.configuration());

        return create.select(DATASETS.fields())
                .from(DATASETS)
                .join(DATASET_CLASSES)
                .on(DATASET_CLASSES.DATASET_ID.equal(DATASETS.ID))
                .join(IMAGES)
                .on(IMAGES.DATASET_CLASS_ID.equal(DATASET_CLASSES.ID))
                .join(EXTRACTIONS).on(EXTRACTIONS.IMAGE_ID.equal(IMAGES.ID))
                .where(
                        EXTRACTIONS.ID.equal(id)
                )
                .fetchOneInto(Datasets.class);
    }
}
