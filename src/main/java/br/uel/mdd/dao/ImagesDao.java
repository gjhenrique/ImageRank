package br.uel.mdd.dao;

import br.uel.mdd.db.tables.pojos.DatasetClasses;
import br.uel.mdd.db.tables.pojos.Images;
import br.uel.mdd.db.tables.records.ImagesRecord;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.impl.DAOImpl;
import org.jooq.impl.DSL;

import java.util.List;

import static br.uel.mdd.db.Sequences.IMAGES_ID_SEQ;
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
        List<Images> images = create.select()
                .from(IMAGES)
                .where(IMAGES.FILE_NAME.equal(name)
                                .and(IMAGES.DATASET_CLASS_ID.equal(datasetClasses.getId()))
                )
                .fetch().into(Images.class);
        if(images.isEmpty()){
            return null;
        } else{
            return images.get(0);
        }
    }
}
