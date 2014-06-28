package br.uel.mdd.module;

import br.uel.mdd.db.tables.pojos.DistanceFunctions;
import br.uel.mdd.metric.MetricEvaluator;
import br.uel.mdd.utils.ReflectionUtils;

/**
 * @author ${user}
 * @TODO Auto-generated comment
 * <p/>
 * Created by pedro on 02/06/14.
 */
public class QueryModule extends AppModule{

    private DistanceFunctions distanceFunction;

    public QueryModule(DistanceFunctions distanceFunction){
        this.distanceFunction = distanceFunction;
    }

    @Override
    public void configure(){
        super.configure();
        Class clazz = ReflectionUtils.findClassByName("br.uel.mdd.metric", distanceFunction.getClassName());
        this.bind(MetricEvaluator.class).to(clazz);
        this.bind(DistanceFunctions.class).toInstance(distanceFunction);
    }
}
