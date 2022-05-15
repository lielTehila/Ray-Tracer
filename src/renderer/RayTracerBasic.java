package renderer;

import geometries.FlatGeometry;
import geometries.Geometries;
import lighting.LightSource;
import primitives.*;
import scene.Scene;
import geometries.Intersectable.GeoPoint;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

public class RayTracerBasic extends RayTracer{

    private static final double DELTA = 0.1; //Fixed for first moving magnitude rays for shading rays

    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    /***
     * return the color of the point on the geometry
     * by calculate the ambientlight, emmision, intensity of the point
     * @param intersection point on a geometry
     * @return the color of the point
     */
    private Color calcColor(GeoPoint intersection, Ray ray) {
        return scene.getAmbientLight().getIntensity()//
                .add(calcLocalEffects(intersection, ray));
    }


    private Color calcLocalEffects(GeoPoint gp, Ray ray) {
        Color color = gp.geometry.getEmission();
        Vector v = ray.getDir ();
        Vector n = gp.geometry.getNormal(gp.point);
        double nv = alignZero(n.dotProduct(v));
        if (nv == 0) return color;
        Material material = gp.geometry.getMaterial();
        for (LightSource lightSource : scene.getLights()) {
            Vector l = lightSource.getL(gp.point);
            double nl = alignZero(n.dotProduct(l));
            if (nl * nv > 0) { // sign(nl) == sing(nv)
                if(unshaded(gp,lightSource ,n,nl,nv)) {
                    Color iL = lightSource.getIntensity(gp.point);
                    color = color.add(
                            iL.scale(calcDiffusive(material, nl)),
                            iL.scale(calcSpecular(material, n, l, nl, v)));
                }
            }
        }
        return color;
    }

    /***
     * Checking for shading between a point and the light source.
     * @param gp The geometric point being examined for non-shading between the point and the light source
     * @param lightSource
     * @param n
     * @return
     */
    private boolean unshaded(GeoPoint gp, LightSource lightSource , Vector n,double nl, double nv)
    {
        Point point = gp.point;
        Vector lightDirection = lightSource.getL(point).scale(-1); // from point to light source

        Vector delta = n.scale(nv<0 ? DELTA : - DELTA);
        Point pointRay = point.add(delta);
        Ray lightRay = new Ray(pointRay, lightDirection);

        double maxDistance = lightSource.getDistance(point);
        List<GeoPoint> intersections = scene.getGeometries().findGeoIntersections(lightRay,maxDistance);

//        // Flat geometry can't self-intersect
//        if (gp.geometry instanceof FlatGeometry) {
//            intersections.remove(gp);
//        }
//
        return intersections==null;
    }
    /**
     * calc the diffusive light effect on the specific point
     * @param material the contain the attenuation and shininess factors
     * @param nl dot-product n*l
     * @return double3 of the diffusive factor
     */
    private Double3 calcDiffusive(Material material,double nl) {
        double abs_nl = Math.abs(nl);
        return  material.getkD().scale(abs_nl);
    }

    /**
     * calc the specular light effect on the specific point
     * @param material the contain the attenuation and shininess factors
     * @param n  normal to surface at the point
     * @param l direction from light to point
     * @param nl dot-product n*l
     * @param v  direction from point of view to point
     * @return double3 of the scapular factor
     */
    private Double3 calcSpecular(Material material,Vector n,Vector l,double nl,Vector v) {
        // nl is the dot product among the vector from the specular light to the point and the normal vector of the point
        //nl must not be zero
        if (isZero(nl)) {
            throw new IllegalArgumentException("nl cannot be Zero for scaling the normal vector");
        }
        Vector r = l.subtract(n.scale(2 * nl));
        double vr = alignZero(v.dotProduct(r));
        if (vr >= 0) {
            return Double3.ZERO; // view from direction opposite to r vector
        }
        return material.getkS().scale(Math.pow(-1d * vr,material.getShininess()));
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

