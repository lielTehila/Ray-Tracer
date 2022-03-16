package geometries;
import primitives.Point;
import primitives.Ray;

import java.util.List;

public class Triangle extends Polygon {
    public Triangle(Point p1, Point p2, Point p3)
    {
        super(p1,p2,p3);
    }
    /***
     * implementation of findIntersections from Geometry
     * @param ray - ray pointing towards the graphic object
     * @return Intersections between the ray and the geometry.
     */
    @Override
    public List<Point> findIntersections(Ray ray) {
        return super.findIntersections(ray);
    }
}


