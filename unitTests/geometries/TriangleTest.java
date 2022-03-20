package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TriangleTest {

    /**
     * Test method for {@link geometries.Triangle#findIntersections(Ray ray)}.
     */
    @Test
    void testFindIntersections() {
        Triangle triangle = new Triangle(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0));
        Plane pl = new Plane(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0));
        Ray ray;
        // ============ Equivalence Partitions Tests ==============
        // TC01: Inside triangle
        ray = new Ray(new Point(2, 2, 2), new Vector(-3, -3, -3));
        assertEquals(List.of(new Point(1d / 3, 1d / 3, 1d / 3)), triangle.findIntersections(ray),
                "Bad intersection");

        // TC02: Against edge
        ray = new Ray(new Point(0, 0, -2), new Vector(2, 2, 0));
        assertEquals(List.of(new Point(1, 1, -2)), pl.findIntersections(ray),
                "Wrong intersection with plane");
        assertNull(triangle.findIntersections(ray), "Bad intersection");

        // TC03: Against vertex
        ray = new Ray(new Point(0, 0, 2), new Vector(-1, -1, 0));
        assertEquals(List.of(new Point(-0.5, -0.5, 2)), pl.findIntersections(ray),
                "Wrong intersection with plane");
        assertNull(triangle.findIntersections(ray), "Bad intersection");

        // =============== Boundary Values Tests ==================
        // TC11: In vertex
        ray = new Ray(new Point(0, 0, -1), new Vector(0, 1, 1));
        assertEquals(List.of(new Point(0, 1, 0)), pl.findIntersections(ray),
                "Wrong intersection with plane");
        assertNull(triangle.findIntersections(ray), "Bad intersection");

        // TC12: On edge
        ray = new Ray(new Point(-2, -2, 0), new Vector(1, 1, 0));
        assertEquals(List.of(new Point(0.5, 0.5, 0)), pl.findIntersections(ray),
                "Wrong intersection with plane");
        assertNull(triangle.findIntersections(ray), "Bad intersection");

        // TC13: On edge continuation
        ray = new Ray(new Point(-2, 0, 0), new Vector(0, 1, 0));
        assertEquals(List.of(new Point(-2, 2, 0)), pl.findIntersections(ray),
                "Wrong intersection with plane");
        assertNull(triangle.findIntersections(ray), "Bad intersection");

    }
}