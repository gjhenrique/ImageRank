package br.uel.mdd.avaliation;


import br.uel.mdd.dao.QueriesDao;
import com.google.inject.Inject;

import java.util.List;

public class PrecisionRecallEvaluator {

    private QueriesDao queriesDao;

    @Inject
    public PrecisionRecallEvaluator(QueriesDao queriesDao) {
        this.queriesDao = queriesDao;
    }

    public List<PrecisionRecall> precisionRecallByExtractors(Integer distanceFunctionId, Integer... extractorsId) {
        return queriesDao.precisionRecallByDistanceFunctionId(distanceFunctionId, extractorsId);
    }

}
