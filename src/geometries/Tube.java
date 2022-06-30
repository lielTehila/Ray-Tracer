package geometries;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 * The class represents tube.
 */
public class Tube extends Geometry{

    /**
     * The radius of tube
     */
    final double radius;

    /**
     * The radius of ray
     */
    final Ray axisRay;

    /**
     * Constructor with parameters
     * @param rd radius
     * @param r Ray
     */
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
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray,double maxDistance) {
        return null;
    }
}
