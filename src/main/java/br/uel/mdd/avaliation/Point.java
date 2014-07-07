package br.uel.mdd.avaliation;

public class Point {

    private float x;

    private float y;

    public Point(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float[] asArray() {
        return new float[]{x, y};
    }

    public Float[] asObjectArray() {
        return new Float[]{x, y};
    }

}
