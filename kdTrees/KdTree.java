/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

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
        if (p == null || isEmpty()) return null;
        return nearest(p, root.p, root);
    }

    private Point2D nearest(Point2D p, Point2D nearestPoint, Node node) {

        if (node == null) return nearestPoint;

        if (node.nodeLevel % 2 == 0) {
            if (p.x() > node.p.x()) {
                // right
                Point2D npr = nearest(p,
                                      (node.p.distanceSquaredTo(p) < nearestPoint
                                              .distanceSquaredTo(p) ? node.p :
                                       nearestPoint), node.rt);
                if (npr.distanceSquaredTo(p) > Math.abs(node.p.x() - p.x())) {
                    Point2D npl = nearest(p, npr, node.lb);
                    return (p.distanceSquaredTo(npr) > p.distanceSquaredTo(npl) ? npl : npr);
                }
                else {
                    return npr;
                }
            }
            else {
                Point2D npl = nearest(p,
                                      node.p.distanceSquaredTo(p) < nearestPoint
                                              .distanceSquaredTo(p) ? node.p :
                                      nearestPoint, node.lb);

                if (npl.distanceSquaredTo(p) > Math.abs(node.p.x() - p.x())) {
                    Point2D npr = nearest(p, npl, node.rt);
                    return npr.distanceSquaredTo(p) > npl.distanceSquaredTo(p) ? npl : npr;
                }
                else {
                    return npl;
                }
            }
        }
        else {
            if (p.y() > node.p.y()) {
                Point2D npu = nearest(p, node.p.distanceSquaredTo(p) < nearestPoint
                        .distanceSquaredTo(p) ?
                                         node.p : nearestPoint, node.rt);
                if (npu.distanceSquaredTo(p) > Math.abs(node.p.y() - p.y())) {
                    Point2D npd = nearest(p, npu, node.lb);
                    return npu.distanceSquaredTo(p) > npd.distanceSquaredTo(p) ? npd : npu;
                }
                else {
                    return npu;
                }
            }
            else {
                Point2D npd = nearest(p, node.p.distanceSquaredTo(p) < nearestPoint
                        .distanceSquaredTo(p) ?
                                         node.p : nearestPoint, node.lb);
                if (npd.distanceSquaredTo(p) > Math.abs(node.p.y() - p.y())) {
                    Point2D npu = nearest(p, npd, node.rt);
                    return npu.distanceSquaredTo(p) > npd.distanceSquaredTo(p) ? npd : npu;
                }
                else {
                    return npd;
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
        //
        // KdTree kdTree = new KdTree();
        // kdTree.insert(new Point2D(0.7, 0.2));
        // kdTree.insert(new Point2D(0.5, 0.4));
        // kdTree.insert(new Point2D(0.2, 0.3));
        // kdTree.insert(new Point2D(0.4, 0.7));
        // kdTree.insert(new Point2D(0.9, 0.6));

        kdTree.draw();
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
