package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;

public class Plane implements Geometry{
    final Point _q0;
    final Vector _normal;

    /**
     * TODO explanations here
     * @param q0
     * @param normal vector for the normal (will bwe normalized automatically)
     */public Plane(Point q0, Vector normal) {
        _q0 = q0;
        _normal = normal.normalize();
    }

    public Plane(Point p1, Point p2, Point p3) {
        _q0 =p1;
//        //TODO check direction of vectors
//        Vector U = p1.subtract(p2);
//        Vector V = p3.subtract(p2);

        Vector U = p2.subtract(p1);
        Vector V = p3.subtract(p1);

        Vector N = U.crossProduct(V);

        //right hand rule
        _normal = N.normalize();;
    }

    public Point getQ0() {
        return _q0;
    }

    /***
     * implementation of getNormal from Geometry
     * @return the normal vector of the plane
     */

    public Vector getNormal() {
        return _normal;
    }

    /***
     * implementation of getNormal
     * @param point point outside the grophic shape
     * @return normal of the plane in the point
     */
    @Override
    public Vector getNormal(Point point) {
        return getNormal();
    }

    /***
     * implementation of findIntersections from Geometry
     * @param ray - ray pointing towards the graphic object
     * @return Intersections between the ray and the geometry.
     */
    @Override
    public List<Point> findIntersections(Ray ray) {
        /*
        Vector v = ray.getDir();
        Vector n = _normal;
        Point p0 = ray.getP0();

        double nv = alignZero(n.dotProduct(v));
         */
        return null;
    }
}