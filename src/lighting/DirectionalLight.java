package lighting;

import primitives.Color;
import primitives.Vector;

public class DirectionalLight extends Light {
    private Vector direction;
    /***
     * the constructor with params
     * @param c color
     */
    public DirectionalLight(Color c,Vector v){
        super(c);
        direction=v;
    }
}
