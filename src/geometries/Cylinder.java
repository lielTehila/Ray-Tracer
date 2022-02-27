package geometries;

import primitives.Point;
import primitives.Vector;

public class Cylinder implements Geometry{
    final double height;

    public Cylinder(double h) {height=h;}

    @Override
    public Vector getNormal(Point point) {
        return null;
    }
}
