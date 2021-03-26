/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;

import java.awt.Color;
import java.util.ArrayList;

public class PointSET {

    private SET<Point2D> points;

    // construct an empty set of points
    public PointSET() {
        points = new SET<Point2D>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return points.isEmpty();
    }

    // number of points in the set
    public int size() {
        return points.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (!points.contains(p)) points.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        return points.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        StdDraw.setPenColor(Color.BLACK);
        points.forEach(Point2D::draw);
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(
            RectHV rect) {
        ArrayList<Point2D> range = new ArrayList<>();
        points.forEach(point -> { if (rect.contains(point)) range.add(point); });
        return range;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(
            Point2D p) {
        Point2D nearest = null;
        double minDistance = Double.POSITIVE_INFINITY;
        for (Point2D point : points) {
            if (!point.equals(p)) {
                double distance = point.distanceSquaredTo(p);
                if (distance < minDistance) {
                    minDistance = distance;
                    nearest = point;
                }
            }
        }
        return nearest;
    }

    public static void main(
            String[] args) {
    }                  // unit testing of the methods (optional)
}
