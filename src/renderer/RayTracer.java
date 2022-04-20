package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

/**
 * abstract class Ray Tracer
 */
public abstract class RayTracer {

    protected Scene scene;

    public RayTracer(Scene scene) {
        this.scene = scene;
    }

    /**
     * the fanction get a ray and reyurn the color of the ray attach
     * @param ray
     * @return
     */
    abstract public Color traceRay(Ray ray);

}
