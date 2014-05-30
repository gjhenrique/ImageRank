package br.uel.mdd.dao;

import br.uel.mdd.db.tables.pojos.ClassImage;
import br.uel.mdd.db.tables.records.ClassImageRecord;
import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;

/**
 * @author ${user}
 * @TODO Auto-generated comment
 * <p/>
 * Created by pedro on 30/05/14.
 */
public class ClassImageDao extends DAOImpl<ClassImageRecord, ClassImage, Integer>{
    public ClassImageDao(Configuration configuration) {
        super(br.uel.mdd.db.tables.ClassImage.CLASS_IMAGE, ClassImage.class, configuration);
    }

    @Override
    protected Integer getId(ClassImage object) {
        return object.getId();
    }
}
