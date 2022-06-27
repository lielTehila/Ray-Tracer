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
    /**
     * Height of the grid for picture improvements
     */
    private int _N=8;

    /**
     * Width of the grid for picture improvements
     */
    private int _M=8;

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
        int Nx = imageWriter.getNx();
        int Ny = imageWriter.getNy();
        Pixel.initialize(Ny, Nx, printInterval);
        IntStream.range(0, Ny).parallel().forEach(i -> {
            IntStream.range(0, Nx).parallel().forEach(j -> {
                castRay(Nx, Ny, j, i);
                Pixel.pixelDone();
                Pixel.printPixel();
            });
        });


        imageWriter.writeToImage();
        //renderImageWithAntialiasing();//2 שיפורים עם שיפור זמן ריצה
        return this;
    }
    //רגיל בלי שינויים
//    public Camera renderImage() {
//        int Nx = imageWriter.getNx();
//        int Ny = imageWriter.getNy();
//        for (int i = 0; i < Ny; i++) {
//            for (int j = 0; j < Nx; j++) {
//                 castRay(Nx, Ny, i, j);
//            }
//        }
//
//        imageWriter.writeToImage();
//        //renderImageWithAntialiasing();//2 שיפורים עם שיפור זמן ריצה
//        return this;
//    }



    /***
         * create ray from camera to specific pixel
         * @param Nx - Pixel size in a row
         * @param Ny -Pixel size in a column
         * @param i -The number of pixels to move in a column
         * @param j-The number of pixels to move in a row
         */
    //שיפור  anti aliasing// בלי שיפור זמן ריצה מתאים לתרגיל 8
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
    //   שיפור ריצה של הזמן
//    private void castRay(int Nx, int Ny, int i, int j) {
//        //improving of anti-aliasing
//        int bigNy = 2*Ny;
//        int bigNx = 2*Nx;
//        Ray middleRay = constructRay(Nx, Ny, j, i);
//        Color pixelColor=new Color(java.awt.Color.BLACK);
//        pixelColor =pixelColor.add(rayTracer.traceRay(middleRay)) ;
//        java.awt.Color c1=rayTracer.traceRay(middleRay).getColor();
//        for (int iColumn = i*2; iColumn < i*2+2; iColumn++) {
//            for (int jRow = j*2; jRow < j*2+2; jRow++) {
//                Ray ray = constructRay(bigNx, bigNy, jRow, iColumn);
//                java.awt.Color c2=rayTracer.traceRay(ray).getColor();
//                if(Math.abs(c1.getBlue()-c2.getBlue())>5&&Math.abs(c1.getGreen()-c2.getGreen())>5&&Math.abs(c1.getRed()-c2.getRed())>5)
//                    pixelColor =pixelColor.add(castRayHelp( Nx, Ny, iColumn, jRow,rayTracer.traceRay(ray)));
//                else
//                    pixelColor =pixelColor.add(rayTracer.traceRay(ray)) ;
//            }
//        }
//        pixelColor=pixelColor.reduce(5);
//        imageWriter.writePixel(j, i, pixelColor);
//    }
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

    //בלי שיפורים של תצוגה וריצה
    private void castRay(int Nx, int Ny, int i, int j) {
        Ray ray = constructRay(Nx, Ny, j, i);
        Color pixelColor = rayTracer.traceRay(ray);
        imageWriter.writePixel(j, i, pixelColor);
    }

    public Camera writeToImage() {
        imageWriter.writeToImage();
        return  this;
    }

    public void printGrid(int interval, Color color) {
//        if (color == null)
//            throw new UnsupportedOperationException();
        imageWriter.printGrid(interval, color);
    }

    //דברים מהגיט


    /**
     * Construct ray through nthe center of a pixel when we have only the bottom right corner's ray
     * @param ray the ray
     * @param nX number of pixel in width
     * @param nY number of pixels in height
     * @return center's ray
     */
    public Ray constructPixelCenterRay(Ray ray, double nX, double nY) {

        //Ry = h / nY - pixel height ratio
        double height = alignZero(this.height / nY);
        //Rx = h / nX - pixel width ratio
        double width = alignZero(this.width / nX);


        Point point = getPointOnViewPlane(ray);
        point = point.add(vRight.scale(width / 2)).add(vUp.scale(-height / 2));
        return new Ray(place, point.subtract(place));
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

    /**
     * Function that gets a pixel's center ray and constructs a list of 5 rays from that ray,
     * one at each corner of the pixel and one in the center
     * @param myRay the center's ray
     * @param nX number of pixel in width
     * @param nY number of pixels in height
     * @return list of rays
     */
    public List<Ray> construct5RaysFromRay(Ray myRay, double nX, double nY) {

        List<Ray> myRays = new LinkedList<>();

        //Ry = h / nY - pixel height ratio
        double rY = alignZero(height / nY);
        //Rx = h / nX - pixel width ratio
        double rX = alignZero(width / nX);


        Point center = getPointOnViewPlane(myRay);

        //[-1/2, -1/2]
        myRays.add( new Ray(place, center.add(vRight.scale(-rX / 2)).add(vUp.scale(rY / 2)).subtract(place)));
        //[1/2, -1/2]
        myRays.add( new Ray(place, center.add(vRight.scale(rX / 2)).add(vUp.scale(rY / 2)).subtract(place)));

        myRays.add(myRay);
        //[-1/2, 1/2]
        myRays.add( new Ray(place, center.add(vRight.scale(-rX / 2)).add(vUp.scale(-rY / 2)).subtract(place)));
        //[1/2, 1/2]
        myRays.add( new Ray(place, center.add(vRight.scale(rX / 2)).add(vUp.scale(-rY / 2)).subtract(place)));
        return myRays;
    }

    /**
     * Render every pixel by calling recursive adaptive render function
     * @param nX nb of pixels in col
     * @param nY nb of pixels in row
     * @param depth depth of recursion
     * @param firstRay first ray traced in center of the pixel
     * @param //isSoftShadows is rendering with soft shadows improvement
     * @return the color of the pixel
     */
    private Color renderPixel(double nX, double nY, int depth, Ray firstRay) {
        List<Ray> myRays = construct5RaysFromRay(firstRay, nX, nY); //construct 5 rays into the pixel
        HashMap<Integer, ColoredRay> rays = new HashMap<>();
        int i = 0;
        //trace all the rays and store the colors with the rays
        for (Ray myRay : myRays) {
            rays.put(++i, new ColoredRay(myRay, rayTracer.traceRay(myRay)));
        }
        //render the pixel in recursive function
        return renderPixelRecursive(rays, nX, nY, depth);
    }

    /**
     * Function that returns a list of 4 rays in a pixel
     * one on the top upper the center point
     * one on the left of the center
     * one on the right of the center
     * one on the bottom of the center
     * @param ray the center's ray
     * @param nX number of pixel in width
     * @param nY number of pixels in height
     * @return list of rays
     */
    public List<Ray> construct4RaysThroughPixel(Ray ray, double nX, double nY) {

        //Ry = h / nY - pixel height ratio
        double height = alignZero(this.height / nY);
        //Rx = h / nX - pixel width ratio
        double width = alignZero(this.width / nX);

        List<Ray> myRays = new ArrayList<>();
        Point center = getPointOnViewPlane(ray);


        Point point1 = center.add(vUp.scale(height / 2));
        Point point2 = center.add(vRight.scale(-width / 2));
        Point point3 = center.add(vRight.scale(width / 2));
        Point point4 = center.add(vUp.scale(-height / 2));
        myRays.add(new Ray(place, point1.subtract(place)));
        myRays.add(new Ray(place, point2.subtract(place)));
        myRays.add(new Ray(place, point3.subtract(place)));
        myRays.add(new Ray(place, point4.subtract(place)));
        return myRays;
    }


    /**
     * Render the pixel by doing recursion until the color is the same in all the 5 rays or end of depth
     * @param myRays the rays
     * @param nX nb of pixels in col
     * @param nY nb of pixels in row
     * @param depth recursion depth
     * @param //isSoftShadows is rendering with soft shadows improvement
     * @return the color of the pixel
     */
    private Color renderPixelRecursive(HashMap<Integer, ColoredRay> myRays, double nX, double nY, int depth) {
        boolean flag = false;
        //get the center of the pixel ray
        ColoredRay mainRay = myRays.get(3);
        //get center's color
        Color mainColor = mainRay.getColor();
        if (depth >= 1) {
            //get color of all the 4 different points
            //if one differs than center need to send the pixel to compute color in recursion
            for (Integer integer : myRays.keySet()) {
                if (integer != 3) {
                    ColoredRay tmpRay = myRays.get(integer);
                    Color tmpColor = tmpRay.getColor();
                    if (tmpRay.getColor() == null) {
                        tmpColor = rayTracer.traceRay(tmpRay.getRay());
                        myRays.put(integer, new ColoredRay(tmpRay.getRay(), tmpColor));
                    }
                    if (!tmpColor.equals(mainColor)) {
                        flag = true;
                    }
                }
            }
            if (flag) {
                //Create a map of Colored rays for the 4 under pixels and send to recursion
                List<ColoredRay> newRays = construct4RaysThroughPixel(myRays.get(3).getRay(), nX, nY).stream().map(
                        x -> new ColoredRay(x, rayTracer.traceRay(x))
                ).collect(Collectors.toList());
                HashMap<Integer, ColoredRay> rays = new HashMap<>();
                rays.put(1, myRays.get(1));
                rays.put(2, newRays.get(0));
                Ray tempCenter = constructPixelCenterRay(myRays.get(1).getRay(), nX * 2, nY * 2);
                rays.put(3, new ColoredRay(tempCenter, rayTracer.traceRay(tempCenter)));
                rays.put(4, newRays.get(1));
                rays.put(5, myRays.get(3));
                mainColor = mainColor.add(renderPixelRecursive(rays, nX * 2, nY * 2, depth - 1));
                rays = new HashMap<>();
                rays.put(1, newRays.get(0));
                rays.put(2, myRays.get(2));
                tempCenter = constructPixelCenterRay(newRays.get(0).getRay(), nX * 2, nY * 2);
                rays.put(3, new ColoredRay(tempCenter, rayTracer.traceRay(tempCenter)));
                rays.put(4, myRays.get(3));
                rays.put(5, newRays.get(2));
                mainColor = mainColor.add(renderPixelRecursive(rays, nX * 2, nY * 2, depth - 1));
                rays = new HashMap<>();
                rays.put(1, newRays.get(1));
                rays.put(2, myRays.get(3));
                tempCenter =constructPixelCenterRay(newRays.get(1).getRay(), nX * 2, nY * 2);
                rays.put(3, new ColoredRay(tempCenter, rayTracer.traceRay(tempCenter)));
                rays.put(4, myRays.get(4));
                rays.put(5, newRays.get(3));
                mainColor = mainColor.add(renderPixelRecursive(rays, nX * 2, nY * 2, depth - 1));
                rays = new HashMap<>();
                rays.put(1, myRays.get(3));
                rays.put(2, newRays.get(2));
                tempCenter = constructPixelCenterRay(myRays.get(3).getRay(), nX * 2, nY * 2);
                rays.put(3, new ColoredRay(tempCenter, rayTracer.traceRay(tempCenter)));
                rays.put(4, newRays.get(3));
                rays.put(5, myRays.get(5));
                mainColor = mainColor.add(renderPixelRecursive(rays, nX * 2, nY * 2, depth - 1));
                mainColor = mainColor.reduce(5);
            }
        }
        return mainColor;
    }
    public Ray constructRayThroughPixel(int nX, int nY, double j, double i) {

        //Pc = P0 + d * vTo
        Point pc = place.add(vTo.scale(distance));
        Point pIJ = pc;

        //Ry = height / nY : height of a pixel
        double rY = alignZero(height / nY);
        //Ry = weight / nX : width of a pixel
        double rX = alignZero(width / nX);
        //xJ is the value of width we need to move from center to get to the point
        double xJ = alignZero((j - ((nX - 1) / 2d)) * rX);
        //yI is the value of height we need to move from center to get to the point
        double yI = alignZero(-(i - ((nY - 1) / 2d)) * rY);

        if (xJ != 0) {
            pIJ = pIJ.add(vRight.scale(xJ)); // move to the point
        }
        if (yI != 0) {
            pIJ = pIJ.add(vUp.scale(yI)); // move to the point
        }

        //get vector from camera p0 to the point
        Vector vIJ = pIJ.subtract(place);

        //return ray to the center of the pixel
        return new Ray(place, vIJ);

    }
    /**
     * This function get a ray launched in the center of a pixel and launch a beam n * m others rays
     * on the same pixel
     *
     * @param nX  number of pixels in a row of view plane
     * @param nY  number of pixels in a column of view plane
     * @param n   number of the rays to launch in pixel
     * @param m   number of the ray to launch in the pixel
     * @param ray the ray that it is already launched in the center of the pixel
     * @return list of rays when every ray is launched inside a pixel with random emplacement
     */
    public List<Ray> constructRaysGridFromRay(int nX, int nY, int n, int m, Ray ray) {

        Point p0 = ray.getPoint(distance); //center of the pixel
        List<Ray> myRays = new LinkedList<>(); //to save all the rays

        double pixelHeight = alignZero(height / nY);
        double pixelHWidth = alignZero(width / nX);

        //We call the function constructRayThroughPixel like we used to but this time we launch m * n ray in the same pixel

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                myRays.add(constructRayThroughPixel(m, n, j, i, pixelHeight, pixelHWidth, p0));
            }
        }

        return myRays;
    }
    /**
     * The function constructs a ray from Camera location through a point (i,j) on the grid of a
     * pixel in the view plane
     *
     * @param m      grid's height
     * @param n      grid's width
     * @param j      number of the pixel in the row
     * @param i      number of the pixel in the column
     * @param pixelH height of the pixel
     * @param pixelW width of the pixel
     * @param pc     pixel center
     * @return the ray through pixel's center
     */
    private Ray constructRayThroughPixel(int m, int n, double j, double i, double pixelH, double pixelW, Point pc) {

        Point pIJ = pc;

        //Ry = height / nY : height of a pixel
        double rY = pixelH / n;
        //Ry = weight / nX : width of a pixel
        double rX = pixelW / m;
        //xJ is the value of width we need to move from center to get to the point
        //we get to the bottom/top of the pixel and then we move randomly in the pixel to get the point
        double xJ = ((j + r.nextDouble() / (r.nextBoolean() ? 2 : -2)) - ((m - 1) / 2d)) * rX;
        //yI is the value of height we need to move from center to get to the point
        //we get to the side of the pixel and then we move randomly in the pixel to get the point
        double yI = -((i + r.nextDouble() / (r.nextBoolean() ? 2 : -2)) - ((n - 1) / 2d)) * rY;

        if (xJ != 0) {
            pIJ = pIJ.add(vRight.scale(xJ));
        }
        if (yI != 0) {
            pIJ = pIJ.add(vUp.scale(yI));
        }

        //get vector from camera p0 to the point
        Vector vIJ = pIJ.subtract(place);

        //return ray to the center of the pixel
        return new Ray(place, vIJ);

    }
    //פונקציה ראשית
    private void renderImageWithAntialiasing() {
        if (_N == 0 || _M == 0)
            throw new MissingResourceException("You need to set the n*m value for the rays launching", RayTracerBasic.class.getName(), "");

        for (int i = 0; i < imageWriter.getNy(); i++) {
            for (int j = 0; j < imageWriter.getNx(); j++) {
                Ray myRay = constructRayThroughPixel(imageWriter.getNx(), imageWriter.getNy(), j, i);
                List<Ray> myRays = constructRaysGridFromRay(imageWriter.getNx(), imageWriter.getNy(), _N, _M, myRay);
                Color myColor = new Color(0, 0, 0);
                for (Ray ray : myRays) {
                    myColor = myColor.add(rayTracer.traceRay(ray));
                }
                imageWriter.writePixel(j, i, myColor.reduce(_N * _M));
            }
        }
    }

}

