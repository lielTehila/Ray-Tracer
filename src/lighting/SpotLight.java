package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Util;
import primitives.Vector;

public class SpotLight extends PointLight {
    private Vector direction;

    /***
     *
     * @param direction the direction of the light
     * @param intensity intensity color
     * @param point the position of the light
       */
    public SpotLight(Color intensity, Point point,Vector direction) {
        super(intensity, point);
        this.direction = direction.normalize();
    }

    /***
     * default constractor
     * @param d direction of the light
     * @param intensity intensity of the light
     * @param p position of the light source
     */
    public SpotLight(Vector d, Color intensity, Point p) {
        super(intensity, p);
        direction = d;
    }

    @Override
    public Color getIntensity(Point p) {

        //if the vector orthogonal to the direction so the light not affect on the point
        if(Util.isZero(direction.dotProduct(getL(p)))){
            return Color.BLACK;
        }
        Vector l = super.getL(p);
        double factor = Math.max(0, direction.dotProduct(l));

        return super.getIntensity(p).scale(factor);

    }

}
