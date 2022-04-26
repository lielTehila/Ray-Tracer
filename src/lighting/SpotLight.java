package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class SpotLight extends PointLight {
    private Vector direction;

    /***
     *
     * @param d the direction of the light
     * @param intensity intensity color
     * @param p the position of the light
     * @param c discount factor
     * @param l discount factor
     * @param q discount factor
     */
    public SpotLight(Vector d, Color intensity, Point p, double c, double l, double q) {
        super(intensity, p, c, l, q);
        direction = d;
    }
}
