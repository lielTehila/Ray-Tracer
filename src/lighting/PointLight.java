package lighting;

import geometries.Geometries;
import primitives.Color;
import primitives.Point;
import scene.Scene;

public class PointLight extends Light {
    private Point position;
    private double kC=1;
    private double kL=0;
    private double kQ=0;

    /***
     * constractor with default values for the discount factors
     * @param intensity intensity color
     * @param p the position of the light
     */
    public PointLight(Color intensity, Point p) {
        super(intensity);
        position = p;
    }

    public static class PointLightBuilder {

        private double kC=1;
        private double kL=0;
        private double kQ=0;

        public PointLightBuilder() {

        }
        //chaining methods

        public PointLightBuilder setKC(double kc) {
            this.kC = kc;
            return this;
        }
        public PointLightBuilder setKL(double kl) {
            this.kL = kl;
            return this;
        }
        public PointLightBuilder setKQ(double kq) {
            this.kQ = kq;
            return this;
        }

        public PointLight build() {
            PointLight pointLight = new PointLight(this);  //להוסיף בנאי של POINTLIGHT שמקבל אובייקט זה->, ולהבין איך צריך לאתחל את מקדמי ההנחתה
            return pointLight;
        }
    }
}
