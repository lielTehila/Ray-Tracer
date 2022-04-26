package lighting;

import primitives.Color;

abstract class Light {


    private Color intensity;//light intensity as color
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
