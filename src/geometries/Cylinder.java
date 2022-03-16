package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

public class Cylinder implements Geometry{
    final double height;

    public Cylinder(double h) {height=h;}

    @Override
    public Vector getNormal(Point point) {
        return null;
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
