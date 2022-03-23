package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class GeometriesTest {

    @Test
    void testFindIntersections() {
        Geometries forms = new Geometries(
                new Plane(new Point(2, 0, 0), new Vector(-1, 1, 0)),
                new Sphere( new Point(5, 0, 0),2d),
                new Triangle(new Point(8.5, -1, 0), new Point(7.5, 1.5, 1), new Point(7.5, 1.5, -1))
        );
        Ray ray;

        // ============ Equivalence Partitions Tests ==============
        // TC01: Some geo intersect
        ray = new Ray(new Point(1, 0, 0), new Vector(7, 3, 0));
        assertEquals(3, forms.findIntersections(ray).size(), "wrong intersections");

        // =============== Boundary Values Tests ==================
        // TC02: Empty collection
        ray = new Ray(new Point(1, 0, 0), new Vector(1, 0, 0));
        assertNull(new Geometries().findIntersections(ray), "It is empty!");

        // TC03: None geo intersect
        ray = new Ray(new Point(1, 0, 0), new Vector(1, 3, 0));
        assertNull(forms.findIntersections(ray), "wrong intersections");

        // TC04: Single geo intersect
        ray = new Ray(new Point(1, 0, 0), new Vector(4, 3, 0));
        assertEquals(1, forms.findIntersections(ray).size(), "wrong intersections");

        // TC05: All geo intersect
        ray = new Ray(new Point(1, 0, 0), new Vector(7, 1, 0));
        assertEquals(4, forms.findIntersections(ray).size(), "wrong intersections");
    }


    @Test
    void testAdd() {
    }
}