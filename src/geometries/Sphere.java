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
    public Vector getNormal(Point point) {
        return null;
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        return null;
    }
}
