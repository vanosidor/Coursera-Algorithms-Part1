/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.LinkedList;

public class FastCollinearPoints {

    private final LineSegment[] lineSegments;

    public FastCollinearPoints(Point[] points) {

        // check not null
        if (points == null) {
            throw new IllegalArgumentException();
        }

        for (Point p : points) {
            if (p == null) throw new IllegalArgumentException();
        }

        final int length = points.length;
        final LinkedList<LineSegment> maxLineSegments = new LinkedList<LineSegment>();

        Point[] sortedPoints = points.clone();
        Arrays.sort(sortedPoints);

        //check duplicates
        for (int i = 0; i < length - 1; i++) {
            if (sortedPoints[i].compareTo(sortedPoints[i + 1]) == 0)
                throw new IllegalArgumentException();
        }

        for (int i = 0; i < sortedPoints.length; i++) {

            Point originPoint = sortedPoints[i];
            Point[] sortedPointsBySlope = sortedPoints.clone();

            Arrays.sort(sortedPointsBySlope, originPoint.slopeOrder());

            int x = 1;

            while (x < length) {
                LinkedList<Point> resultPoints = new LinkedList<>();

                double slopeToOrigin = originPoint.slopeTo(sortedPointsBySlope[x]);

                while (x < length && originPoint.slopeTo(sortedPointsBySlope[x]) == slopeToOrigin) {
                    resultPoints.add(sortedPointsBySlope[x++]);
                }

                //have line segments if: 1) Are collinear - have 4 points on same line; 2) p < first element
                if (resultPoints.size() >= 3 && originPoint.compareTo(resultPoints.peek()) < 0) {
                    Point min = originPoint;
                    Point max = resultPoints.removeLast();
                    maxLineSegments.add(new LineSegment(min, max));
                }
            }

        }

        lineSegments = maxLineSegments.toArray(new LineSegment[0]);
    }    // finds all line segments containing 4 or more points

    public int numberOfSegments() {
        return lineSegments.length;
    }   // the number of line segments

    public LineSegment[] segments() {
        return lineSegments.clone();
    }       // the line segments

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
