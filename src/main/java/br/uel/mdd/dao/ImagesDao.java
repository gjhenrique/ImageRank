package br.uel.mdd.dao;

import br.uel.mdd.db.tables.pojos.Images;
import br.uel.mdd.db.tables.records.ImagesRecord;
import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;

/**
 * @author ${user}
 * @TODO Auto-generated comment
 * <p/>
 * Created by pedro on 30/05/14.
 */
public class ImagesDao extends DAOImpl<ImagesRecord, Images, Integer>{


    public ImagesDao(Configuration configuration) {
        super(br.uel.mdd.db.tables.Images.IMAGES, Images.class, configuration);
    }

    @Override
    protected Integer getId(Images object) {
        return object.getId();
    }
}
