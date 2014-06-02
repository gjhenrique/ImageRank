package br.uel.mdd.dao;

import br.uel.mdd.db.tables.pojos.DistanceFunctions;
import br.uel.mdd.db.tables.records.DistanceFunctionsRecord;
import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;

import static br.uel.mdd.db.tables.DistanceFunctions.DISTANCE_FUNCTIONS;

/**
 * @author ${user}
 * @TODO Auto-generated comment
 * <p/>
 * Created by pedro on 02/06/14.
 */
public class DistanceFunctionsDao extends DAOImpl<DistanceFunctionsRecord, DistanceFunctions, Integer> {
    public DistanceFunctionsDao(Configuration configuration) {
        super(DISTANCE_FUNCTIONS, DistanceFunctions.class, configuration);
    }

    @Override
    protected Integer getId(DistanceFunctions object) {
        return object.getId();
    }




}
