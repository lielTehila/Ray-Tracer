package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 *The class represents a triangle by inheritance from a polygon.
 */
public class Triangle extends Polygon {

    /**
     *Constructor with parameters
     * @param p1 First point
     * @param p2 Second point
     * @param p3 Third point
     */
    public Triangle(Point p1, Point p2, Point p3) {
        super(p1, p2, p3);
    }

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        List<Vector> lst = new LinkedList<>();

        Point pr = ray.getP0();
        Vector v = ray.getDir();

        for (Point p : super.vertices) {
            Vector vp = p.subtract(pr);
            lst.add(vp);
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
        List<GeoPoint> pointlst =plane.findGeoIntersections(ray, maxDistance);
        if(pointlst==null)
            return null;
        Point intersectPoint = pointlst.get(0).point;
        double t=intersectPoint.distance(pr);
        if((alignZero(t-maxDistance)) > 0)
            return null;
        return List.of(new GeoPoint(this, intersectPoint));
    }
}

