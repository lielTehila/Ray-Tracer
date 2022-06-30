package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

import java.util.List;

/**
 * Directional lighting, has no source but only direction.
 */
public class DirectionalLight extends Light implements LightSource {
    private Vector direction;
    /***
     * the constructor with params
     * @param c color
     */
    public DirectionalLight(Color c,Vector v){
        super(c);
        direction=v.normalize();
    }

    @Override
    public Color getIntensity(Point p) {
        return getIntensity();
    }

    @Override
    public Vector getL(Point p) {
        return direction;
    }

    @Override
    public double getDistance(Point point) {
        return Double.POSITIVE_INFINITY;
    }
    @Override
    public List<Vector> getListRound(Point point, double beamRadius, double SsRayCounter) {
        return  List.of(this.direction.normalize());
    }
}
