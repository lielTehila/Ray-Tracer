package renderer;

import primitives.*;
import primitives.Vector;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;
import static renderer.Pixel.printInterval;

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
    static public boolean withTheads = true;
    static public boolean antialiasing = true;
    static public boolean softShadows = true;
    static public boolean adaptiveSuperSampling = true;


    /**
     * Height of the grid for picture improvements
     */
    private int _N = 8;

    /**
     * Width of the grid for picture improvements
     */
    private int _M = 8;

    /**
     * random variable used for stochastic ray creation
     */
    private final Random r = new Random();

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
     * @param d double
     * @return camera
     */
    public Camera setVPDistance(double d) {
        distance = d;
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
     * @return vector to
     */
    public Vector getVTo() {
        return vTo;
    }

    /**
     * return the v up
     * @return vector up
     */
    public Vector getVUp() {
        return vUp;
    }

    /**
     * return the vRight
     * @return vRight
     */
    public Vector getVRight() {
        return vRight;
    }

    /**
     * return distance
     * @return distance
     */
    public double getDistance() {
        return distance;
    }

    /**
     * @return width
     */
    public double getWidth() {
        return width;
    }

    /**
     * @return Height
     */
    public double getHeight() {
        return height;
    }

    /**
     * calaulate and return the ray from the camera to the specific pixel
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
     * @param imageWriter get new imageWriter
     * @return this
     */
    public Camera setImageWriter(ImageWriter imageWriter) {
        this.imageWriter = imageWriter;
        return this;
    }

    /***
     * set RayTracer
     * @param rayTracer get new rayTracer
     * @return this
     */
    public Camera setRayTracer(RayTracer rayTracer) {
        this.rayTracer = rayTracer;
        return this;
    }

    /**
     * render image with params
     * @param _withTheads            determine if the render func will act with threads
     * @param _antialiasing          determine if the render picture will have an improvment of antialiasing
     * @param _softShadows           determine if the render picture will have an improvment of softShadows
     * @param _adaptiveSuperSampling determine if the render func will act with improve run time of adaptiveSuperSampling
     * @return this
     */
    public Camera renderImage(boolean _withTheads, boolean _antialiasing, boolean _softShadows, boolean _adaptiveSuperSampling) {
        //set the demands in the global variables
        withTheads = _withTheads;
        antialiasing = _antialiasing;
        softShadows = _softShadows;
        adaptiveSuperSampling = _adaptiveSuperSampling;

        if (!withTheads)
            renderImageSimple();
        renderImage();

        return this;
    }
    public Camera renderImage() {
        int Nx = imageWriter.getNx();
        int Ny = imageWriter.getNy();
        Pixel.initialize(Ny, Nx, printInterval);
        IntStream.range(0, Ny).parallel().forEach(i -> {
            IntStream.range(0, Nx).parallel().forEach(j -> {
                if (!antialiasing) {  //build picture without antialiasing improve
                    castRaySimple(Nx, Ny, i, j);
                    Pixel.pixelDone();
                    Pixel.printPixel();
                } else if (antialiasing && adaptiveSuperSampling) {  //build picture with antialiasing improve and run-time improve
                    castRayImprovedAntialising(Nx, Ny, i, j);
                    Pixel.pixelDone();
                    Pixel.printPixel();
                } else { //  //build picture with antialiasing improve
                    castRayRegularAntialiasing(Nx, Ny, i, j);
                    Pixel.pixelDone();
                    Pixel.printPixel();
                }

            });
        });
        return this;
    }

    /**
     * render without threads
     * @return this
     */
    public Camera renderImageSimple() {
        int Nx = imageWriter.getNx();
        int Ny = imageWriter.getNy();
        for (int i = 0; i < Ny; i++) {
            for (int j = 0; j < Nx; j++) {
                if (!antialiasing) {  //build picture without antialiasing improve
                    castRaySimple(Nx, Ny, i, j);
                } else if (antialiasing && adaptiveSuperSampling) {  //build picture with antialiasing improve and run-time improve
                    castRayImprovedAntialising(Nx, Ny, i, j);
                } else { //  //build picture with antialiasing improve
                    castRayRegularAntialiasing(Nx, Ny, i, j);
                }

            }
        }
        imageWriter.writeToImage();
        return this;
    }


    /***
     * create ray from camera to specific pixel
     * castRay with antialiasing without its run-time improvment
     * @param Nx - Pixel size in a row
     * @param Ny -Pixel size in a column
     * @param i -The number of pixels to move in a column
     * @param j-The number of pixels to move in a row
     */
    private void castRayRegularAntialiasing(int Nx, int Ny, int i, int j) {
        //improving of anti-aliasing
        int bigNy = 9 * Ny;
        int bigNx = 9 * Nx;
        Color pixelColor = new Color(java.awt.Color.BLACK);
        for (int iColumn = i * 9; iColumn < i * 9 + 9; iColumn++) {
            for (int jRow = j * 9; jRow < j * 9 + 9; jRow++) {

                Ray ray = constructRay(bigNx, bigNy, jRow, iColumn);
                pixelColor = pixelColor.add(rayTracer.traceRay(ray));
            }
        }
        pixelColor = pixelColor.reduce(81);
        imageWriter.writePixel(j, i, pixelColor);
    }

    /***
     * create ray from camera to specific pixel
     * castRay with antialiasing and with its run-time improvment - adaptive super sampling
     * @param Nx - Pixel size in a row
     * @param Ny -Pixel size in a column
     * @param i -The number of pixels to move in a column
     * @param j-The number of pixels to move in a row
     */
    private void castRayImprovedAntialising(int Nx, int Ny, int i, int j) {
        //improving of anti-aliasing
        int bigNy = 2 * Ny;
        int bigNx = 2 * Nx;
        Ray middleRay = constructRay(Nx, Ny, j, i);
        Color pixelColor = new Color(java.awt.Color.BLACK);
        pixelColor = pixelColor.add(rayTracer.traceRay(middleRay));
        java.awt.Color c1 = rayTracer.traceRay(middleRay).getColor();
        for (int iColumn = i * 2; iColumn < i * 2 + 2; iColumn++) {
            for (int jRow = j * 2; jRow < j * 2 + 2; jRow++) {
                Ray ray = constructRay(bigNx, bigNy, jRow, iColumn);
                java.awt.Color c2 = rayTracer.traceRay(ray).getColor();
                if (Math.abs(c1.getBlue() - c2.getBlue()) > 5 && Math.abs(c1.getGreen() - c2.getGreen()) > 5 && Math.abs(c1.getRed() - c2.getRed()) > 5)
                    pixelColor = pixelColor.add(castRayHelp(Nx, Ny, iColumn, jRow, rayTracer.traceRay(ray)));
                else
                    pixelColor = pixelColor.add(rayTracer.traceRay(ray));
            }
        }
        pixelColor = pixelColor.reduce(5);
        imageWriter.writePixel(j, i, pixelColor);
    }


    /***
     * create ray from camera to specific pixel
     * castRay without antialiasing
     * @param Nx - Pixel size in a row
     * @param Ny -Pixel size in a column
     * @param i -The number of pixels to move in a column
     * @param j-The number of pixels to move in a row
     */
    private void castRaySimple(int Nx, int Ny, int i, int j) {
        Ray ray = constructRay(Nx, Ny, j, i);
        Color pixelColor = rayTracer.traceRay(ray);
        imageWriter.writePixel(j, i, pixelColor);
    }

    /***
     * help function to castRay
     * @param Nx - Pixel size in a row
     * @param Ny -Pixel size in a column
     * @param i -The number of pixels to move in a column
     * @param j-The number of pixels to move in a row
     */
    private Color castRayHelp(int Nx, int Ny, int i, int j, Color pixelColor) {
        int bigNy = 2 * Ny;
        int bigNx = 2 * Nx;
        Ray middleRay = constructRay(Nx, Ny, j, i);
        Point m = new Point(rayTracer.traceRay(middleRay).getColor().getBlue(), rayTracer.traceRay(middleRay).getColor().getGreen(), rayTracer.traceRay(middleRay).getColor().getRed());
        for (int iColumn = i * 2; iColumn < i * 2 + 2; iColumn++) {
            for (int jRow = j * 2; jRow < j * 2 + 2; jRow++) {
                Ray ray = constructRay(bigNx, bigNy, jRow, iColumn);
                Point r = new Point(rayTracer.traceRay(ray).getColor().getBlue(), rayTracer.traceRay(ray).getColor().getGreen(), rayTracer.traceRay(ray).getColor().getRed());
                if (m.distance(r) > 10)
                    pixelColor = pixelColor.add(castRayHelp(Nx, Ny, iColumn, jRow, rayTracer.traceRay(ray)));
                else
                    pixelColor = pixelColor.add(rayTracer.traceRay(ray));
            }
        }
        pixelColor = pixelColor.reduce(5);
        return pixelColor;
    }

    public Camera writeToImage() {
        imageWriter.writeToImage();
        return this;
    }

    public void printGrid(int interval, Color color) {
//        if (color == null)
//            throw new UnsupportedOperationException();
        imageWriter.printGrid(interval, color);
    }


    /**
     * Function to find a specific point on the plane
     * we need to calculate the distance from the point on the camera to the plane
     * for that we use the cos of the angle of the direction ray with vTo vector
     * @param r ray to the specific point
     * @return the distance to the point
     */
    private Point getPointOnViewPlane(Ray r) {
        double t0 = distance;
        double t = t0 / (vTo.dotProduct(r.getDir())); //cosinus of the angle
        return r.getPoint(t);
    }

}
