package renderer;

import scene.Scene;

/**
 * abstract class Ray Tracer
 */
public abstract class RayTracer {

    private final Scene scene;

    public RayTracer(Scene scene){
        this.scene = scene;
    }
}
