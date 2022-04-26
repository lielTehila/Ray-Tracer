package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import scene.Scene;

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
    public Camera(Point p, Vector to, Vector up) {
        if (!isZero(up.dotProduct(to))) {
            throw new IllegalArgumentException();
        }

        vTo = to.normalize();
        vUp = up.normalize();

        vRight = vTo.crossProduct(vUp);

        place = p;

    }

    /**
     * set the size of view plane
     *
     * @param width  width of the view plane
     * @param height height of the view plane
     * @return camera
     */
    public Camera setVPSize(double width, double height) {
        this.width = width;
        this.height = height;
        return this;
    }

    /**
     * set the distance of the view plane from the camera
     *
     * @param d double
     * @return camera
     */
    public Camera setVPDistance(double d) {
        distance = d;
        return this;
    }

    /**
     * return the place
     *
     * @return place
     */
    public Point getPlace() {
        return place;
    }

    /**
     * return the v to
     *
     * @return
     */
    public Vector getVTo() {
        return vTo;
    }

    /**
     * return the v up
     *
     * @return
     */
    public Vector getVUp() {
        return vUp;
    }

    /**
     * return the vRight
     *
     * @return
     */
    public Vector getVRight() {
        return vRight;
    }

    /**
     * return distance
     *
     * @return
     */
    public double getDistance() {
        return distance;
    }

    /**
     * return width
     *
     * @return
     */
    public double getWidth() {
        return width;
    }

    /**
     * return Height
     *
     * @return
     */
    public double getHeight() {
        return height;
    }

    /**
     * calaulate and return the ray from the camera to the specific pixel
     *
     * @param nX-   Pixel size in a row
     * @param nY-   Pixel size in a column
     * @param j-The number of pixels to move in a row
     * @param i-The number of pixels to move in a column
     * @return the ray from the camera to the specific pixel
     */
    public Ray constructRay(int nX, int nY, int j, int i) {

        Point Pc = place.add(vTo.scale(distance));

        double Ry = height / nY;
        double Rx = width / nX;

        double yi = -(i - (nY - 1) / 2d) * Ry;
        double xj = (j - (nX - 1) / 2d) * Rx;

        Point pij = Pc;

        //if there is no moving
        if (isZero(xj) && isZero(yi)) {
            return new Ray(place, pij.subtract(place));
        }
        //if the moving is only at column
        if (isZero(xj)) {
            pij = pij.add(vUp.scale(yi));
            return new Ray(place, pij.subtract(place));
        }
        //if the moving is only at row
        if (isZero(yi)) {
            pij = pij.add(vRight.scale(xj));
            return new Ray(place, pij.subtract(place));
        }
        //pij is the point after the moving
        pij = pij.add(vRight.scale(xj).add(vUp.scale(yi)));


        return new Ray(place, pij.subtract(place));


    }

    /**
     * set ImageWriter
     *
     * @param imageWriter
     * @return
     */
    public Camera setImageWriter(ImageWriter imageWriter) {
        this.imageWriter = imageWriter;
        return this;
    }

    /***
     * set RayTracer
     * @param rayTracer
     * @return
     */
    public Camera setRayTracer(RayTracer rayTracer) {
        this.rayTracer = rayTracer;
        return this;
    }

    /**
     * build the image with printing the geometries and the background
     */
    public void renderImage() {
       // if (place == null || vTo == null || vUp == null || vRight == null || width == 0 || height == 0 || imageWriter == null || rayTracer == null)
//        if (this == null ||  imageWriter == null || rayTracer == null)
//            throw new UnsupportedOperationException();
        int Nx = imageWriter.getNx();
        int Ny = imageWriter.getNy();

        for (int i = 0; i < Ny; i++) {
            for (int j = 0; j < Nx; j++) {
                castRay(Nx, Ny, i, j);
            }
        }
        imageWriter.writeToImage();

    }

    private void castRay(int Nx, int Ny, int i, int j) {
        Ray ray = constructRay(Nx, Ny, j, i);
        Color pixelColor = rayTracer.traceRay(ray);
        imageWriter.writePixel(j, i, pixelColor);
    }


    public void writeToImage() {
        imageWriter.writeToImage();
    }

    public void printGrid(int interval, Color color) {
//        if (color == null)
//            throw new UnsupportedOperationException();
        imageWriter.printGrid(interval, color);
    }

}
