package br.uel.mdd.avaliation;

import br.uel.cross.parallel.mams.SlimTree;
import br.uel.cross.parallel.mams.pagemanager.DiskPageManager;
import br.uel.cross.parallel.mams.pagemanager.PageManager;
import br.uel.mdd.dao.DatasetsDao;
import br.uel.mdd.dao.DistanceFunctionsDao;
import br.uel.mdd.db.tables.pojos.Datasets;
import br.uel.mdd.db.tables.pojos.DistanceFunctions;
import br.uel.mdd.db.tables.pojos.Extractions;
import br.uel.mdd.metric.MetricEvaluator;
import br.uel.mdd.utils.DistanceFunctionUtils;
import br.uel.mdd.utils.PrimitiveUtils;
import com.google.inject.Inject;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SlimTreeWrapper implements Index {

    private final static String INDEX_NAME = "slim-idx";
    private final static String SEPARATOR = "-";
    private final static int BLOCK_SIZE = 16384;

    private File rootIndexPath;

    private Map<String, SlimTree> indexes = new HashMap<>();

    private DistanceFunctionsDao distanceFunctionsDao;

    private DatasetsDao datasetsDao;

    @Inject
    public SlimTreeWrapper(DistanceFunctionsDao distanceFunctionsDao, DatasetsDao datasetsDao) {

        String folder = getClass().getResource("/").getFile();

        rootIndexPath = new File(folder + File.separator + INDEX_NAME);
        if (!rootIndexPath.exists()) {
            if (!rootIndexPath.mkdir())
                throw new RuntimeException("Unable to create index directory");
        }

        this.distanceFunctionsDao = distanceFunctionsDao;
        this.datasetsDao = datasetsDao;
    }

    @Override
    public void addEntry(Extractions extractions) {

        List<DistanceFunctions> distanceFunctions = distanceFunctionsDao.findAll();
        for (DistanceFunctions distanceFunction : distanceFunctions) {

            MetricEvaluator metricEvaluator = DistanceFunctionUtils.getMetricEvaluatorFromDistanceFunction(distanceFunction);
            SlimTree slimTree = findSlimTree(extractions, metricEvaluator);

//            Index still not exists
            if (slimTree == null)
                slimTree = createSlimTree(extractions, metricEvaluator);

            float[] features = PrimitiveUtils.castWrapperToPrimitive(extractions.getExtractionData());

            slimTree.add(features, extractions.getId());
        }
    }

    private SlimTree createSlimTree(Extractions extractions, MetricEvaluator metricEvaluator) {

        String absolutePathIndex = getIndexAbsolutePath(extractions, metricEvaluator);
        return buildNewSlimTree(absolutePathIndex, metricEvaluator);
    }

    private String getIndexAbsolutePath(Extractions extractions, MetricEvaluator metricEvaluator) {

        Datasets dataset = datasetsDao.fetchByExtractionId(extractions.getId());
        String indexName = dataset.getName() + SEPARATOR + extractions.getExtractorId() + SEPARATOR + metricEvaluator.getClass().getSimpleName();
        return rootIndexPath.getAbsolutePath() + File.separator + indexName + ".idx";
    }

    public SlimTree findSlimTree(Extractions extractions, MetricEvaluator metricEvaluator) {
//        Retrieving from "cache"
        SlimTree slimTree = indexes.get(getIndexAbsolutePath(extractions, metricEvaluator));

//        Trying to load from existing index
        if (slimTree == null) {

            String absolutePathIndex = getIndexAbsolutePath(extractions, metricEvaluator);
            File idx = new File(absolutePathIndex);
            slimTree = (idx.exists()) ? fetchSlimTree(absolutePathIndex, metricEvaluator) : null;
        }

        return slimTree;
    }

    private SlimTree buildNewSlimTree(String path, MetricEvaluator metricEvaluator) {
        PageManager pageManager = new DiskPageManager(path, BLOCK_SIZE);
        return buildSlimTree(pageManager, path, metricEvaluator);
    }

    private SlimTree fetchSlimTree(String path, MetricEvaluator metricEvaluator) {
        PageManager pageManager = new DiskPageManager(path);
        return buildSlimTree(pageManager, path, metricEvaluator);
    }

    private SlimTree buildSlimTree(PageManager pageManager, String path, MetricEvaluator metricEvaluator) {
        MetricEvaluatorAdapter adapter = new MetricEvaluatorAdapter(metricEvaluator);
        SlimTree newSlimTree = new SlimTree(pageManager, adapter);

//        Puting in the "cache"
        indexes.put(path, newSlimTree);

        return newSlimTree;
    }


}
