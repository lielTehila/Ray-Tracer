package renderer;

import geometries.FlatGeometry;
import geometries.Geometries;
import geometries.Geometry;
import lighting.LightSource;
import primitives.*;
import scene.Scene;
import geometries.Intersectable.GeoPoint;

import java.util.List;


import static primitives.Double3.ZERO;
import static primitives.Util.alignZero;
import static primitives.Util.isZero;

public class RayTracerBasic extends RayTracer{

    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;

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
    // שיניתי את K לDOUBLE3 כי הוא היה DOUBLE רגיל
    private Color calcColor(GeoPoint gp, Ray ray, int level, double k) {
        Color color = scene.getAmbientLight().getIntensity()
                .add(calcLocalEffects(gp, ray));
        return 1 == level ? color : color = color.add(calcGlobalEffects(gp, ray, level, k));
    }


    private Color calcGlobalEffects(GeoPoint gp, Ray inRay, int level, double k)
    {
        Color color = Color.BLACK;
        Double3 kr = gp.geometry.getMaterial().kR;
        Double3 kkr = kr.scale(k);
        //Double3 kkr = kr.scale(k);
        Geometry geometryGeo = gp.geometry;
        Point geoPoint =gp.point;
        Vector n = geometryGeo.getNormal(geoPoint);
        if (!kkr.lowerThan(MIN_CALC_COLOR_K)){
            Ray reflectedRay = constructReflectedRay( geoPoint, inRay,n);
            color = color.add(secondaryRayColor(level - 1, kr, kkr, reflectedRay));
//            GeoPoint reflectedPoint = findClosestIntersection(reflectedRay);
//            if (…){
//                color = color.add(calcColor(reflectedPoint, reflectedRay,level-1,kkr)
//                        .scale(kr));
//            }
        }

        Double3 kt = gp.geometry.getMaterial().kT;
        Double3 kkt = kt.scale(k);
        if (!kkt.lowerThan(MIN_CALC_COLOR_K)){
            Ray refractedRay = constructRefractedRay(geoPoint, inRay, n);
            color = color.add(secondaryRayColor(level - 1, kt, kkt, refractedRay));

//            Ray refractedRay = constructRefractedRay(gp.point, inRay);
//            GeoPoint refractedPoint = findClosestIntersection(refractedRay);
//            if (…) {
//                color = color.add(calcColor(refractedPoint, refractedRay)
//                    .scale(kt);
//            }
        }

        return color;
    }
    private Ray constructRefractedRay(Point pointGeo, Ray inRay, Vector n) {
        return new Ray(pointGeo, inRay.getDir(), n);
    }
    private Color secondaryRayColor(int level, Double3 scalefactor, Double3 kkrt, Ray secondaryRay) {
        GeoPoint secondaryGeoPoint = findClosestIntersection(secondaryRay);
        Color color = Color.BLACK;
        if (secondaryGeoPoint != null) {
            color = calcColor(secondaryGeoPoint, secondaryRay, level, kkrt).scale(scalefactor);
        }
        return color;
    }
    private Ray constructReflectedRay(Point pointGeo, Ray inRay, Vector n) {
        //r = v - 2.(v.n).n
        Vector v = inRay.getDir();
        double vn = v.dotProduct(n);

        if (vn == 0) {
            return null;
        }

        Vector r = v.subtract(n.scale(2 * vn));
        return new Ray(pointGeo, r, n);
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
                if(unshaded(lightSource,l,n,gp)) {
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
    //GeoPoint geopoint,LightSource lightSource , Vector n,double nl, double nv
    //GeoPoint gp, LightSource lightSource , Vector n,double nl, double nv
    private boolean unshaded(LightSource light, Vector l, Vector n, GeoPoint geopoint)
    {
        Vector lightDirection = l.scale(-1); // from point to light source
        Point pointGeo = geopoint.point;
        Ray lightRay = new Ray(pointGeo, lightDirection, n);

        List<GeoPoint> intersections = scene.getGeometries().findGeoIntersections(lightRay);
        if (intersections == null) {
            return true;
        }
        double lightDistance = light.getDistance(pointGeo);
        for (GeoPoint gp : intersections) {
            if (alignZero(gp.point.distance(pointGeo) - lightDistance) <= 0
                    && gp.geometry.getMaterial().kT.equals(0) ) {
                return false;
            }
        }
        return true;


//        Point pointGeo = gp.point;
//
//        Vector lightDirection = lightSource.getL(pointGeo).scale(-1); // from point to light source
//
////        Vector delta = n.scale(nv<0 ? DELTA : - DELTA);
////        Point pointRay = point.add(delta);
//        Ray lightRay = new Ray(pointGeo, lightDirection,n);
//        double maxDistance = lightSource.getDistance(pointGeo);
//        List<GeoPoint> intersections = scene.getGeometries().findGeoIntersections(lightRay,maxDistance);
//        return intersections==null;

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
            return ZERO; // view from direction opposite to r vector
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

    /**
     * find the closest geo point
     * @param ray the ray that create intersection points
     * @return closest geo point
     */
    private GeoPoint findClosestIntersection(Ray ray)
    {
        if (ray == null) {
            return null;
        }

        GeoPoint theClosestPoint = null;
        double closestDistance = Double.MAX_VALUE;
        Point ray_p0 = ray.getP0();

        List<GeoPoint> intersections = scene.getGeometries().findGeoIntersections(ray);
        if (intersections == null)
            return null;

        for (GeoPoint geoPoint : intersections) {
            double distance = ray_p0.distance(geoPoint.point);
            if (distance < closestDistance) {
                closestDistance = distance;
                theClosestPoint = geoPoint;
            }
        }
        return theClosestPoint;
    }


    }

