package lab.kd_tree;

public class Point2D implements Comparable<Point2D> {
    private static final double APPROXIMATE_EQUALITY = 0.0001d;

    private double x;
    private double y;

    public Point2D(double x, double y) {
        this.setX(x);
        this.setY(y);
    }

    public double getX() {
        return this.x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return this.y;
    }

    public void setY(double y) {
        this.y = y;
    }

    @Override
    public int compareTo(Point2D that) {
        if (this.y < that.y) return -1;
        if (this.y > that.y) return +1;
        if (this.x < that.x) return -1;
        if (this.x > that.x) return +1;

        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (!(obj instanceof Point2D)) {
            return false;
        }

        Point2D that = (Point2D)obj;
        return (this.x - that.x) < APPROXIMATE_EQUALITY && (this.y - that.y) < APPROXIMATE_EQUALITY;
    }

    @Override
    public int hashCode() {
        int hashX = Double.valueOf(this.x).hashCode();
        int hashY = Double.valueOf(this.y).hashCode();
        return 31 * hashX + hashY;
    }

    @Override
    public String toString() {
        return String.format("(%f, %f)", this.x, this.y);
    }
}
