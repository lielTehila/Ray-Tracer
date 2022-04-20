package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Unit tests for primitives.Point class
 * @author Yiska levi and liel tehila simchi
 */

class PointTest {

    @Test
    void testDistanceSquared() {
    }

    /**
     * check if the Distance() work well.
     */
    @Test
    void testDistance() {
        assertEquals(new Point(0,0,0).distance(new Point (1,1,1)),Math.sqrt(3));
        assertEquals(new Point(0,0,0).distance(new Point (0,0,0)),0);
        assertEquals(new Point(3,3,1).distance(new Point (-2,5,0)),Math.sqrt(30));


    }

    @Test
    void testAdd() {
    }

    @Test
    void testSubtract() {
        assertTrue(1==1);
    }
}