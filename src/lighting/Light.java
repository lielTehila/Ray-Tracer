package lighting;

import primitives.Color;

/**
 * General class for displaying a light source, contains the color of the lighting.
 */
abstract class Light {

    /**
     * light intensity as color
     */
    private Color intensity;

    /**
     * Constructor with parameters
     * @param c Color
     */
    protected Light(Color c)
    {
        intensity=c;
    }

    /**
     * getter from intensity
     * @return actual intensity
     */
    public Color getIntensity() {
        return intensity;
    }
}
