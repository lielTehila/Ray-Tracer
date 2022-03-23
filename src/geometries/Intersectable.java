package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;

/***
 * common interface for all graphic objects
 * that this intersect with a ray {@link primitives.Ray}
 */
public interface Intersectable {
    /***
     * find all intersections points {@link Point} that intersect with
     * a specific Ray {@link Ray}
     * @param ray - ray pointing towards the graphic object
     * @return immutable list of intersections  points {@link Point}
     */
    public List<Point> findIntersections(Ray ray);
}
