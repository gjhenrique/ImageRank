package br.uel.mdd.avaliation;


import br.uel.mdd.avaliation.plot.GnuPlot;
import br.uel.mdd.avaliation.plot.Plot;
import br.uel.mdd.dao.DistanceFunctionsDao;
import br.uel.mdd.dao.ExtractorsDao;
import br.uel.mdd.dao.QueriesDao;
import br.uel.mdd.db.tables.pojos.DistanceFunctions;
import br.uel.mdd.db.tables.pojos.Extractors;
import br.uel.mdd.db.tables.pojos.PrecisionRecall;
import br.uel.mdd.extractor.FeatureExtractor;
import br.uel.mdd.metric.MetricEvaluator;
import br.uel.mdd.utils.DistanceFunctionUtils;
import br.uel.mdd.utils.ExtractorUtils;
import com.google.inject.Inject;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PrecisionRecallEvaluator {

    private QueriesDao queriesDao;

    private ExtractorsDao extractorsDao;

    private DistanceFunctionsDao distanceFunctionsDao;

    @Inject
    public PrecisionRecallEvaluator(QueriesDao queriesDao, ExtractorsDao extractorsDao, DistanceFunctionsDao distanceFunctionsDao) {
        this.queriesDao = queriesDao;
        this.extractorsDao = extractorsDao;
        this.distanceFunctionsDao = distanceFunctionsDao;
    }

    public List<PrecisionRecall> precisionRecallByExtractors(Integer distanceFunctionId, Integer... extractorsId) {
        return queriesDao.precisionRecallByDistanceFunctionId(distanceFunctionId, extractorsId);
    }

    public List<PrecisionRecall> precisionRecallByDistanceFunction(Integer extractorId, Integer ... distanceFunctionsId) {
        return queriesDao.precisionRecallByExtractorId(extractorId, distanceFunctionsId);
    }

    public void plotChartByExtractors(List<PrecisionRecall> precisionRecalls) {
        Plot plot = new GnuPlot();

        for (PrecisionRecall precisionRecall : precisionRecalls) {
            Extractors extractors = extractorsDao.findById(precisionRecall.getId());
            FeatureExtractor featureExtractor = ExtractorUtils.getFeatureExtractorImplementation(extractors);
            plot.addValue(featureExtractor.toString(), precisionRecall.getRecall(), precisionRecall.getPrecision());
        }

        Set<PrecisionRecall> prs = new HashSet<>(precisionRecalls);

        for (PrecisionRecall precisionRecall : prs) {
            Extractors extractors = extractorsDao.findById(precisionRecall.getId());
            FeatureExtractor featureExtractor = ExtractorUtils.getFeatureExtractorImplementation(extractors);
            plot.addArbitrary(featureExtractor.toString(), 0, 0.0, 1.0);
        }

        plot.plot();
    }

    public void plotChartByDistanceFunction(List<PrecisionRecall> precisionRecalls) {

        Plot plot = new GnuPlot();

        for (PrecisionRecall precisionRecall : precisionRecalls) {
            DistanceFunctions distanceFunctions = distanceFunctionsDao.findById(precisionRecall.getId());
            MetricEvaluator metricEvaluator = DistanceFunctionUtils.getMetricEvaluatorFromDistanceFunction(distanceFunctions);
            plot.addValue(metricEvaluator.toString(), precisionRecall.getRecall(), precisionRecall.getPrecision());
        }

        Set<PrecisionRecall> prs = new HashSet<>(precisionRecalls);

        for (PrecisionRecall precisionRecall : prs) {
            DistanceFunctions distanceFunctions = distanceFunctionsDao.findById(precisionRecall.getId());
            MetricEvaluator metricEvaluator = DistanceFunctionUtils.getMetricEvaluatorFromDistanceFunction(distanceFunctions);
            plot.addArbitrary(metricEvaluator.toString(), 0, 0.0, 1.0);
        }

        plot.plot();
    }

}
