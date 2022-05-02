package scene;

import geometries.Geometries;
import lighting.AmbientLight;
import lighting.LightSource;
import primitives.Color;

import java.util.LinkedList;
import java.util.List;


public class Scene {

    private final String name;
    private final Color background;
    private final AmbientLight ambientLight;
    private final Geometries geometries;
    List<LightSource> lights = new LinkedList<>();

    /***
     * @return the name of the scene
     */

    public String getName() {
        return name;
    }

    /***
     * @return the background color at the scene
     */

    public Color getBackGround() {
        return background;
    }

    /***
     * @return the ambient light in the scene
     */

    public AmbientLight getAmbientLight() {
        return ambientLight;
    }

    /***
     * @return all the geometry shapes
     */
    public Geometries getGeometries() {
        return geometries;
    }

   /***
     * @return all the lights sources
     */
    public List<LightSource> getLights() {
        return lights;
    }



    /***
     * constructor in builder pattern
     * @param builder- object builder with same params as the scene's params
     */
    public Scene(SceneBuilder builder) {
        name = builder.name;
        background = builder.background;
        ambientLight = builder.ambientLight;
        geometries = builder.geometries;
        lights = builder.lights;
    }

    public static class SceneBuilder {

        private final String name;
        private List<LightSource> lights = new LinkedList<>();
        private Color background = Color.BLACK;
        private AmbientLight ambientLight = new AmbientLight();
        private Geometries geometries = new Geometries();

        public SceneBuilder(String name) {
            this.name = name;
        }

        //chaining methods

        public SceneBuilder setBackground(Color background) {
            this.background = background;
            return this;
        }

        /***
         * set the lights database
         * @param lights - collection of thwe lights in the scene
         * @return the scene
         */
        public SceneBuilder setLights(List<LightSource> lights) {
            this.lights = lights;
            return this;
        }

        public SceneBuilder setAmbientLight(AmbientLight ambientLight) {
            this.ambientLight = ambientLight;
            return this;
        }

        public SceneBuilder setGeometries(Geometries geometries) {
            this.geometries = geometries;
            return this;
        }

        public Scene build() {
            Scene scene = new Scene(this);
            return scene;
        }
    }
}
