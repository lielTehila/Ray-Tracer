package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.ArrayList;
import java.util.List;

import static primitives.Util.isZero;

public class Triangle extends Polygon {
    public Triangle(Point p1, Point p2, Point p3) {
        super(p1, p2, p3);
    }

    /***
     * implementation of findIntersections from Geometry
     * @param ray - ray pointing towards the graphic object
     * @return Intersections between the ray and the geometry.
     */

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        List<Vector> lst = new ArrayList<Vector>();

        Point pr = ray.getP0();
        Vector v = ray.getDir();

        for (Point p : super.vertices) {
            Point pHelp = p.subtract(pr);
            lst.add(new Vector(pHelp.get_xyz()));
        }

        Vector n1 = lst.get(0).crossProduct(lst.get(1)).normalize();
        Vector n2 = lst.get(1).crossProduct(lst.get(2)).normalize();
        Vector n3 = lst.get(2).crossProduct(lst.get(0)).normalize();

        double d1 = v.dotProduct(n1);
        double d2 = v.dotProduct(n2);
        double d3 = v.dotProduct(n3);

        if (isZero(d1) || isZero(d2) || isZero(d3)) {
            return null;
        }

        //check if all the signs are same
        if (!(d1 > 0 && d2 > 0 && d3 > 0 || d1 < 0 && d2 < 0 && d3 < 0)) {
            return null;
        }
        List<GeoPoint> pointlst =plane.findGeoIntersections(ray);
        Point intersectPoint = pointlst.get(0).point;
        return List.of(new GeoPoint(this, intersectPoint));
    }
}


