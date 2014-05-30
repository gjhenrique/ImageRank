package br.uel.mdd.dao;

import br.uel.mdd.db.tables.records.ExperimentosRecord;
import br.uel.mdd.model.Experimentos;
import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;

import static br.uel.mdd.db.tables.Experimentos.EXPERIMENTOS;

public class ExperimentosDao extends DAOImpl<ExperimentosRecord, Experimentos, Long> {

    public ExperimentosDao(Configuration configuration) {
        super(EXPERIMENTOS, Experimentos.class, configuration);
    }

    @Override
    protected Long getId(Experimentos object) {
        return object.getId();
    }
}
