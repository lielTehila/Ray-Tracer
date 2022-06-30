package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;
import java.util.Objects;

/***
 * common interface for all graphic objects
 * that this intersects with a ray {@link primitives.Ray}
 */
abstract public class Intersectable {

    /***
     * find all intersections points {@link Point} that intersect with
     * a specific Ray {@link Ray}
     * @param ray - ray pointing towards the graphic object
     * @return immutable list of intersections  points {@link Point}
     */
    public final List<Point> findIntersections(Ray ray) {
        List<GeoPoint> geoList = findGeoIntersections(ray);
        return geoList == null ? null
                : geoList.stream()
                .map(gp -> gp.point)
                .toList();
    }

    /**
     * The function finds intersections between the ray and the geometric at a defined distance.
     * The function is a general function that calls an abstract function To enable finding intersection points with different geometric.
     * @param ray The ray we want to find points of intersection with the geometric.
     * @param maxDistance The maximum distance at which we are looking for intersection points.
     * @return List of intersection point between the ray and the geometric.
     */
    public final List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
        return findGeoIntersectionsHelper(ray, maxDistance);
    }

    /**
     * The function finds intersections between the ray and the geometric with a distance of default value.
     * The function is a general function that calls an abstract function To enable finding intersection points with different geometric.
     * @param ray The ray we want to find points of intersection with the geometric.
     * @return List of intersection point between the ray and the geometric.
     */
    public final List<GeoPoint> findGeoIntersections(Ray ray) {
        return findGeoIntersectionsHelper(ray, Double.POSITIVE_INFINITY);
    }

    /**
     * The function finds intersections between the ray and the geometric at a defined distance.
     * An abstract function and each of the geometric bodies is exactly the same in a way that fits the same body.
     * @param ray The ray we want to find points of intersection with the geometric.
     * @param maxDistance The maximum distance at which we are looking for intersection points.
     * @return List of intersection point between the ray and the geometric.
     */
    protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance);

    /**
     * A class representing a point on a geometric.
     */
    public static class GeoPoint {

        /**
         * the geometric
         */
        public final Geometry geometry;
        /**
         * the point on geometric.
         */
        public final Point point;

        /***
         * the constructor with params for the internal class
         * @param geometry a geometry shape
         * @param point the point of ______??
         */
        public GeoPoint(Geometry geometry, Point point) {
            this.geometry = geometry;
            this.point = point;
        }

        @Override
        public String toString() {
            return "GeoPoint{" +
                    "geometry=" + geometry +
                    ", point=" + point +
                    '}';
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            GeoPoint geoPoint = (GeoPoint) obj;
            return geometry.equals(geoPoint.geometry) && point.equals(geoPoint.point);
        }

    }

}
