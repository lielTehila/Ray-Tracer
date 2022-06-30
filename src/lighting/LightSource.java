package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

import java.util.List;

/**
 *Lighting representation interface, contains functions for various calculations required for lighting.
 */
public interface LightSource {
    /***
     * calaulate the intensity in specific point
     * @param p specific point
     * @return the intensity in this point
     */
    public Color getIntensity(Point p);

    /***
     * light vector
     * @param p position of the point
     * @return normalize vector from the position to the point
     */
    public Vector getL(Point p);

    /**
     * Calculates the distance between the light source and the point.
     * @param point The point from which you want to calculate the distance
     * @return distance between the light source and the point.
     */
    double getDistance(Point point);

    /**
     *Builds a list of vectors in a radius around the point.
     * @param point on the geometry
     * @param beamRadius radius of the light
     * @param SsRayCounter the number of the different rayies to thr light
     * @return
     */
    public List<Vector> getListRound(Point point, double beamRadius, double SsRayCounter);
}
