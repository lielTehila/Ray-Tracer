package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static primitives.Util.isZero;

public class Camera {
    private Point place;
    private Vector vTo;
    private Vector vUp;
    private Vector vRight;
    private double distance;
    private double width;
    private double height;
    private ImageWriter imageWriter;
    private RayTracer rayTracer;

    /***
     * constructor of camera check the vectors up and to are orthogonal
     * @param p place
     * @param up vector up
     * @param to vector to
     */
    public Camera(Point p,Vector up,Vector to)
    {
        if(up.dotProduct(to)!=0)
        {
            throw new IllegalArgumentException();
        }
        vTo=to.normalize();
        vUp=up.normalize();
        vRight=to.crossProduct(up).normalize();
        place=p;

    }

    /**
     * set the size of view plane
     * @param wh width
     * @param ht height
     * @return camera
     */
    public Camera setVPSize(double wh, double ht)
    {
        width=wh;
        height=ht;
        return this;
    }

    /**
     * set the distance of the view plane from the camera
     * @param d double
     * @return camera
     */
    public Camera setVPDistance(double d)
    {
        distance=d;
        return this;
    }

    /**
     * return the place
     * @return place
     */
    public Point getPlace() {
        return place;
    }

    /**
     * return the v to
     * @return
     */
    public Vector getVTo() {
        return vTo;
    }

    /**
     * return the v up
     * @return
     */
    public Vector getVUp() {
        return vUp;
    }

    /**
     * return the vRight
     * @return
     */
    public Vector getVRight() {
        return vRight;
    }

    /**
     * return distance
     * @return
     */
    public double getDistance() {
        return distance;
    }

    /**
     * return width
     * @return
     */
    public double getWidth() {
        return width;
    }

    /**
     * return Height
     * @return
     */
    public double getHeight() {
        return height;
    }

    /**
     *
     * @param nX
     * @param nY
     * @param j
     * @param i
     * @return
     */
    public Ray constructRay(int nX, int nY, int j, int i)
    {
        Point Pc = place.add(vTo.scale(distance));
        double Ry= height/nY;
        double Rx= width/nX;
        double yi=-(i-(nY-1)/2)*Ry;
        double xj=(j-(nX-1)/2)*Rx;
        Point pij = Pc;
        if (isZero(xj) && isZero(yi)) {
            return new Ray(place, pij.subtract(place));
        }
        if (isZero(xj)) {
            pij = pij.add(vUp.scale(yi));
            return new Ray(place, pij.subtract(place));
        }
        if (isZero(yi)) {
           pij = pij.add(vRight.scale(xj));
            return new Ray(place, pij.subtract(place));
        }

        pij =pij.add(vRight.scale(xj).add(vUp.scale(yi)));
        Vector vij=pij.subtract(place);
        return  new Ray(place,vij);
    }

    public Camera setImageWriter(ImageWriter imageWriter) {
        this.imageWriter = imageWriter;
        return this;
    }

    public Camera setRayTracer(RayTracer rayTracer) {
        this.rayTracer = rayTracer;
        return this;
    }

    public void renderImage() {
        //read the matze
    }
/*
    public void printGrid(int gap, Color color) {
        imageWriter.printGrid(gap,color);
    }
 */

    public void writeToImage() {
        imageWriter.writeToImage();

    }
}
