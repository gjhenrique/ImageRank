package br.uel.mdd.metric;

/**
 * Implementation of the Chebyschev Distance
 */
public class LinfDistance implements MetricEvaluator{
    @Override
    public double getDistance(Double[] obj1, Double[] obj2) {

        if (obj1.length == obj2.length){
            double max = 0;
            for (int i = 0; i < obj1.length; i++) {
                if( Math.abs(obj1[i]-obj2[i]) > max ){
                    max = Math.abs(obj1[i]-obj2[i]);
                }
            }
            return max;
        }else {
            throw new IllegalArgumentException("The vectors must have the same length");
        }
    }
}
