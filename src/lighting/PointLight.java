package lighting;

import geometries.Geometries;
import primitives.Color;
import primitives.Point;
import scene.Scene;

public class PointLight extends Light {
    private final Point position;
    private double kC;
    private double kL;
    private double kQ;

    /***
     * constractor with default values for the discount factors
     * @param intensity intensity color
     * @param p the position of the light
     */
    public PointLight(Color intensity, Point p) {
        super(intensity);
        this.position = p;
        kC = 1;
        kL = 0;
        kQ = 0;
    }

    public PointLight setKC(double kc) {
        this.kC = kc;
        return this;
    }

    public PointLight setKL(double kl) {
        this.kL = kl;
        return this;
    }

    public PointLight setKQ(double kq) {
        this.kQ = kq;
        return this;
    }
}
