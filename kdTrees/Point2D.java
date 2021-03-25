/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdDraw;

public class Point2D {
    private final double x;
    private final double y;

    // construct the point (x, y)
    public Point2D(double x, double y) {
        this.x = x;
        this.y = y;

    }

    // x-coordinate
    public double x() {
        return x;
    }

    // y-coordinate
    public double y() {
        return y;
    }

    // Euclidean distance between two points
    public double distanceTo(Point2D that) {
        return Math.sqrt((this.x - that.x) * (this.x - that.x) + (this.y - that.y) * (this.y
                - that.y));
    }

    // square of Euclidean distance between two points
    public double distanceSquaredTo(Point2D that) {
        return (this.x - that.x) * (this.x - that.x) + (this.y - that.y) * (this.y - that.y);
    }

    // for use in an ordered symbol table
    public int compareTo(Point2D that) {
        if (that == null) throw new IllegalArgumentException();
        if (this.y < that.y) return -1;
        if (this.y > that.y) return 1;

        return Double.compare(this.x, that.x);
    }

    // does this point equal that object?
    public boolean equals(Object that) {
        if (this == that) return true;
        if (that == null) return false;

        if (this.getClass() != that.getClass()) return false;

        Point2D p = (Point2D) that;

        if (this.x == p.x && this.y == p.y) return true;
        return false;
    }

    // draw to standard draw
    public void draw() {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        StdDraw.point(x, y);
    }

    // string representation
    public String toString() {
        return ("x: " + x + "y: " + y);
    }

    public static void main(String[] args) {
        Point2D testPoint = new Point2D(0.01, 0.02);
        testPoint.draw();
    }
}
