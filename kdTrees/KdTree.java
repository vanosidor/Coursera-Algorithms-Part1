/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

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
                insert(root, p, true);
            }
        }
    }

    private void insert(Node root, Point2D p, boolean hasXAxis) {

    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        // TODO
        return false;
    }

    // draw all points to standard draw
    public void draw() {
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        return null;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {

        return null;
    }


    // unit testing of the methods (optional)
    public static void main(String[] args) {

    }

    private static class Node {
        private final Point2D p;      // the point
        private final int nodeLevel;    // the axis-aligned rectangle corresponding to this node
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
