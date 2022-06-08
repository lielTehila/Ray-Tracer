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
     * @return vRight
     */
    public Vector getVRight() {
        return vRight;
    }

    /**
     * return distance
     *
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
     * build the image with printing the geometries and the background
     */
    public Camera renderImage() {
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
        return this;
    }

    /***
     * create ray from camera to specific pixel
     * @param Nx - Pixel size in a row
     * @param Ny -Pixel size in a column
     * @param i -The number of pixels to move in a column
     * @param j-The number of pixels to move in a row
     */
    //שיפור פיקסל
//    private void castRay(int Nx, int Ny, int i, int j) {
//        //improving of anti-aliasing
//        int bigNy = 9*Ny;
//        int bigNx = 9*Nx;
//        Color pixelColor=new Color(java.awt.Color.BLACK);
//        for (int iColumn = i*9; iColumn < i*9+9; iColumn++) {
//            for (int jRow = j*9; jRow < j*9+9; jRow++) {
//
//                Ray ray = constructRay(bigNx, bigNy, jRow, iColumn);
//                pixelColor =pixelColor.add(rayTracer.traceRay(ray)) ;
//            }
//        }
//        pixelColor=pixelColor.reduce(81);
//        imageWriter.writePixel(j, i, pixelColor);
//    }
    // של פיקסל שיפור ריצה
    private void castRay(int Nx, int Ny, int i, int j) {
        //improving of anti-aliasing
        int bigNy = 2*Ny;
        int bigNx = 2*Nx;
        Ray middleRay = constructRay(Nx, Ny, j, i);
        Color pixelColor=new Color(java.awt.Color.BLACK);
        pixelColor =pixelColor.add(rayTracer.traceRay(middleRay)) ;
        Point m=new Point(rayTracer.traceRay(middleRay).getColor().getBlue(),rayTracer.traceRay(middleRay).getColor().getGreen(),rayTracer.traceRay(middleRay).getColor().getRed());

        for (int iColumn = i*2; iColumn < i*2+2; iColumn++) {
            for (int jRow = j*2; jRow < j*2+2; jRow++) {
                Ray ray = constructRay(bigNx, bigNy, jRow, iColumn);
                Point r=new Point(rayTracer.traceRay(ray).getColor().getBlue(),rayTracer.traceRay(ray).getColor().getGreen(),rayTracer.traceRay(ray).getColor().getRed());
                if(m.distance(r)>10)
                    pixelColor =pixelColor.add(castRayHelp( Nx, Ny, iColumn, jRow,rayTracer.traceRay(ray)));
                else
                    pixelColor =pixelColor.add(rayTracer.traceRay(ray)) ;
            }
        }
        pixelColor=pixelColor.reduce(5);
        imageWriter.writePixel(j, i, pixelColor);
    }
    private Color castRayHelp(int Nx, int Ny, int i, int j,Color pixelColor) {
        int bigNy = 2*Ny;
        int bigNx = 2*Nx;
        Ray middleRay = constructRay(Nx, Ny, j, i);
        Point m=new Point(rayTracer.traceRay(middleRay).getColor().getBlue(),rayTracer.traceRay(middleRay).getColor().getGreen(),rayTracer.traceRay(middleRay).getColor().getRed());
        for (int iColumn = i*2; iColumn < i*2+2; iColumn++) {
            for (int jRow = j*2; jRow < j*2+2; jRow++) {
                Ray ray = constructRay(bigNx, bigNy, jRow, iColumn);
                Point r=new Point(rayTracer.traceRay(ray).getColor().getBlue(),rayTracer.traceRay(ray).getColor().getGreen(),rayTracer.traceRay(ray).getColor().getRed());
                if(m.distance(r)>10)
                    pixelColor =pixelColor.add(castRayHelp( Nx, Ny, iColumn, jRow,rayTracer.traceRay(ray))) ;
                else
                    pixelColor =pixelColor.add(rayTracer.traceRay(ray)) ;
            }
        }
        pixelColor=pixelColor.reduce(5);
        return pixelColor;
    }
    //2
//    private Color castRayHelp(int Nx, int Ny, int i, int j) {
//        int bigNy = 2*Ny;
//        int bigNx = 2*Nx;
//        Ray middleRay = constructRay(Nx, Ny, j, i);
//        Color pixelColor=new Color(java.awt.Color.BLACK);
//        pixelColor =pixelColor.add(rayTracer.traceRay(middleRay)) ;
//        Point m=new Point(rayTracer.traceRay(middleRay).getColor().getBlue(),rayTracer.traceRay(middleRay).getColor().getGreen(),rayTracer.traceRay(middleRay).getColor().getRed());
//
//        for (int iColumn = i*2; iColumn < i*2+2; iColumn++) {
//            for (int jRow = j*2; jRow < j*2+2; jRow++) {
//                Ray ray = constructRay(bigNx, bigNy, jRow, iColumn);
//                Point r=new Point(rayTracer.traceRay(ray).getColor().getBlue(),rayTracer.traceRay(ray).getColor().getGreen(),rayTracer.traceRay(ray).getColor().getRed());
//                if(m.distance(r)>10)
//                    pixelColor =pixelColor.add(castRayHelp( Nx, Ny, iColumn, jRow)) ;
//                else
//                    pixelColor =pixelColor.add(rayTracer.traceRay(ray)) ;
//            }
//        }
//        pixelColor=pixelColor.reduce(5);
//        return pixelColor;
//    }
    //רגיל
//    private void castRay(int Nx, int Ny, int i, int j) {
//        Ray ray = constructRay(Nx, Ny, j, i);
//        Color pixelColor = rayTracer.traceRay(ray);
//        imageWriter.writePixel(j, i, pixelColor);
//    }

    public Camera writeToImage() {
        imageWriter.writeToImage();
        return  this;
    }

    public void printGrid(int interval, Color color) {
//        if (color == null)
//            throw new UnsupportedOperationException();
        imageWriter.printGrid(interval, color);
    }

}
