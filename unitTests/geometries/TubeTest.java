package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;
/**
 * for test {@link Tube}
 * Unit tests for geometries.Tube class
 * @author Yiska levi and liel tehila simchi
 */
class TubeTest {
    /**
     * Test method for {@link geometries.Tube#getNormal(primitives.Point)}.
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        Tube t = new Tube(1.0, new Ray(new Point(1,0,0),new Vector(0,0,1)));
        Vector norm = t.getNormal(new Point(1,1,3));
        //make sure that the norm is normalize to the axis ray
        double res = norm.dotProduct(t.axisRay.getDir());
        assertEquals(0d, res, "normal is not orthogonal to the tube");
        //check that the normal is right
        assertEquals(new Vector(0,1,0), norm,"bad normalize in tube" );
    }
}