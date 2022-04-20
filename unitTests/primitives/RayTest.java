package primitives;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RayTest {

    @Test
    void testFindClosestPoint() {
        Ray r = new Ray(new Point(1,0,0), new Vector(1,0,0));
        List<Point> lst1 = new ArrayList<Point>();
        lst1.add(new Point(0,0,0)) ;
        lst1.add(new Point(3,0,0)) ;
        List<Point> lst2 = new ArrayList<Point>();
        lst2.add(new Point(0,3,8)) ;
        lst2.add(new Point(3,0,0)) ;
        List<Point> lst3 = new ArrayList<Point>();
        lst3.add(new Point(0,4,6)) ;
        lst3.add(new Point(2,0,0)) ;
        lst3.add(new Point(10,8,-3)) ;

        // =============== Boundary Values Tests ==================
        //the list is empty
        assertNull(r.findClosestPoint(new ArrayList<>()));
        // the closet Point is first
        assertEquals(new Point(0,0,0),r.findClosestPoint(lst1));
        // the closet Point is last
        assertEquals(new Point(3,0,0),r.findClosestPoint(lst2));

        // ============ Equivalence Partitions Tests ==============
        //the closet point is not first and not last
        assertEquals(new Point(2,0,0),r.findClosestPoint(lst3));
    }
}