package renderer;

import geometries.Intersectable;
import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import primitives.Point;
import primitives.Vector;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static renderer.CameraTest.ZERO_POINT;


public class CameraRayIntersectionsIntegrationTests {

    /**
     * it is an assert function that check if the expected number of intersection is right
     *
     * @param cam      camera
     * @param geo      a geometry
     * @param expected number of expected intersections
     */
    private void assertCountIntersections(Camera cam, Intersectable geo, int expected) {
        int count = 0;

        List<Point> allPoints = null;

        cam.setVPSize(3, 3);
        cam.setVPDistance(1);
        int nX = 3;
        int nY = 3;
        //view plane 3X3 (WxH 3X3 & nx,ny =3 => Rx,Ry =1)
        for (int i = 0; i < nY; ++i) {
            for (int j = 0; j < nX; ++j) {
                List<Point> intersections = geo.findIntersections(cam.constructRay(nX, nY, j, i));
                if (intersections != null) {
                    if (allPoints == null) {
                        allPoints = new LinkedList<>();
                    }
                    allPoints.addAll(intersections);
                }
                if (intersections != null)
                    count += intersections.size();
            }
        }

        System.out.format("there is %d points:%n", count);
        if (allPoints != null) {
            for (Point item : allPoints) {
                System.out.println(item);
            }
        }
        System.out.println();

        assertEquals(expected, count, "Wrong amount of intersections");
    }


    //test function for sphere
    @Test
    public void SphereFindIntersectionTests() {
        Camera cam1 = new Camera(Point.ZERO, new Vector(0, 0, -1), new Vector(0, -1, 0)).setVPDistance(1).setVPSize(3, 3);
        Camera cam2 = new Camera(new Point(0, 0, 0.5), new Vector(0, 0, -1), new Vector(0, -1, 0)).setVPDistance(1).setVPSize(3, 3);

        assertCountIntersections(cam1, new Sphere(new Point(0, 0, -15), 1), 2);
        // TC01: Small Sphere 2 points
        assertCountIntersections(cam1, new Sphere(new Point(0, 0, -3), 1), 2);

        // TC02: Big Sphere 18 points
        assertCountIntersections(cam2, new Sphere(new Point(0, 0, -2.5), 2.5), 18);

        // TC03: Medium Sphere 10 points
        assertCountIntersections(cam2, new Sphere(new Point(0, 0, -2), 2), 10);

        // TC04: Inside Sphere 9 points
        assertCountIntersections(cam2, new Sphere(new Point(0, 0, -1), 4), 9);

        //TC05: 0 intersections point
        assertCountIntersections(cam1, new Sphere(new Point(0, 0, 4), 1), 0);

    }


    //test function for plane
    @Test
    public void PlaneFindIntersectionTests() {
        Camera cam = new Camera(ZERO_POINT, new Vector(0, 0, 3), new Vector(0, 3, 0)).setVPSize(3, 3).setVPDistance(1);

        // TC01: Plane against camera 9 points
        assertCountIntersections(cam, new Plane(new Point(0, 0, 15), new Vector(0, 0, -3)), 9);

        // TC02: Plane with small angle 9 points
        assertCountIntersections(cam, new Plane(new Point(0, 0, 15), new Vector(0, -3, -6)), 9);

        // TC03: Plane parallel to lower rays 6 points
        assertCountIntersections(cam, new Plane(new Point(0, 0, 15), new Vector(0, -3, -3)), 6);

        // TC04: Beyond Plane 0 points
        assertCountIntersections(cam, new Plane(new Point(0, 0, 15), new Vector(0, -3, -3)), 6);

    }

    //test function for triangle
    @Test
    public void TriangleFindIntersectionTests() {
        Camera cam = new Camera(ZERO_POINT, new Vector(0, 0, -1), new Vector(0, -1, 0)).setVPSize(3, 3).setVPDistance(1);
        // TC01: Small triangle 1 point
        assertCountIntersections(cam, new Triangle(new Point(1, 1, -2), new Point(-1, 1, -2), new Point(0, -1, -2)), 1);

        // TC02: Medium triangle 2 points
        assertCountIntersections(cam, new Triangle(new Point(1, 1, -2), new Point(-1, 1, -2), new Point(0, -20, -2)), 2);


    }
}


