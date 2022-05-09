package geometries;

import primitives.Material;
import primitives.Color;

/**
 * Flat Geometry is a Marker abstract class extending Geometry
 * to differentiate it from RadialGeometry
 * we did not decalre it as an interface
 */
public abstract class FlatGeometry extends Geometry {
    /**
     * Associated plane in which the flat geometry lays
     */
    public Plane plane = null;

}