package renderer;

import geometries.Intersectable;
import primitives.Point;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CameraRayIntersectionsIntegrationTests {

    private void assertCountIntersections(Camera cam, Intersectable geo, int expected) {
        int count = 0;

        List<Point> allPoints = null;

        cam.setVPSize(3, 3);
        cam.setVPDistance(1);
        int nX =3;
        int nY =3;
        //view plane 3X3 (WxH 3X3 & nx,ny =3 => Rx,Ry =1)
        for (int i = 0; i < nY; ++i) {
            for (int j = 0; j < nX; ++j) {
                var intersections = geo.findIntersections(cam.constructRay(nX, nY, j, i));
                if (intersections != null) {
                    if (allPoints == null) {
                        allPoints = new LinkedList<>();
                    }
                    allPoints.addAll(intersections);
                }
                if(intersections != null)
                    count += intersections.size();
            }
        }

        System.out.format("there is %d points:%n", count);
        if (allPoints != null) {
            for (var item : allPoints) {
                System.out.println(item);
            }
        }
        System.out.println();

        assertEquals(expected, count, "Wrong amount of intersections");
    }
}
