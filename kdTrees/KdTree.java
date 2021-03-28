/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.Comparator;

public class KdTree {

    // Node topNode;
    private Node root;
    private int size = 0;

    // construct an empty set of points
    public KdTree() {

    }

    // is the set empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // number of points in the set
    public int size() {
        return size;
    }

    // add the point to the set (if it is not already in the set)
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
            // left
            if (node.lb == null) {
                node.lb = new Node(p, node.nodeLevel + 1, null, null);
                size++;
            }
            else insert(node.lb, p, !useXAxis);
        }
        // what if cmp == 0?
        else {
            // right
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

            // int cmp = p.compareTo(current.p);
            if (p.equals(current.p)) return true;

            boolean useXAxis = current.nodeLevel % 2 == 0;
            Comparator<Point2D> point2DComparator = useXAxis ? Point2D.X_ORDER : Point2D.Y_ORDER;

            int cmp = point2DComparator.compare(p, current.p);

            if (cmp < 0) {
                current = current.lb;
            }

            if (cmp > 0) {
                current = current.rt;
            }
        }

        return false;
    }

    // draw all points to standard draw
    public void draw() {
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        return null;
    }

    public Point2D nearest(Point2D p) {
        if (root == null) return null;
        return null;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
        // test
        KdTree kdTree = new KdTree();
        kdTree.insert(new Point2D(0.7, 0.2));
        kdTree.insert(new Point2D(0.5, 0.4));
        kdTree.insert(new Point2D(0.2, 0.3));
        kdTree.insert(new Point2D(0.4, 0.7));
        kdTree.insert(new Point2D(0.9, 0.6));
    }

    private static class Node {
        private final Point2D p;      // the point
        private final int nodeLevel;    // even level - vertical, odd - horizontal
        private Node lb;        // the left/bottom subtree
        private Node rt;            // the right/top subtree

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
