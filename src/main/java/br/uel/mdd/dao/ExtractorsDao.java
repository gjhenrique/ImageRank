package br.uel.mdd.dao;

import br.uel.mdd.db.tables.pojos.Extractors;
import br.uel.mdd.db.tables.records.ExtractorsRecord;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.impl.DAOImpl;
import org.jooq.impl.DSL;

import static br.uel.mdd.db.tables.Extractions.EXTRACTIONS;
import static br.uel.mdd.db.tables.Extractors.EXTRACTORS;

public class ExtractorsDao extends DAOImpl<ExtractorsRecord, Extractors, Integer> {

    public ExtractorsDao(Configuration configuration) {
        super(EXTRACTORS, Extractors.class, configuration);
    }

    @Override
    protected Integer getId(Extractors object) {
        return object.getId();
    }

    public boolean hasExtractions(Integer id) {

        DSLContext create = DSL.using(this.configuration());
        int count = create.fetchCount(create.select(EXTRACTIONS.fields())
                        .from(EXTRACTORS)
                        .join(EXTRACTIONS).onKey()
                        .where(EXTRACTIONS.EXTRACTOR_ID.equal(id))
        );

        return count > 0;
    }
}
