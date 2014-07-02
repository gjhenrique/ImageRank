package br.uel.mdd.dao;

import br.uel.mdd.db.tables.pojos.Queries;
import br.uel.mdd.db.tables.records.QueriesRecord;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.impl.DAOImpl;
import org.jooq.impl.DSL;

import static br.uel.mdd.db.Sequences.QUERIES_ID_SEQ;
import static br.uel.mdd.db.tables.Queries.QUERIES;

/**
 * @author ${user}
 * @TODO Auto-generated comment
 * <p/>
 * Created by pedro on 02/06/14.
 */
public class QueriesDao extends DAOImpl<QueriesRecord, Queries, Integer>{
    public  QueriesDao(Configuration configuration) {
        super(QUERIES, Queries.class, configuration);
    }

    @Override
    protected Integer getId(Queries object) {
        return object.getId();
    }

    public void insertNullPk(Queries queries){
        long id = DSL.using(this.configuration()).nextval(QUERIES_ID_SEQ);
        queries.setId((int) id);
        this.insert(queries);
    }

    public Queries fetchByExtractionIdAndDistanceFunctionIdAndK(int extractionId, int distanceFunctionId, int k) {
        DSLContext create = DSL.using(this.configuration());
        return create.select(QUERIES.fields())
                .from(QUERIES)
                .where(
                        QUERIES.EXTRACTION_ID.equal(extractionId)
                                .and(QUERIES.DISTANCE_FUNCTION_ID.equal(distanceFunctionId))
                        .and(QUERIES.K.equal(k))
                ).fetchOneInto(Queries.class);
    }

}
