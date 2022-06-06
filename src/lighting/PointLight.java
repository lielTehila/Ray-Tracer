package lighting;

import geometries.Geometries;
import primitives.Color;
import primitives.Double3;
import primitives.Point;
import primitives.Vector;
import scene.Scene;
import java.util.Random;


import java.util.LinkedList;
import java.util.List;

import static primitives.Util.isZero;

public class PointLight extends Light  implements LightSource{
    private final Point position;
    private Double3 kC;
    private Double3 kL;
    private Double3 kQ;

    /***
     * constractor with default values for the discount factors
     * @param intensity intensity color
     * @param p the position of the light
     */
    public PointLight(Color intensity, Point p) {
        super(intensity);
        this.position = p;
        kC = new Double3(1);
        kL = new Double3(0);
        kQ = new Double3(0);
    }

    public PointLight setKc(Double3 kc) {
        this.kC = kc;
        return this;
    }
    public PointLight setKc(double kc) {
        this.kC = new Double3(kc);
        return this;
    }

    public PointLight setKl(Double3 kl) {
        this.kL = kl;
        return this;
    }

    public PointLight setKl(double kl) {
        this.kL = new Double3(kl);
        return this;
    }

    public PointLight setKq(Double3 kq) {
        this.kQ = kq;
        return this;
    }
    public PointLight setKq(double kq) {
        this.kQ = new Double3(kq);
        return this;
    }

    @Override
    public Color getIntensity(Point p) {

        double distance = position.distance(p);
        Double3 denominator = (kC.add(kL.scale(distance)).add(kQ.scale(distance*distance)));
        return getIntensity().reduce(denominator);
    }

    @Override
    public Vector getL(Point p) {
        //check that we will not get ZERO vector
        if(position.equals(p)){
            return null;
        }
        return p.subtract(position).normalize();
    }

    @Override
    public double getDistance(Point point) {
        return point.distance(this.position);
    }

    @Override
    public List<Vector> getListRound(Point point, double beamRadius, double SsRayCounter)
    {
        List<Vector> beam = new LinkedList<>();

        // first we'll add the vector from the point to the position of the pointLight
        Vector v = this.getL(point);
        beam.add(v);

        // if the ray counter is less than 2 then there is only one vector
        // the vector v we've calculated above
        if (SsRayCounter <= 1) {
            return beam;
        }

        // the distance between the point and the point light
        double distance = this.position.distance(point);

        // the distance can't be zero
        if (isZero(distance)) {
            throw new IllegalArgumentException("distance cannot be 0");
        }

        Vector normalX;

        // if v = (0,0,z) then the normal of x is (-z,0,0)
        // else the normal of x is (-y,x,0)
        if (v.get_x() == 0 && v.get_y() == 0) {
            normalX = new Vector(v.get_z() * -1, 0, 0).normalize();
        }
        else {
            normalX = new Vector(v.get_y() * -1, v.get_x(), 0).normalize();
        }

        // the normal for y will be the cross product between v and the normal of x
        Vector normalY = v.crossProduct(normalX).normalize();

        // cos($), sin($)
        double cosTheta, sinTheta;

        // x,y values of the points in the beam
        double x, y;

        // the point that help us create the vectors to the beam
        Point newPoint;
        Random rand = new Random();

        // a loop to create SsRayCounter amount of vectors in the given beamRadius
        for (int i = 0; i < SsRayCounter; i++) {
            newPoint = this.position; // initialize with the point light's position

            // we chose random cos($), sin($) between -1 and 1
            cosTheta = 2 * rand.nextDouble() - 1;

            // sin ^ 2 + cos ^ 2 = 1
            sinTheta = Math.sqrt(1d - cosTheta * cosTheta);

            // range is a random number < |beamRadius|
            double range = beamRadius * (2 * rand.nextDouble() - 1);

            // if its zero then the new vector will be equal to v - so we'll ignore this, and continue
            if (range == 0) {
                i--;
                continue;
            }

            // define the x,y values inside the radius
            x = range * cosTheta;
            y = range * sinTheta;

            if (x != 0) {
                newPoint = newPoint.add(normalX.scale(x));
            }

            if (y != 0) {
                newPoint = newPoint.add(normalY.scale(y));
            }

            beam.add(point.subtract(newPoint).normalize());
            //beam.add(this.getL(newPoint));
        }
        return beam;
    }
}
