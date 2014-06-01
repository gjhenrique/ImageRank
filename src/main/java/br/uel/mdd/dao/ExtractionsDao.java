package br.uel.mdd.dao;

import br.uel.mdd.db.tables.pojos.Extractions;
import br.uel.mdd.db.tables.records.ExtractionsRecord;
import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;
import org.jooq.impl.DSL;

import static br.uel.mdd.db.Sequences.EXTRACTIONS_ID_SEQ;
import static br.uel.mdd.db.tables.Extractions.EXTRACTIONS;

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
}