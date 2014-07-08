package br.uel.mdd.avaliation;


import br.uel.mdd.avaliation.plot.GnuPlot;
import br.uel.mdd.avaliation.plot.Plot;
import br.uel.mdd.dao.ExtractorsDao;
import br.uel.mdd.dao.QueriesDao;
import br.uel.mdd.db.tables.pojos.Extractors;
import br.uel.mdd.db.tables.pojos.PrecisionRecall;
import br.uel.mdd.extractor.FeatureExtractor;
import br.uel.mdd.utils.ExtractorUtils;
import com.google.inject.Inject;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
            Extractors extractors = extractorsDao.findById(precisionRecall.getExtractorId());
            FeatureExtractor featureExtractor = ExtractorUtils.getFeatureExtractorImplementation(extractors);
            plot.addValue(featureExtractor.toString(), precisionRecall.getRecall(), precisionRecall.getPrecision());
        }

        Set<PrecisionRecall> prs = new HashSet<>(precisionRecalls);

        for (PrecisionRecall precisionRecall : prs) {
            Extractors extractors = extractorsDao.findById(precisionRecall.getExtractorId());
            FeatureExtractor featureExtractor = ExtractorUtils.getFeatureExtractorImplementation(extractors);
            plot.addArbitrary(featureExtractor.toString(), 0, 0.0, 1.0);
        }

        plot.plot();
    }

}
