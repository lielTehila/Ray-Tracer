package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;
import java.util.Objects;

/***
 * common interface for all graphic objects
 * that this intersect with a ray {@link primitives.Ray}
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

    /***
     * ????
     * @param ray
     * @return
     */
    public final List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
        return findGeoIntersectionsHelper(ray, maxDistance);
    }
    public final List<GeoPoint> findGeoIntersections(Ray ray) {
        return findGeoIntersectionsHelper(ray, Double.POSITIVE_INFINITY);
    }

    protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance);

    /**
     *?????
     */
    public static class GeoPoint{

        public final Geometry geometry;
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

//        @Override
//        public int hashCode() {
//            return Objects.hash(geometry, point);
//        }
    }

}
