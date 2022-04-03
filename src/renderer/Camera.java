package renderer;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

public class Camera {
    private Point place;
    private Vector vTo;
    private Vector vUp;
    private Vector vRight;
    private double distance;
    private double width;
    private double height;

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
        return  null;
    }

}
