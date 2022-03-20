package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
/**
 * test for {@link Sphere}
 * Unit tests for geometries.Sphere class
 * @author Yiska levi and liel tehila simchi
 */
class SphereTest {

    /**
     * Test method for {@link geometries.Sphere#getNormal(primitives.Point)}.
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        Sphere s = new Sphere( new Point(2,0,0),2.0);
        assertEquals(new Vector(1,0,0),s.getNormal(new Point(4,0,0)),"bad normalize in sphere");
    }
    /**
     * Test method for {@link geometries.Sphere#findIntersections(primitives.Ray)}.
     */
    @Test
    public void testFindIntersections() {
        Sphere sphere = new Sphere( new Point (1, 0, 0),1d);

        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray's line is outside the sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(-1, 0, 0), new Vector(1, 1, 0))),
                "Ray's line out of sphere");

        // TC02: Ray starts before and crosses the sphere (2 points)
        Point p1 = new Point(0.0651530771650466, 0.355051025721682, 0);
        Point p2 = new Point(1.53484692283495, 0.844948974278318, 0);
        List<Point> result = sphere.findIntersections(new Ray(new Point(-1, 0, 0),
                new Vector(3, 1, 0)));
        assertEquals(2, result.size(), "Wrong number of points");
        if (result.get(0).get_x() > result.get(1).get_x())
            result = List.of(result.get(1), result.get(0));
        assertEquals(List.of(p1, p2), result, "Ray crosses sphere");

        // TC03: Ray starts inside the sphere (1 point)
        result = sphere.findIntersections(new Ray(new Point(1.1, 0.1, 0),
                new Vector(1, 0, 0)));
        assertEquals(1, result.size(), "Wrong number of points");

        // TC04: Ray starts after the sphere (0 points)
        result = sphere.findIntersections(new Ray(new Point(-1, 0, 0),
                new Vector(-1, -1, 0)));
        assertEquals(0, result.size(), "Wrong number of points");

        // =============== Boundary Values Tests ==================

        // **** Group: Ray's line crosses the sphere (but not the center)
        // TC11: Ray starts at sphere and goes inside (1 points)
        result = sphere.findIntersections(new Ray(new Point(2, 0, 0),
                new Vector(-1, -0.1, 0)));
        assertEquals(0, result.size(), "Wrong number of points");
        // TC12: Ray starts at sphere and goes outside (0 points)
        result = sphere.findIntersections(new Ray(new Point(2, 0, 0),
                new Vector(1, 0.1, 0)));
        assertEquals(0, result.size(), "Wrong number of points");

        // **** Group: Ray's line goes through the center
        // TC13: Ray starts before the sphere (2 points)
        result = sphere.findIntersections(new Ray(new Point(3, 0, 0),
                new Vector(-1, 0, 0)));
        assertEquals(2, result.size(), "Wrong number of points");
        // TC14: Ray starts at sphere and goes inside (1 points)
        result = sphere.findIntersections(new Ray(new Point(2, 0, 0),
                new Vector(-1, 0, 0)));
        assertEquals(1, result.size(), "Wrong number of points");
        // TC15: Ray starts inside (1 points)
        result = sphere.findIntersections(new Ray(new Point(0.5, 0, 0),
                new Vector(-1, 0, 0)));
        assertEquals(1, result.size(), "Wrong number of points");
        // TC16: Ray starts at the center (1 points)
        result = sphere.findIntersections(new Ray(new Point(1, 0, 0),
                new Vector(1, 0, 0)));
        assertEquals(1, result.size(), "Wrong number of points");
        // TC17: Ray starts at sphere and goes outside (0 points)
        result = sphere.findIntersections(new Ray(new Point(2, 0, 0),
                new Vector(0, 0, 0)));
        assertEquals(1, result.size(), "Wrong number of points");
        // TC18: Ray starts after sphere (0 points)
        result = sphere.findIntersections(new Ray(new Point(-1, 0, 0),
                new Vector(-1, 0, 0)));
        assertEquals(0, result.size(), "Wrong number of points");

        // **** Group: Ray's line is tangent to the sphere (all tests 0 points)
        // TC19: Ray starts before the tangent point
        result = sphere.findIntersections(new Ray(new Point(0, -1, 0),
                new Vector(0, 1, 0)));
        assertEquals(0, result.size(), "Wrong number of points");
        // TC20: Ray starts at the tangent point
        result = sphere.findIntersections(new Ray(new Point(2, 0, 0),
                new Vector(0, 1, 0)));
        assertEquals(0, result.size(), "Wrong number of points");
        // TC21: Ray starts after the tangent point
        result = sphere.findIntersections(new Ray(new Point(2, 1, 0),
                new Vector(0, 1, 0)));
        assertEquals(0, result.size(), "Wrong number of points");

        // **** Group: Special cases
        // TC19: Ray's line is outside, ray is orthogonal to ray start to sphere's center line
        result = sphere.findIntersections(new Ray(new Point(-1, 0, 0),
                new Vector(0, 1, 0)));
        assertEquals(0, result.size(), "Wrong number of points");

    }


}