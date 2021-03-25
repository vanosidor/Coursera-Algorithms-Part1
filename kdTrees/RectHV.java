/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

public class RectHV {
    // construct the rectangle [xmin, xmax] x [ymin, ymax]

    private double xmin;
    private double ymin;
    private double xmax;
    private double ymax;

    // construct the rectangle [xmin, xmax] x [ymin, ymax]
    // throw an IllegalArgumentException if (xmin > xmax) or (ymin > ymax)
    public RectHV(double xmin, double ymin,
                  double xmax, double ymax) {
        if (xmin > xmax || ymin > ymax) throw new IllegalArgumentException();
        this.xmin = xmin;
        this.ymin = ymin;
        this.xmax = xmax;
        this.ymax = ymax;
    }


    // minimum x-coordinate of rectangle
    public double xmin() {
        return xmin;
    }

    // minimum y-coordinate of rectangle
    public double ymin() {
        return ymin;
    }

    // maximum x-coordinate of rectangle
    public double xmax() {
        return xmax;
    }

    // maximum y-coordinate of rectangle
    public double ymax() {
        return ymax;
    }

    // does this rectangle contain the point p (either inside or on boundary)?
    public boolean contains(Point2D p) {
        return (p.x() >= xmin && p.x() <= xmax) && (p.y() >= ymin && p.y() <= ymax);
    }

    public boolean intersects(
            RectHV that) {

    }

    // does this rectangle intersect that rectangle (at one or more points)?
    public double distanceTo(Point2D p) {

    }

    // does this rectangle intersect that rectangle (at one or more points)?


    public double distanceTo(
            Point2D p)            // Euclidean distance from point p to closest point in rectangle

    public double distanceSquaredTo(
            Point2D p)     // square of Euclidean distance from point p to closest point in rectangle

    public boolean equals(Object that)              // does this rectangle equal that object?

    public void draw()                           // draw to standard draw

    public String toString()                       // string representation               // string representation
}