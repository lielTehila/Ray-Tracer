package geometries;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.*;

public class Sphere implements Geometry{
    final Point center;
    final double radius;

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
    /***
     * implementation of findIntersections from Geometry
     * @param ray - ray pointing towards the graphic object
     * @return Intersections between the ray and the geometry.
     */
    @Override
    public List<Point> findIntersections(Ray ray) {

        Point P0 = ray.getP0();
        //check the cae that the point is on the center
        if (P0.equals(center)) {
            return List.of(center.add(ray.getDir().scale(radius)));
        }

        Vector u = this.center.subtract(P0);

        double tm = ray.getDir().dotProduct(u);
        double d = Math.sqrt(u.lengthSquared()-Math.pow(tm,2));
        if (d>=radius)
            return null;
        double th = Math.sqrt(Math.pow(radius,2)-Math.pow(d,2));
        double t1 = tm -th;
        double t2 = tm +th;

        if (t1<= 0 && t2<=0)
            return null;

        List<Point> lst=new ArrayList<Point>();
        if (t1 >0)
        {
            Point cross_p = ray.getP0().add(ray.getDir().scale(t1));
            lst.add(cross_p);
        }

        if (t2 >0)
        {
            Point cross_p = ray.getP0().add(ray.getDir().scale(t2));
            lst.add(cross_p);
        }

        return lst;
    }
}
