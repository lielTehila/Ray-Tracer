package renderer;

import geometries.Geometries;
import primitives.Color;
import primitives.Point;
import primitives.Ray;
import scene.Scene;

import java.util.List;

public class RayTracerBasic extends RayTracer{
    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    /***
     * return the color of the point on the geometry
     * @param p point on a geometry
     * @return the color of the point
     */
    private Color calcColor(Point p) {
        return scene.getAmbientLight().getIntensity();
    }

    /***
     * return the color of the point that the ray arrive to
     * @param ray the ray that we send
     * @return the color of the point
     */
    @Override
    public Color traceRay(Ray ray) {
        List<Point> lst= scene.getGeometries().findIntersections(ray);
        Point closetPoint = ray.findClosestPoint(lst);
        if (closetPoint==null)
            return scene.getBackGround();
        else
            return calcColor(closetPoint);
    }



    }

