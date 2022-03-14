package geometries;

import primitives.Point;
import primitives.Vector;

/***
 * interface for all graphic 3D objects that are
 * positioned in our 3D
 */
public interface Geometry extends Intersectable {
    /***
     * normal vector from a specific Point {@link Point}
     * @param point point outside the grophic shape
     * @return rornal vector {@link Vector}
     */
    Vector getNormal(Point point);
}