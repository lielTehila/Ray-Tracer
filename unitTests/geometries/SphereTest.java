package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

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
}