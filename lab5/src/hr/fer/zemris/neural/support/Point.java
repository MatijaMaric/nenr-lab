package hr.fer.zemris.neural.support;

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

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public static double dist(Point a, Point b) {
        return a.distTo(b);
    }

    public double distTo(Point o) {
        return Math.sqrt((this.x - o.x) * (this.x - o.x) + (this.y - o.y) * (this.y - o.y));
    }

    public void add(Point o) {
        x += o.getX();
        y += o.getY();
    }

    public void sub(Point o) {
        x -= o.getX();
        y -= o.getY();
    }

    public void scale(double s) {
        x *= s;
        y *= s;
    }

    public void iscale(double s) {
        scale(1 / s);
    }

    public Point copy() {
        return new Point(x, y);
    }
}
