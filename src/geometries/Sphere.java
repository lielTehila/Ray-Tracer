package geometries;
import primitives.Point;
import primitives.Vector;

public class Sphere implements Geometry{
    final Point point;
    final double radius;

    public Sphere(Point p, double r){
        point=new Point(p.get_xyz());
        radius= r;
    }

    @Override
    public Vector getNormal(Point point) {
        return null;
    }
}
