package geometries;

import primitives.Point;
import primitives.Vector;

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

    /**
     * implementation of getNormal from Geometry
     * @param point
     * @return
     */

    public Vector getNormal() {
        return _normal;
    }

    @Override
    public Vector getNormal(Point point) {
        return getNormal();
    }


}