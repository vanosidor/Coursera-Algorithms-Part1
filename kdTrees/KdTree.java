/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class KdTree {
    private Node root;
    private int size = 0;

    public KdTree() {

    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (root == null) {
            root = Node.create(p, 0, null, null);
            size++;
        }
        else {
            if (!contains(p)) {
                insert(root, p, true); // second node check x axis
            }
        }
    }

    private void insert(Node node, Point2D p, boolean useXAxis) {
        Comparator<Point2D> slopeComparator = useXAxis ? Point2D.X_ORDER : Point2D.Y_ORDER;
        int cmp = slopeComparator.compare(p, node.p);
        if (cmp < 0) {
            if (node.lb == null) {
                node.lb = new Node(p, node.nodeLevel + 1, null, null);
                size++;
            }
            else insert(node.lb, p, !useXAxis);
        }
        else {
            if (node.rt == null) {
                node.rt = new Node(p, node.nodeLevel + 1, null, null);
                size++;
            }
            else insert(node.rt, p, !useXAxis);
        }
    }

    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (root == null) return false;
        Node current = root;
        while (current != null) {
            if (p.equals(current.p)) return true;

            boolean useXAxis = current.nodeLevel % 2 == 0;
            Comparator<Point2D> point2DComparator = useXAxis ? Point2D.X_ORDER : Point2D.Y_ORDER;

            int cmp = point2DComparator.compare(p, current.p);

            if (cmp < 0) {
                current = current.lb;
            }
            else current = current.rt;
        }

        return false;
    }

    public void draw() {
        draw(root, new RectHV(0, 0, 1, 1));
    }

    private void draw(Node node, RectHV rectHV) {

        if (node == null) return;

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);

        node.p.draw();

        if (node.nodeLevel % 2 == 0) {
            StdDraw.setPenRadius(0.001);
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(node.p.x(), rectHV.ymin(), node.p.x(), rectHV.ymax());
            if (node.lb != null)
                draw(node.lb, new RectHV(rectHV.xmin(), rectHV.ymin(), node.p.x(), rectHV.ymax()));
            if (node.rt != null)
                draw(node.rt, new RectHV(node.p.x(), rectHV.ymin(), rectHV.xmax(), rectHV.ymax()));
        }
        else {
            StdDraw.setPenRadius(0.001);
            StdDraw.setPenColor(StdDraw.BLUE);

            StdDraw.line(rectHV.xmin(), node.p.y(), rectHV.xmax(), node.p.y());

            if (node.lb != null) {
                draw(node.lb, new RectHV(rectHV.xmin(), rectHV.ymin(), rectHV.xmax(), node.p.y()));
            }
            if (node.rt != null) {
                draw(node.rt, new RectHV(rectHV.xmin(), node.p.y(), rectHV.xmax(), rectHV.ymax()));
            }
        }

    }

    public Iterable<Point2D> range(RectHV rect) {

        if (rect == null) throw new IllegalArgumentException();

        List<Point2D> resultList = new LinkedList<>();
        range(root, rect, resultList);
        return resultList;
    }

    private void range(Node node, RectHV rect, List<Point2D> points) {

        if (node == null) return;

        if (rect.contains(node.p)) points.add(node.p);

        if (node.nodeLevel % 2 == 0) {
            if (node.p.x() >= rect.xmin() && node.p.x() <= rect.xmax()) {
                range(node.lb, rect, points);
                range(node.rt, rect, points);
            }
            else if (node.p.x() > rect.xmax()) {
                range(node.lb, rect, points);
            }
            else {
                range(node.rt, rect, points);
            }
        }
        else {
            if (node.p.y() >= rect.ymin() && node.p.y() <= rect.ymax()) {
                range(node.rt, rect, points);
                range(node.lb, rect, points);
            }
            else if (node.p.y() > rect.ymax()) {
                range(node.lb, rect, points);
            }
            else {
                range(node.rt, rect, points);
            }
        }
    }

    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (isEmpty()) return null;
        return nearest(p, root.p, root, new RectHV(0, 0, 1, 1));
    }

    private Point2D nearest(Point2D queryPoint, Point2D nearestPoint, Node node, RectHV rect) {

        if (node == null) return nearestPoint;

        double xmin = rect.xmin();
        double xmax = rect.xmax();
        double ymin = rect.ymin();
        double ymax = rect.ymax();

        double currentPointX = node.p.x();
        double currentPointY = node.p.y();

        Point2D newNearest = (node.p.distanceSquaredTo(queryPoint) < nearestPoint
                .distanceSquaredTo(queryPoint)) ?
                             node.p : nearestPoint;

        if (node.nodeLevel % 2 == 0) {
            if (queryPoint.x() > currentPointX) {
                // search right
                Point2D nearestRight = nearest(queryPoint, newNearest, node.rt,
                                               new RectHV(currentPointX, ymin, xmax, ymax));
                // must check left rect distance
                RectHV leftRect = new RectHV(xmin, ymin,
                                             currentPointX,
                                             ymax);
                if (nearestRight.distanceSquaredTo(queryPoint) < leftRect
                        .distanceSquaredTo(queryPoint)) {
                    return nearestRight;
                }
                else {
                    Point2D nearestLeft = nearest(queryPoint, newNearest, node.lb,
                                                  leftRect);
                    if (nearestLeft.distanceSquaredTo(queryPoint) < nearestRight
                            .distanceSquaredTo(queryPoint)) return nearestLeft;
                    else return nearestRight;
                }
            }
            else {
                // search left
                Point2D nearestLeft = nearest(queryPoint, newNearest, node.lb,
                                              new RectHV(xmin, ymin, currentPointX, ymax));
                RectHV rightRect = new RectHV(currentPointX, ymin,
                                              xmax, ymax);
                if (nearestLeft.distanceSquaredTo(queryPoint) < rightRect
                        .distanceSquaredTo(queryPoint)) {
                    return nearestLeft;
                }
                else {
                    Point2D nearestRight = nearest(queryPoint, newNearest, node.rt,
                                                   rightRect);
                    if (nearestRight.distanceSquaredTo(queryPoint) < nearestLeft
                            .distanceSquaredTo(queryPoint)) return nearestRight;
                    else return nearestLeft;
                }
            }
        }
        else {
            if (queryPoint.y() > currentPointY) {
                //search top
                Point2D nearestTop = nearest(queryPoint, newNearest, node.rt,
                                             new RectHV(xmin, currentPointY, xmax, ymax));
                RectHV bottomRect = new RectHV(xmin, ymin, xmax,
                                               currentPointY);
                if (nearestTop.distanceSquaredTo(queryPoint) < bottomRect
                        .distanceSquaredTo(queryPoint)) {
                    return nearestTop;
                }
                else {
                    Point2D nearestBottom = nearest(queryPoint, newNearest, node.lb,
                                                    new RectHV(xmin, ymin, xmax, currentPointY));
                    if (nearestBottom.distanceSquaredTo(queryPoint) < nearestTop
                            .distanceSquaredTo(queryPoint)) return nearestBottom;
                    else return nearestTop;
                }
            }
            else {
                //search bottom
                Point2D nearestBottom = nearest(queryPoint, newNearest, node.lb,
                                                new RectHV(xmin, ymin, xmax, currentPointY));
                RectHV topRect = new RectHV(xmin, currentPointY,
                                            xmax, ymax);
                if (nearestBottom.distanceSquaredTo(queryPoint) < topRect
                        .distanceSquaredTo(queryPoint)) {
                    return nearestBottom;
                }
                else {
                    Point2D nearestTop = nearest(queryPoint, newNearest, node.rt,
                                                 topRect);
                    if (nearestTop.distanceSquaredTo(queryPoint) < nearestBottom
                            .distanceSquaredTo(queryPoint)) return nearestTop;
                    else return nearestBottom;
                }
            }
        }
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);
        KdTree kdTree = new KdTree();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdTree.insert(p);
        }

        StdDraw.enableDoubleBuffering();

        kdTree.draw();

        // Iterable<Point2D> range = kdTree.range(new RectHV(0.16,  0.41, 0.53, 0.54));
        Point2D query = new Point2D(0.125, 0.875);
        Point2D nearest = kdTree.nearest(query);
        StdOut.println(nearest);
        StdDraw.setPenColor(StdDraw.GREEN);
        StdDraw.setPenRadius(0.02);
        query.draw();
        StdDraw.show();
    }

    private static class Node {
        private final Point2D p;     // the point
        private final int nodeLevel; // even level - vertical, odd - horizontal
        private Node lb;             // the left/bottom subtree
        private Node rt;             // the right/top subtree

        private Node(Point2D p, int nodeLevel, Node lb, Node rt) {
            this.p = p;
            this.nodeLevel = nodeLevel;
            this.lb = lb;
            this.rt = rt;
        }

        public static Node create(Point2D p, int nodeLevel, Node lb, Node rt) {
            return new Node(p, nodeLevel, lb, rt);
        }
    }
}
