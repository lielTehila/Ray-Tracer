package geometries;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

public class Tube implements Geometry{
    final double radius;
    final Ray axisRay;

    public Tube(double rd, Ray r){
        radius=rd;
        axisRay =new Ray(r.getP0(),r.getDir());
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
