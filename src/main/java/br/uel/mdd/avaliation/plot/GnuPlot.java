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
import com.panayotis.gnuplot.terminal.PostscriptTerminal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class GnuPlot implements Plot {

    //    double[] is represented as a point
    private Map<String, List<Point>> values = new HashMap<>();

    private JavaPlot plot = new JavaPlot();

    private PlotColor[] colors = new NamedPlotColor[]{NamedPlotColor.BLUE, NamedPlotColor.BLACK, NamedPlotColor.RED, NamedPlotColor.BROWN, NamedPlotColor.GREEN};
    private int currentColor;

    private int[] lineTypes = new int[]{2, 5, 6, 10, 13};
    private int currentLineTypes;

    private final Logger gnuPlotLogger = LoggerFactory.getLogger(GnuPlot.class);

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
    public boolean addArbitrary(String key, int position, double x, double y) {
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
        preparePlot();
        plot.plot();
    }

    private void preparePlot() {
        Set<String> keysSet = values.keySet();

        for (String key : keysSet) {

            DataSet dataSet = createDataset(values.get(key));

            AbstractPlot legend = createLegend(key, dataSet);

            plot.addPlot(legend);

        }
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

    @Override
    public void saveToFile(String path) {
        if (path != null) {
            PostscriptTerminal epsTerminal = new PostscriptTerminal(path);
            epsTerminal.setColor(true);
            plot.setTerminal(epsTerminal);
            gnuPlotLogger.info("Plotting to file: {}", path);
            preparePlot();
            plot.plot();
            gnuPlotLogger.info("Done plotting.");
            String pdfPath = path.replace("eps", "pdf");
            convertFile(path, pdfPath);
            new File(path).delete();
        }
    }

    private void convertFile(String path, String pdfPath) {

        // TODO adicionar configuração do binário do ps2pdf em arquivo .properties

        try {
            Process convert = Runtime.getRuntime().exec("/usr/bin/ps2pdf -dEPSCrop "+path + " " + pdfPath);
            BufferedReader input = new BufferedReader(new InputStreamReader(convert.getInputStream()));
            String line;
            gnuPlotLogger.info("Converting file: {}", path);

            while ((line=input.readLine())!= null){
                System.out.println(line);
            }
            int exitVal = convert.waitFor();
            gnuPlotLogger.info("Done converting. Final file: {}", pdfPath);
            System.out.println("Exited with code " + exitVal);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }


}
