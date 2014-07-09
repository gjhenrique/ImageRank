package br.uel.mdd.avaliation.plot;

public interface Plot {

    public void addValue(String key, double x, double y);

    public void plot();

    boolean addArbitrary(String key, int position, double x, double y);

    /**
     * Save the chart to a file.
     * The path must contain the filename and extension
     * @param path
     */
    void saveToFile(String path);
}
