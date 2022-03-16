package geometries;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

public class Sphere implements Geometry{
    final Point point;
    final double radius;

    public Sphere(Point p, double r){
        point=new Point(p.get_xyz());
        radius= r;
    }

    @Override
    public Vector getNormal(Point p) {
        Point pn = p.subtract(point);
        Vector norm = new Vector(pn.get_xyz());
        return norm.normalize();
    }
    /***
     * implementation of findIntersections from Geometry
     * @param ray - ray pointing towards the graphic object
     * @return Intersections between the ray and the geometry.
     */
    @Override
    public List<Point> findIntersections(Ray ray) {
        return null;
    }
}
