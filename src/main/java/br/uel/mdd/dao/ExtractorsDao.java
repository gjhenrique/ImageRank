package br.uel.mdd.dao;

import br.uel.mdd.db.tables.pojos.Extractors;
import br.uel.mdd.db.tables.records.ExtractorsRecord;
import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;

public class ExtractorsDao extends DAOImpl<ExtractorsRecord, Extractors, Integer> {

    public ExtractorsDao(Configuration configuration) {
        super(br.uel.mdd.db.tables.Extractors.EXTRACTORS, Extractors.class, configuration);
    }

    @Override
    protected Integer getId(Extractors object) {
        return object.getId();
    }
}
