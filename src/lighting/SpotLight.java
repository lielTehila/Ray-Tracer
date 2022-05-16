package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Util;
import primitives.Vector;

import static primitives.Util.alignZero;

public class SpotLight extends PointLight {
    private Vector direction;

    /***
     * primary constractor
     * @param direction the direction of the light
     * @param intensity intensity color
     * @param point the position of the light
       */
    public SpotLight(Color intensity, Point point,Vector direction) {
        super(intensity, point);
        this.direction = direction.normalize();
    }

//    /***
//
//     * @param d direction of the light
//     * @param intensity intensity of the light
//     * @param p position of the light source
//     */
//    public SpotLight(Vector d, Color intensity, Point p) {
//        super(intensity, p);
//        direction = d.normalize();
//    }

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
