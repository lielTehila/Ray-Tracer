package primitives;

import java.util.Objects;

public class Point {
    final Double3 _xyz;

    public Double3 get_xyz() {
        return _xyz;
    }

    /**
     * primary constructor for Point
     * @param xyz Double3 value for x,z,z axis
     */
    public Point(Double3 xyz) {
        _xyz = xyz;
    }

    /**
     * secondary constructor for Point
     *
     * @param x coordinate value for X axis
     * @param y coordinate value for Y axis
     * @param z coordinate value for Z axis
     */
    public Point(double x, double y , double z) {
        //this(new Double3(x,y,z));
        _xyz = new Double3(x,y,z);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return _xyz.equals(point._xyz);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_xyz);
    }

    @Override
    public String toString() {
        return "Point " + _xyz ;
    }

    /**
     *
     * @param other
     * @return  d = ((x2 - x1)*(x2 - x1) + (y2 - y1)*(y2 - y1) + (z2 - z1)*(z2 - z1))
     */
    public double distanceSquared(Point other){
        double x1 = _xyz.d1;
        double y1 = _xyz.d2;
        double z1 = _xyz.d3;

        double x2 = other._xyz.d1;
        double y2 = other._xyz.d2;
        double z2 = other._xyz.d3;

        return ((x2 - x1)*(x2 - x1) + (y2 - y1)*(y2 - y1) + (z2 - z1)*(z2 - z1));
    }

    /**
     *
     * @param other
     * @return d = Sqrt (lengthSquare)
     * @link https://www.engineeringtoolbox.com/distance-relationship-between-two-points-d_1854.html
     */
    public  double distance (Point other){
        return Math.sqrt(distanceSquared(other));
    }

    /**
     *
     * @param vector
     * @return
     */
    public Point add(Vector vector) {
        return  new Point(_xyz.add(vector._xyz));
    }


    public Vector subtract(Point point) {
        return new Vector(_xyz.subtract(point._xyz));
    }
}