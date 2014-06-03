package br.uel.mdd.dao;

import br.uel.mdd.db.tables.pojos.ClassImage;
import br.uel.mdd.db.tables.pojos.Datasets;
import br.uel.mdd.db.tables.records.ClassImageRecord;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.impl.DAOImpl;
import org.jooq.impl.DSL;

import java.security.InvalidParameterException;
import java.util.List;

import static br.uel.mdd.db.Sequences.CLASS_IMAGE_ID_SEQ;

import static br.uel.mdd.db.tables.ClassImage.*;
import static br.uel.mdd.db.tables.DatasetClasses.*;

/**
 * @author ${user}
 * @TODO Auto-generated comment
 * <p/>
 * Created by pedro on 30/05/14.
 */
public class ClassImageDao extends DAOImpl<ClassImageRecord, ClassImage, Integer> {
    public ClassImageDao(Configuration configuration) {
        super(br.uel.mdd.db.tables.ClassImage.CLASS_IMAGE, ClassImage.class, configuration);
    }

    @Override
    protected Integer getId(ClassImage object) {
        return object.getId();
    }

    public void insertNullPk(ClassImage classImage) {
        long id = DSL.using(this.configuration()).nextval(CLASS_IMAGE_ID_SEQ);
        if (id > Integer.MAX_VALUE) throw new InvalidParameterException("Id is too large for integer");
        classImage.setId((int) id);
        this.insert(classImage);
    }

    public List<ClassImage> fetchByDataset(Datasets dataset){
       if(dataset.getId() != null) {
            DSLContext create = DSL.using(this.configuration());
            return create.select(CLASS_IMAGE.fields())
                    .from(CLASS_IMAGE)
                    .join(DATASET_CLASSES)
                    .onKey()
                    .where(DATASET_CLASSES.DATASET_ID.equal(dataset.getId())).fetch().into(ClassImage.class);
        }
        return null;
    }

    /**
     * Returns the Image class that match with the conditions.<br/>
     * Only one is returned because the class name should be unique per dataset.
     * @param dataset The Dataset POJO
     * @param classImage The name of the class
     * @return A POJO with data if there is any record, null otherwise
     */
    public ClassImage fetchByDatasetAndClassName(Datasets dataset, String classImage){
        if(dataset.getId() != null && (classImage != null && !classImage.isEmpty())) {
            DSLContext create = DSL.using(this.configuration());
            return create.select(CLASS_IMAGE.fields())
                    .from(CLASS_IMAGE)
                    .join(DATASET_CLASSES)
                    .onKey()
                    .where(DATASET_CLASSES.DATASET_ID.equal(dataset.getId())
                                    .and(CLASS_IMAGE.NAME.equal(classImage))
                    )
                    .fetchOneInto(ClassImage.class);
        }
        return null;
    }

}
