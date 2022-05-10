package geometries;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

public class Tube extends Geometry{
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
    /***
     * implementation of findIntersections from Geometry
     * @param ray - ray pointing towards the graphic object
     * @return Intersections between the ray and the geometry.
     */

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray,double maxDistance) {
        return null;
    }
}
