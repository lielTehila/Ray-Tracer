package primitives;

import java.util.List;
import java.util.Objects;
import geometries.Intersectable.GeoPoint;

import static primitives.Util.isZero;

public class Ray {
    final private Point p0;
    final private Vector dir;

    /***
     * constractor with params
     * @param  p-Point
     * @param d-Vector
     */
    public Ray( Point p, Vector d)
    {
        p0=new Point(p.get_xyz());
        dir=new Vector(d.get_xyz()).normalize() ;
    }

    /***
     * print the elements
     * @return string
     */
    @Override
    public String toString() {
        return "Ray{" +
                "p0=" + p0 +
                ", dir=" + dir +
                '}';
    }

    /***
     *
     * @return p0
     */
    public Point getP0() {
        return p0;
    }
    /***
     *
     * @return dir
     */
    public Vector getDir() {
        return dir;
    }

    /***
     *
     * @param d-object to compare
     * @return true if they are equal
     */
    public Point getPoint(double d){
        if(isZero(d)){
            return p0;
        }
        return p0.add(dir.scale(d));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ray ray = (Ray) o;
        return Objects.equals(p0, ray.p0) && Objects.equals(dir, ray.dir);
    }

    /**
     * find the closet point
     * @param points list of points
     * @return the closet point
     */
    public Point findClosestPoint(List<Point> points) {
        return points == null || points.isEmpty() ? null
                : findClosestGeoPoint(points.stream().map(p -> new GeoPoint(null, p)).toList()).point;
    }

//    public Point findClosestPoint (List<Point> lst)
//    {
//        if (lst==null||lst.isEmpty())
//            return null;
//
//        double min= p0.distance(lst.get(0));
//        Point pMin= lst.get(0);
//        lst.remove(0);
//
//        while(!lst.isEmpty()){
//            Point p=lst.get(0);
//            double length = p0.distance(p);
//            if (length<min){
//                min=length;
//                pMin = lst.get(0);
//            }
//            lst.remove(0);
//        }
//        return pMin;
//    }
    public GeoPoint findClosestGeoPoint (List<GeoPoint> lst)
    {
        if (lst==null||lst.isEmpty())
            return null;

        double min= p0.distance(lst.get(0).point);
        GeoPoint pMin= lst.get(0);
        lst.remove(0);

        while(!lst.isEmpty()){
            GeoPoint p=lst.get(0);
            double length = p0.distance(p.point);
            if (length<min){
                min=length;
                pMin = lst.get(0);
            }
            lst.remove(0);
        }
        return pMin;
    }
    /*
    @Override
    public int hashCode() {
        return Objects.hash(p0, dir);
    }
     */
}
