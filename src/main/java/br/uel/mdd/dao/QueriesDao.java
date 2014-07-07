package br.uel.mdd.dao;

import br.uel.mdd.avaliation.PrecisionRecall;
import br.uel.mdd.db.tables.pojos.Queries;
import br.uel.mdd.db.tables.records.QueriesRecord;
import org.jooq.Condition;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.Select;
import org.jooq.impl.DAOImpl;
import org.jooq.impl.DSL;

import java.util.List;

import static br.uel.mdd.db.Sequences.QUERIES_ID_SEQ;
import static br.uel.mdd.db.tables.DatasetClasses.DATASET_CLASSES;
import static br.uel.mdd.db.tables.Extractions.EXTRACTIONS;
import static br.uel.mdd.db.tables.Images.IMAGES;
import static br.uel.mdd.db.tables.Queries.QUERIES;

public class QueriesDao extends DAOImpl<QueriesRecord, Queries, Integer> {

    private DSLContext create = DSL.using(this.configuration());

    public QueriesDao(Configuration configuration) {
        super(QUERIES, Queries.class, configuration);
    }

    @Override
    protected Integer getId(Queries object) {
        return object.getId();
    }

    public void insertNullPk(Queries queries) {
        long id = DSL.using(this.configuration()).nextval(QUERIES_ID_SEQ);
        queries.setId((int) id);
        this.insert(queries);
    }

    public Queries fetchByExtractionIdAndDistanceFunctionIdAndK(int extractionId, int distanceFunctionId, int k) {

        return create.select(QUERIES.fields())
                .from(QUERIES)
                .where(
                        QUERIES.EXTRACTION_ID.equal(extractionId)
                                .and(QUERIES.DISTANCE_FUNCTION_ID.equal(distanceFunctionId))
                                .and(QUERIES.K.equal(k))
                ).fetchOneInto(Queries.class);
    }

    public List<PrecisionRecall> precisionRecallByDistanceFunctionId(Integer distanceFunctionId, Integer... extractorIds) {

        Condition condition = whereDistanceFunction(distanceFunctionId);
        
        if (extractorIds.length > 0)
            condition = condition.and(whereExtractorId(extractorIds));

        return performQuery(condition);
    }

    private Condition whereDistanceFunction(Integer... distanceFunctionId) {
        return QUERIES.DISTANCE_FUNCTION_ID.in(distanceFunctionId);
    }

    private Condition whereExtractorId(Integer... extractorId) {
        return EXTRACTIONS.EXTRACTOR_ID.in(extractorId);
    }

    public List<PrecisionRecall> performQuery(Condition condition) {

        Select select = getQueries(condition);
        getQueryResults(select);

        return null;
    }


    public Select getQueries(Condition condition) {
        return create.select(QUERIES.ID, EXTRACTIONS.ID, DATASET_CLASSES.ID, QUERIES.K, EXTRACTIONS.EXTRACTOR_ID)
                .from(EXTRACTIONS)
                .join(IMAGES).onKey()
                .join(DATASET_CLASSES).onKey()
                .where(condition);
    }

    public Select getQueryResults(Select queries) {
        create.select().from(queries);
        return null;
    }

}
