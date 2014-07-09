package br.uel.mdd.avaliation.plot;

public class Point {

    private double x;

    private double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double[] asArray() {
        return new double[]{x, y};
    }

    public Double[] asObjectArray() {
        return new Double[]{x, y};
    }

}
