package geometries;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.*;

import static primitives.Util.alignZero;

public class Sphere extends Geometry{
    /**
     * The center point of the circle
     */
    final Point center;
    /**
     *The radius of the circle
     */
    final double radius;

    /**
     * Constructor with params
     * @param p the point in the middle of the sphere.
     * @param r The radius of the sphere.
     */
    public Sphere(Point p, double r){
        center =new Point(p.get_xyz());
        radius= r;
    }

    @Override
    public Vector getNormal(Point p) {
        Point pn = p.subtract(center);
        Vector norm = new Vector(pn.get_xyz());
        return norm.normalize();
    }

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        Point P0 = ray.getP0();
        Vector v = ray.getDir();
        //check the cae that the point is on the center


        if (P0.equals(center)) {
            if(alignZero(radius-maxDistance)>0){
                return null;
            }
            return List.of(new GeoPoint(this,center.add(v.scale(radius))));
        }

        Vector u = this.center.subtract(P0);

        double tm = alignZero(v.dotProduct(u));
        double d = alignZero(Math.sqrt(u.lengthSquared()-Math.pow(tm,2)));

        if (d>=radius)
            return null;

        double th = Math.sqrt(Math.pow(radius,2)-Math.pow(d,2));
        double t1 = alignZero(tm -th);
        double t2 = alignZero(tm +th);

        if (t1<= 0 && t2<=0)
            return null;

        List<GeoPoint> lst=new LinkedList<>();
        if (t1 >0 && alignZero(t1-maxDistance)<=0)
        {
            Point cross_p = ray.getP0().add(ray.getDir().scale(t1));
            lst.add(new GeoPoint(this,cross_p));
        }

        if (t2 >0 &&alignZero(t2-maxDistance)<=0)
        {
            Point cross_p = ray.getP0().add(ray.getDir().scale(t2));
            double t=cross_p.distance(P0);
            if((alignZero(t-maxDistance)) <= 0)
                lst.add(new GeoPoint(this,cross_p));
        }

        return lst;
    }
}
