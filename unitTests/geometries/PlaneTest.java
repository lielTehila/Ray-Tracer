package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;
/**
 * test for {@link Plane}
 * Unit tests for geometries.Plane class
 * @author Yiska levi and liel tehila simchi
 */

class PlaneTest {

    /**
     * Test method for {@link geometries.Plane#getNormal(primitives.Point)}
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        Plane plane = new Plane(new Point(1,0,0),new Point(0,1,0),new Point(0,0,1));
        double norm = Math.sqrt(1/3d);
        assertEquals(new Vector(norm,norm,norm), plane.getNormal(new Point(1,0,0)),"Bad normal to plane");

    }

    /**
     * Test method for {@link geometries.Plane#findIntersections(primitives.Ray)}.
     */
    @Test
    public void testfindIntersectionsRay() {

    }


}