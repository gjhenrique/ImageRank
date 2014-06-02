package br.uel.mdd.dao;

import br.uel.mdd.db.tables.pojos.QueryResults;
import br.uel.mdd.db.tables.records.QueryResultsRecord;
import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;
import org.jooq.impl.DSL;

import static br.uel.mdd.db.Sequences.QUERY_RESULTS_ID_SEQ;
import static br.uel.mdd.db.tables.QueryResults.QUERY_RESULTS;

/**
 * @author ${user}
 * @TODO Auto-generated comment
 * <p/>
 * Created by pedro on 02/06/14.
 */
public class QueryResultsDao extends DAOImpl<QueryResultsRecord, QueryResults, Integer>{
    public QueryResultsDao(Configuration configuration) {
        super(QUERY_RESULTS, QueryResults.class, configuration);
    }

    @Override
    protected Integer getId(QueryResults object) {
        return object.getId();
    }

    public void insertNullPk(QueryResults queryResults){
        long id = DSL.using(this.configuration()).nextval(QUERY_RESULTS_ID_SEQ);
        queryResults.setId((int) id);
        this.insert(queryResults);
    }
}
