package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

public class Plane extends Geometry implements FlatGeometry{
    final Point q0;
    final Vector normal;

    /**
     * TODO explanations here
     * @param _q0
     * @param _normal vector for the normal (will bwe normalized automatically)
     */public Plane(Point _q0, Vector _normal) {
        q0 = _q0;
        normal = _normal.normalize();
    }

    public Plane(Point p1, Point p2, Point p3) {
        q0 =p1;
//        //TODO check direction of vectors
//        Vector U = p1.subtract(p2);
//        Vector V = p3.subtract(p2);

        Vector U = p2.subtract(p1);
        Vector V = p3.subtract(p1);

        Vector N = U.crossProduct(V);

        //right hand rule
        normal = N.normalize();;
    }

    public Point getQ0() {
        return q0;
    }

    /***
     * implementation of getNormal from Geometry
     * @return the normal vector of the plane
     */

    public Vector getNormal() {
        return normal;
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
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        Vector n = normal;
        Vector v = ray.getDir();
        Point p0 = ray.getP0();

        //check if the point of the ray is same to the point of the plane
        if(q0.equals(p0)){
            return  null;
        }

        Vector P0_Q0 = q0.subtract(p0);

        //numerator of to summarise of parameter t
        double nP0Q0  = alignZero(n.dotProduct(P0_Q0));

        //check if p0 is on the plane
        //if the dot-product among P0_Q0 and n is 0, so p0 is on the plane and there is no cross point
        if (isZero(nP0Q0 )){
            return null;
        }

        //denominator of to summarise of parameter t
        //nv is 0 if n and v are orthogonal
        double nv = alignZero(n.dotProduct(v));

        // check if ray is lying in the plane axis
        if(isZero(nv))
        {
            return null;
        }

        double t= nP0Q0/nv;
        // make sure that t is more than 0
        if(alignZero(t-maxDistance)>0 || t<=0){
            return null;
        }
        Point point = ray.getPoint(t);

        return List.of(new GeoPoint(this,ray.getPoint(t)));
    }

}