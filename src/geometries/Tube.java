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
        axisRay = new Ray(r.getP0(),r.getDir());
    }

    @Override
    public Vector getNormal(Point point) {
        Point p = axisRay.getP0();
        Vector v=axisRay.getDir();

        Vector tmp = point.subtract(p);
        double num = v.dotProduct(tmp);
        Point p2 = p.add(v.scale(num));
        if (p.equals(p2)) {
            throw new IllegalArgumentException("point cannot be on the tube axis");
        }
        Vector norm = point.subtract(p2).normalize();
        return norm;

    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        return null;
    }
}
