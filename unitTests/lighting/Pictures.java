package lighting;

import geometries.Geometry;
import geometries.Sphere;
import geometries.Triangle;
import lighting.AmbientLight;
import lighting.DirectionalLight;
import lighting.PointLight;
import lighting.SpotLight;
import org.junit.jupiter.api.Test;
import primitives.*;
import renderer.Camera;
import renderer.ImageWriter;
import renderer.RayTracerBasic;
import scene.Scene;

import static java.awt.Color.*;

public class Pictures {


    @Test
    public void firstPicture() {
        Scene scene = new Scene.SceneBuilder("Test scene") //
                .setAmbientLight(new AmbientLight(new Color(java.awt.Color.BLACK), new Double3(0.7)))
                .setBackground(new Color(98,216,255))
                .build();

        Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setVPSize(200, 200) //
                .setVPDistance(1000);

        Geometry sphere1 = new Sphere(new Point(0, -5, -50), 50d)
                .setEmission(new Color(ORANGE).reduce(2))
                .setMaterial(new Material().setkD(0.1).setkS(0.5).setShininess(200).setKt(0.3));
        Geometry sphere2 = new Sphere(new Point(25, -40, 25), 20d)
                .setEmission(new Color(75,255,250).reduce(2))
                .setMaterial(new Material().setkD(0.5).setkS(0.2).setShininess(200).setKt(0.4));
        Geometry triangle1 = new Triangle(new Point(0,0,-20), new Point(30,50,-20), new Point(-30,50,-20))
                .setEmission(new Color(80,255,211)).setMaterial(new Material().setkD(0.9).setkS(0.5).setShininess(500).setKr(0.3));
        Geometry triangle2 = new Triangle(new Point(0,-25,-40), new Point(30,25,-40), new Point(-30,25,-40))
                .setEmission(new Color(80,255,211)).setMaterial(new Material().setkD(0.5).setkS(0.9).setShininess(500).setKr(0.2));
        Geometry triangle3 = new Triangle(new Point(0,-50,-50), new Point(30,0,-50), new Point(-30,0,-50))
                .setEmission(new Color(80,255,211)).setMaterial(new Material().setkD(0.1).setkS(0.4).setShininess(500).setKr(0.4));


        scene.getGeometries().add(triangle1, triangle2, triangle3);
        scene.getLights().add( //
                new SpotLight(new Color(700, 400, 400), new Point(40, 40, 115), new Vector(-1, -1, -4)) //
                        .setKl(4E-4).setKq(2E-5));
        scene.getLights().add(new SpotLight(new Color(800, 500, 0),new Point(25, -40, 25), new Vector(6, 1, -0.5))
                .setKl(0.001)
                .setKq(0.0001));
        scene.getLights().add(new PointLight(new Color(40,240,255), new Point(-50,-50,300)).setKl(0.001).setKq(0.0002));
        scene.getLights().add(new DirectionalLight(new Color(221,11,255), new Vector(50, -7, -0.5)));


        ImageWriter imageWriter = new ImageWriter("OurFirstPicture4", 500, 500);
        camera.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage(); //
    }
}
