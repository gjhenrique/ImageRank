package br.uel.mdd.dao;

import br.uel.mdd.db.tables.pojos.Queries;
import br.uel.mdd.db.tables.records.QueriesRecord;
import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;
import org.jooq.impl.DSL;

import static br.uel.mdd.db.Sequences.QUERIES_ID_SEQ;

/**
 * @author ${user}
 * @TODO Auto-generated comment
 * <p/>
 * Created by pedro on 02/06/14.
 */
public class QueriesDao extends DAOImpl<QueriesRecord, Queries, Integer>{
    public  QueriesDao(Configuration configuration) {
        super(br.uel.mdd.db.tables.Queries.QUERIES, Queries.class, configuration);
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

}
