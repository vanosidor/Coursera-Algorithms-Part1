/******************************************************************************
 *  Compilation:  javac Point.java
 *  Execution:    java Point
 *  Dependencies: none
 *
 *  An immutable data type for points in the plane.
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;

public class Point implements Comparable<Point> {

    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point

    /**
     * Initializes a new point.
     *
     * @param x the <em>x</em>-coordinate of the point
     * @param y the <em>y</em>-coordinate of the point
     */
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    /**
     * Draws this point to standard draw.
     */
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    /**
     * Draws the line segment between this point and the specified point
     * to standard draw.
     *
     * @param that the other point
     */
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * Returns the slope between this point and the specified point.
     * Formally, if the two points are (x0, y0) and (x1, y1), then the slope
     * is (y1 - y0) / (x1 - x0). For completeness, the slope is defined to be
     * +0.0 if the line segment connecting the two points is horizontal;
     * Double.POSITIVE_INFINITY if the line segment is vertical;
     * and Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1) are equal.
     *
     * @param that the other point
     * @return the slope between this point and the specified point
     */
    public double slopeTo(Point that) {
        /* YOUR CODE HERE */
        if (that.x == this.x) {
            if (that.y == this.y) return Double.NEGATIVE_INFINITY;
            return Double.POSITIVE_INFINITY;
        }
        if (that.y == this.y) return 0.0;
        return (double) (that.y - this.y) / (that.x - this.x);
    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     * Formally, the invoking point (x0, y0) is less than the argument point
     * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     *
     * @param that the other point
     * @return the value <tt>0</tt> if this point is equal to the argument
     * point (x0 = x1 and y0 = y1);
     * a negative integer if this point is less than the argument
     * point; and a positive integer if this point is greater than the
     * argument point
     */
    public int compareTo(Point that) {
        /* YOUR CODE HERE */
        if (this.y < that.y) return -1;
        if (this.y > that.y) return 1;


        if (this.x < that.x) return -1;
        if (this.x > that.x) return 1;
        return 0;
    }

    /**
     * Compares two points by the slope they make with this point.
     * The slope is defined as in the slopeTo() method.
     *
     * @return the Comparator that defines this ordering on points
     */
    public Comparator<Point> slopeOrder() {
        return new SlopeOrder();
    }


    /**
     * Returns a string representation of this point.
     * This method is provide for debugging;
     * your program should not rely on the format of the string representation.
     *
     * @return a string representation of this point
     */
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    /**
     * Unit tests the Point data type.
     */
    public static void main(String[] args) {
        /* YOUR CODE HERE */

        Point a1 = new Point(3, 2);
        Point b1 = new Point(3, 2);
        StdOut.println("Points 1 " + a1.toString() + " " + b1.toString());
        StdOut.println(compareResultString(a1, b1)); // 0
        StdOut.println("slopeTo: " + a1.slopeTo(b1));  // negative inf

        Point a2 = new Point(6, 1);
        Point b2 = new Point(7, 0);
        StdOut.println("\nPoints 2 " + a2.toString() + " " + b2.toString());
        StdOut.println(compareResultString(a2, b2));   // 1
        StdOut.println("slopeTo: " + a2.slopeTo(b2));  // -1

        Point a3 = new Point(1, 3);
        Point b3 = new Point(3, 5);
        StdOut.println("\nPoints 3 " + a3.toString() + " " + b3.toString());
        StdOut.println(compareResultString(a3, b3)); // -1
        StdOut.println("slopeTo: " + a3.slopeTo(b3)); // 1.0

        Point a4 = new Point(321, 3);
        Point b4 = new Point(3344, 3);
        StdOut.println("\nPoints 4 " + a4.toString() + " " + b4.toString());
        StdOut.println(compareResultString(a4, b4)); // -1
        StdOut.println("slopeTo: " + a4.slopeTo(b4)); // Positive zero

        Point a5 = new Point(1263, 3);
        Point b5 = new Point(564, 3);
        StdOut.println("\nPoints 5 " + a5.toString() + " " + b5.toString());
        StdOut.println(compareResultString(a5, b5)); // 1
        StdOut.println("slopeTo: " + a5.slopeTo(b5)); // Positive zero

        Point a6 = new Point(12, 5);
        Point b6 = new Point(12, 7);
        StdOut.println("\nPoints 6 " + a6.toString() + " " + b6.toString());
        StdOut.println(compareResultString(a6, b6)); // 1
        StdOut.println("slopeTo: " + a6.slopeTo(b6)); // Pos Infinity

    }

    private static String compareResultString(Point a, Point b) {
        int res = a.compareTo(b);
        if (res == 0) return "a = b";
        else if (res < 0) return "a < b";
        else return "a > b";
    }

    private class SlopeOrder implements Comparator<Point> {

        public int compare(Point q1, Point q2) {
            double slopeQ1 = Point.this.slopeTo(q1);
            double slopeQ2 = Point.this.slopeTo(q2);

            if (slopeQ1 == slopeQ2) return 0;
            else if (slopeQ1 < slopeQ2) return -1;
            else return 1;
        }
    }
}
