package br.uel.mdd.avaliation.plot;

public interface Plot {

    public void addValue(String key, double x, double y);

    public void plot();

    boolean addArbitrary(String key, int position, double x, double y);
}
