package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Util;
import primitives.Vector;

import static primitives.Util.alignZero;

/**
 * The department represents spot lighting.
 */
public class SpotLight extends PointLight {
    /**
     * Vector lighting direction.
     */
    private Vector direction;

    /***
     * primary constructor
     * @param direction the direction of the light
     * @param intensity intensity color
     * @param point the position of the light
       */
    public SpotLight(Color intensity, Point point,Vector direction) {
        super(intensity, point);
        this.direction = direction.normalize();
    }

    @Override
    public Color getIntensity(Point p) {

        Vector l = super.getL(p);
        double dirl = alignZero(direction.dotProduct(l));

        //if the vector orthogonal to the direction so the light not affect on the point
        if(dirl == 0){
            return Color.BLACK;
        }
        double factor = Math.max(0, dirl);

        return super.getIntensity(p).scale(factor);

    }

}
