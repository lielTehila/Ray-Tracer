package lighting;

import primitives.Color;
import primitives.Double3;

/**
 * ambient light for all graghic objects
 */
public class AmbientLight {


    private  final Color intensity;  //light intensity as color

    /**
     * primary constractor
     * @param Ia basic intensity light
     * @param Ka attention
     */
    public AmbientLight(Color Ia, Double3 Ka){
        intensity= Ia.scale(Ka);
    }


    /**
     * default constractor
     */
    public AmbientLight(){
        intensity=Color.BLACK;
    }

    /**
     * getter from intensity
     * @return actual intensity
     */

    public Color getIntensity() {
        return intensity;
    }

}
