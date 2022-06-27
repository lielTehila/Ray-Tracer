package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Geometries extends Intersectable {
    /**
     * return list of shapes
     */
    private List<Intersectable> shapes = null;

    /**
     * b
     */
    public Geometries() {
        shapes = new LinkedList<>();
    }

    /**
     *
     * @param geometries
     */
    public Geometries(Intersectable... geometries) {
        this();
        add(geometries);
    }

    public void add(Intersectable... geometries) {
        if (shapes == null) {
            throw new IllegalStateException("list not created");
        }
        for (Intersectable i : geometries)
            shapes.add(i);
    }

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        List<GeoPoint> lst = null;
        for (Intersectable shape : shapes) {
            List<GeoPoint> shapeList = shape.findGeoIntersections(ray, maxDistance);
            if (shapeList != null) {
                if (lst == null) {
                    lst = new LinkedList<>();
                }
                lst.addAll(shapeList);
            }
        }
        return lst;
    }

}
