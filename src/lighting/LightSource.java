package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public interface LightSource {
    /***
     * calaulate the intensity in specific point
     * @param p specific point
     * @return the intensity in this point
     */
    public Color getIntensity(Point p);

    /***
     * light vector
     * @param p position of the point
     * @return normalize vector from the position to the point
     */
    public Vector getL(Point p);


    double getDistance(Point point);
}
