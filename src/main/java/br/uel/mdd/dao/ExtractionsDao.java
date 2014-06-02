package br.uel.mdd.dao;

import br.uel.mdd.db.tables.pojos.Extractions;
import br.uel.mdd.db.tables.records.ExtractionsRecord;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.impl.DAOImpl;
import org.jooq.impl.DSL;

import java.util.List;

import static br.uel.mdd.db.Sequences.EXTRACTIONS_ID_SEQ;
import static br.uel.mdd.db.tables.DatasetClasses.DATASET_CLASSES;
import static br.uel.mdd.db.tables.Extractions.EXTRACTIONS;
import static br.uel.mdd.db.tables.Images.IMAGES;

public class ExtractionsDao extends DAOImpl<ExtractionsRecord, Extractions, Integer> {

    public ExtractionsDao(Configuration configuration) {
        super(EXTRACTIONS, Extractions.class, configuration);
    }

    @Override
    protected Integer getId(Extractions object) {
        return object.getId();
    }

    public void insertNullPk(Extractions extractions){
        long id = DSL.using(this.configuration()).nextval(EXTRACTIONS_ID_SEQ);
        extractions.setId((int) id);
        this.insert(extractions);
    }

    public List<Extractions> fetchByDatasetId(Integer datasetId){
        DSLContext create = DSL.using(this.configuration());
        return create.select(EXTRACTIONS.ID, EXTRACTIONS.EXTRACTION_DATA, EXTRACTIONS.IMAGE_ID, EXTRACTIONS.EXTRACTOR_ID)
                .from(DATASET_CLASSES)
                .join(IMAGES)
                .on(IMAGES.DATASET_CLASS_ID.equal(DATASET_CLASSES.ID))
                .join(EXTRACTIONS)
                .on(EXTRACTIONS.IMAGE_ID.equal(IMAGES.ID))
                .where(
                        DATASET_CLASSES.DATASET_ID.equal(datasetId)
                ).fetchInto(Extractions.class);
    }


    public List<Extractions> fetchByDatasetIdAndExtractorId(Integer datasetId, Integer extractorId) {
        DSLContext create = DSL.using(this.configuration());
        return create.select(EXTRACTIONS.ID, EXTRACTIONS.EXTRACTION_DATA, EXTRACTIONS.IMAGE_ID, EXTRACTIONS.EXTRACTOR_ID)
                .from(DATASET_CLASSES)
                .join(IMAGES)
                .on(IMAGES.DATASET_CLASS_ID.equal(DATASET_CLASSES.ID))
                .join(EXTRACTIONS)
                .on(EXTRACTIONS.IMAGE_ID.equal(IMAGES.ID))
                .where(
                        DATASET_CLASSES.DATASET_ID.equal(datasetId)
                                .and(EXTRACTIONS.EXTRACTOR_ID.equal(extractorId))
                ).fetchInto(Extractions.class);
    }
}
