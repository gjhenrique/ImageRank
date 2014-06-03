package br.uel.mdd.dao;

import br.uel.mdd.db.tables.pojos.DatasetClasses;
import br.uel.mdd.db.tables.pojos.Datasets;
import br.uel.mdd.db.tables.pojos.Images;
import br.uel.mdd.db.tables.records.ImagesRecord;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.impl.DAOImpl;
import org.jooq.impl.DSL;

import static br.uel.mdd.db.Sequences.IMAGES_ID_SEQ;
import static br.uel.mdd.db.tables.DatasetClasses.DATASET_CLASSES;
import static br.uel.mdd.db.tables.Images.IMAGES;

/**
 * @author ${user}
 * @TODO Auto-generated comment
 * <p/>
 * Created by pedro on 30/05/14.
 */
public class ImagesDao extends DAOImpl<ImagesRecord, Images, Integer>{


    public ImagesDao(Configuration configuration) {
        super(IMAGES, Images.class, configuration);
    }

    @Override
    protected Integer getId(Images object) {
        return object.getId();
    }

    public void insertNullPk(Images images){
        long id = DSL.using(this.configuration()).nextval(IMAGES_ID_SEQ);
        images.setId((int) id);
        this.insert(images);
    }

    public Images fetchByFileNameAndDatasetClassId(String name, DatasetClasses datasetClasses) {
        DSLContext create = DSL.using(this.configuration());
        return create.select(IMAGES.fields())
                .from(IMAGES)
                .where(IMAGES.FILE_NAME.equal(name)
                                .and(IMAGES.DATASET_CLASS_ID.equal(datasetClasses.getId()))
                )
                .fetchOneInto(Images.class);
    }

    public long getCountByDataset(Datasets dataset) {
        DSLContext create = DSL.using(this.configuration());
        return create.selectCount()
                .from(IMAGES)
                .join(DATASET_CLASSES).onKey()
                .where(
                        DATASET_CLASSES.DATASET_ID.equal(dataset.getId())
                ).fetchOne(0, long.class);
    }
}
