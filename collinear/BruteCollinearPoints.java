/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class BruteCollinearPoints {
    private int numberOfSegments = 0;
    private LineSegment[] tempLineSegments;

    public BruteCollinearPoints(Point[] points) { // finds all line segments containing 4 points

        // check not null
        if (points == null) throw new IllegalArgumentException();

        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) throw new IllegalArgumentException();
        }

        int length = points.length;

        // use linked list instead of temporary array
        tempLineSegments = new LineSegment[length * length];

        Arrays.sort(points);

        for (int i = 0; i < length - 1; i++) {
            if (points[i].compareTo(points[i + 1]) == 0) throw new IllegalArgumentException();
        }

        for (int i = 0; i < length; i++) {
            for (int j = i + 1; j < length; j++) {
                for (int k = j + 1; k < length; k++) {
                    for (int m = k + 1; m < length; m++) {
                        double slopeFirst = points[i].slopeTo(points[j]);
                        double slopeSecond = points[i].slopeTo(points[k]);
                        if (slopeFirst == slopeSecond) {
                            double slopeThird = points[i].slopeTo(points[m]);

                            if (slopeFirst == slopeThird) {
                                if (tempLineSegments.length == numberOfSegments) {
                                    LineSegment[] tempLineSegmentsNew = new LineSegment[
                                            numberOfSegments
                                                    * 2];
                                    for (int n = 0; n < tempLineSegments.length; n++) {
                                        tempLineSegmentsNew[n] = tempLineSegments[n];
                                    }
                                    tempLineSegments = tempLineSegmentsNew;
                                }

                                tempLineSegments[numberOfSegments++] = new LineSegment(points[i],
                                                                                       points[m]);
                            }
                        }
                    }
                }
            }
        }
    }

    public int numberOfSegments() {  // the number of line segments
        return numberOfSegments;
    }

    public LineSegment[] segments() {
        // the line segments
        LineSegment[] segments = new LineSegment[numberOfSegments];
        for (int i = 0; i < numberOfSegments; i++) {
            segments[i] = tempLineSegments[i];
        }
        return segments;
    }


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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}

//Run over k combinations of n elements

// private boolean hasNextCombination() {
//     if (length < 4) return false;
//
//     if (arr == null) {
//         arr = new int[M];
//         for (int i = 0; i < M; i++)
//             arr[i] = i;
//         return true;
//     }
//     for (int i = M - 1; i >= 0; i--)
//         if (arr[i] < length - M + i) {
//             arr[i]++;
//             for (int j = i + 1; j < M; j++)
//                 arr[j] = arr[j - 1] + 1;
//             return true;
//         }
//     return false;
// }
