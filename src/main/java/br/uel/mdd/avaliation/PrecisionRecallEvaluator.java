package br.uel.mdd.avaliation;


import br.uel.mdd.dao.ExtractorsDao;
import br.uel.mdd.dao.QueriesDao;
import br.uel.mdd.db.tables.pojos.Extractors;
import br.uel.mdd.extractor.FeatureExtractor;
import br.uel.mdd.utils.ExtractorUtils;
import com.google.inject.Inject;

import java.util.List;

public class PrecisionRecallEvaluator {

    private QueriesDao queriesDao;

    private ExtractorsDao extractorsDao;

    @Inject
    public PrecisionRecallEvaluator(QueriesDao queriesDao, ExtractorsDao extractorsDao) {
        this.queriesDao = queriesDao;
        this.extractorsDao = extractorsDao;
    }

    public List<PrecisionRecall> precisionRecallByExtractors(Integer distanceFunctionId, Integer... extractorsId) {
        return queriesDao.precisionRecallByDistanceFunctionId(distanceFunctionId, extractorsId);
    }

    public void plotChartByExtractors(List<PrecisionRecall> precisionRecalls) {
        Plot plot = new GnuPlot();

        for (PrecisionRecall precisionRecall : precisionRecalls) {
            Extractors extractors = extractorsDao.findById(precisionRecall.getId());
            FeatureExtractor featureExtractor = ExtractorUtils.getFeatureExtractorImplementation(extractors);
            plot.addValue(featureExtractor.toString(), precisionRecall.getRecall(), precisionRecall.getPrecision());
        }

        plot.plot();
    }

}
