package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class DirectionalLight extends Light implements LightSource {
    private Vector direction;
    /***
     * the constructor with params
     * @param c color
     */
    public DirectionalLight(Color c,Vector v){
        super(c);
        direction=v;
    }

    @Override
    public Color getIntensity(Point p) {
        return getIntensity();
    }

    @Override
    public Vector getL(Point p) {
        return direction;
    }
}
