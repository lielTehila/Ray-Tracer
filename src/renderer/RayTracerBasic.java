package renderer;

import geometries.Geometries;
import primitives.Color;
import primitives.Point;
import primitives.Ray;
import scene.Scene;
import geometries.Intersectable.GeoPoint;

import java.util.List;

public class RayTracerBasic extends RayTracer{
    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    /***
     * return the color of the point on the geometry
     * by calculate the ambientlight, emmision, intensity of the point
     * @param p point on a geometry
     * @return the color of the point
     */
    private Color calcColor(GeoPoint p,Ray ray) {
        Color result=scene.getAmbientLight().getIntensity();
        result=result.add(p.geometry.getEmission());
        return result;
    }


    /***
     * return the color of the point that the ray arrive to
     * @param ray the ray that we send
     * @return the color of the point
     */
    @Override
    public Color traceRay(Ray ray) {
        List<GeoPoint> lst= scene.getGeometries().findGeoIntersections(ray);

        if(lst == null){
            return scene.getBackGround();
        }
        GeoPoint closetPoint = ray.findClosestGeoPoint(lst);
        return calcColor(closetPoint,ray);
    }



    }

