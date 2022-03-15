package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;
/**
 * test for {@link Polygon}
 * Unit tests for geometries.Polygon class
 * @author Yiska levi and liel tehila simchi
 */
class PolygonTest {

    /**
     * Test method for {@link geometries.Polygon#getNormal(primitives.Point)}.
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        Polygon p = new Polygon(new Point(1,0,0),new Point(0,1,0),new Point(0,0,1));
        double norm = Math.sqrt(1/3d);
        assertEquals(new Vector(norm,norm,norm), p.getNormal(new Point(1,0,0)),"Bad normal to plane");

    }
}