package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

/**
 * abstract class Ray Tracer
 */
public abstract class RayTracer {

    /**
     * the scene of the picture.
     */
    protected Scene scene;

    /**
     * constructor with params.
     * @param scene return this.
     */
    public RayTracer(Scene scene) {
        this.scene = scene;
    }

    /***
     * return the color of the point that the ray arrive to
     * @param ray the ray that we send
     * @return the color of the point
     */
    abstract public Color traceRay(Ray ray);

}
