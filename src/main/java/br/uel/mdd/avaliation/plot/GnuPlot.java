package br.uel.mdd.avaliation.plot;

import com.panayotis.gnuplot.JavaPlot;
import com.panayotis.gnuplot.dataset.DataSet;
import com.panayotis.gnuplot.dataset.PointDataSet;
import com.panayotis.gnuplot.plot.AbstractPlot;
import com.panayotis.gnuplot.plot.DataSetPlot;
import com.panayotis.gnuplot.style.NamedPlotColor;
import com.panayotis.gnuplot.style.PlotColor;
import com.panayotis.gnuplot.style.PlotStyle;
import com.panayotis.gnuplot.style.Style;

import java.util.*;

public class GnuPlot implements Plot {

    //    double[] is represented as a point
    private Map<String, List<Point>> values = new HashMap<>();

    private JavaPlot plot = new JavaPlot();

    private PlotColor[] colors = new NamedPlotColor[] {NamedPlotColor.BLUE, NamedPlotColor.BLACK, NamedPlotColor.RED, NamedPlotColor.BROWN, NamedPlotColor.GREEN};
    private int currentColor;

    private int[] lineTypes= new int[] {2, 5, 6, 10, 13};
    private int currentLineTypes;

    public GnuPlot() {
        initializePlot();
    }

    private void initializePlot() {
        plot.setKey(JavaPlot.Key.BELOW);
        plot.getAxis("x").setBoundaries(0.0, 1.0);
        plot.getAxis("y").setBoundaries(0.0, 1.0);
        plot.getAxis("y").setLabel("Precision");
        plot.getAxis("x").setLabel("Recall");
    }

    @Override
    public void addValue(String key, double x, double y) {
//        Initializing values
        if (values.get(key) == null) {
            values.put(key, new ArrayList<Point>());
        }

        List<Point> valuesKey = values.get(key);
        Point point = new Point(x, y);
        valuesKey.add(point);
    }

    @Override
    public boolean addArbitrary(String key, int position, double x, double y){
        if (values.get(key) == null) { // The key must exist
            return false;
        }

        List<Point> valuesKey = values.get(key); // Reference of object
        Point point = new Point(x, y);
        valuesKey.add(position, point);
        return true;
    }


    @Override
    public void plot() {
        Set<String> keysSet = values.keySet();

        for (String key : keysSet) {

            DataSet dataSet = createDataset(values.get(key));

            AbstractPlot legend = createLegend(key, dataSet);

            plot.addPlot(legend);

        }

        plot.plot();
    }

    private DataSet createDataset(List<Point> points) {
        PointDataSet<Double> dataSet = new PointDataSet<>();

        for (Point point : points) {
            com.panayotis.gnuplot.dataset.Point<Double> pointGnuPlot = new com.panayotis.gnuplot.dataset.Point<Double>(point.asObjectArray());
            dataSet.add(pointGnuPlot);
        }

        return dataSet;
    }

    private AbstractPlot createLegend(String legendName, DataSet dataSet) {

        AbstractPlot legend = new DataSetPlot(dataSet);

        legend.setTitle(legendName);
        PlotStyle style = legend.getPlotStyle();
        style.setStyle(Style.LINESPOINTS);
        style.setLineType(colors[currentColor++]);
        style.setPointType(lineTypes[currentLineTypes++]);
        style.setPointSize(1);
        style.setLineWidth(2);

        return legend;
    }

}
