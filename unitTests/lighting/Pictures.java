package lighting;

import geometries.Geometry;
import geometries.Plane;
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
    public void projectPicture() {
        Scene scene = new Scene.SceneBuilder("Test scene") //
                .setAmbientLight(new AmbientLight(new Color(java.awt.Color.BLACK), new Double3(0.7)))
                //.setBackground(new Color(184,255,154))
                .build();

        Camera camera = new Camera(new Point(0, 0, 1300), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setVPSize(200, 200) //
                .setVPDistance(1000);

//        Geometry triangle1 = new Triangle(new Point(0,0,-20), new Point(30,50,-20), new Point(-30,50,-20))
//                .setEmission(new Color(80,255,211)).setMaterial(new Material().setkD(0.2).setkS(0.5).setShininess(500).setKr(0.3));
//        Geometry triangle2 = new Triangle(new Point(0,-25,-40), new Point(30,25,-40), new Point(-30,25,-40))
//                .setEmission(new Color(80,255,211)).setMaterial(new Material().setkD(0.2).setkS(0.9).setShininess(500).setKr(0.2));
//        Geometry triangle3 = new Triangle(new Point(0,-50,-50), new Point(30,0,-50), new Point(-30,0,-50))
//                .setEmission(new Color(80,255,211)).setMaterial(new Material().setkD(0.2).setkS(0.4).setShininess(500).setKr(0.4));
        Geometry floor = new Plane(new Point(0,-50,0),new Vector(0,1,0)).setMaterial(new Material().setkD(1))
                .setEmission(new Color(255,195,96));
        Geometry ceil = new Plane(new Point(0,100,0),new Vector(0,1,0)).setMaterial(new Material().setkD(1))
                .setEmission(new Color(255,195,96));
        Geometry frontWall = new Plane(new Point(0,0,-500),new Vector(0,0,1)).setMaterial(new Material().setKr(0.8).setkD(0.1))
                .setEmission(new Color(BLACK));
        Geometry besideWall = new Plane(new Point(0,0,1300),new Vector(0,0,1)).setMaterial(new Material().setkD(1))
                .setEmission(new Color(255,195,96));
        Geometry rightWall = new Plane(new Point(90,0,0),new Vector(1,0,0)).setMaterial(new Material().setkD(1))
                .setEmission(new Color(RED));
        Geometry leftWall = new Plane(new Point(-90,0,0),new Vector(1,0,0)).setMaterial(new Material().setkD(1))
                .setEmission(new Color(RED));

        Geometry sphere1 = new Sphere(new Point(-50, -30, -200), 20d)
                .setEmission(new Color(RED).reduce(2))
                .setMaterial(new Material().setkD(1));
        Geometry sphere2 = new Sphere(new Point(25, -20, 25), 30d)
                .setEmission(new Color(RED).reduce(2))
                .setMaterial(new Material().setkD(1));
        Geometry sphere3 = new Sphere(new Point(0,100,0), 20d)
                .setEmission(new Color(PINK).reduce(2)) //blue
                .setMaterial(new Material().setkD(1));

        scene.getGeometries().add(floor,ceil,frontWall,besideWall,rightWall,leftWall,sphere1,sphere2,sphere3);
//        scene.getLights().add( //
//                new SpotLight(new Color(BLUE), new Point(0, 99, -205), new Vector(0, 1, 0)) //
//                        .setKl(4E-4).setKq(2E-5));
        scene.getLights().add(new SpotLight(new Color(GREEN),new Point(0, 80, 300), new Vector(0, -1, -5))
               .setKl(0.0001)
               .setKq(0.0001));
        //scene.getLights().add(new PointLight(new Color(GREEN), new Point(0,90,50)).setKl(0.0001).setKq(0.0005));
        //scene.getLights().add(new PointLight(new Color(RED), new Point(0,90,1100)).setKl(0.0001).setKq(0.00002));
//        scene.getLights().add(new DirectionalLight(new Color(221,11,255), new Vector(50, -7, -0.5)));


        ImageWriter imageWriter = new ImageWriter("OurProjectPicture", 500, 500);
        camera.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage(); //
    }


    @Test
    public void firstPicture() {
        Scene scene = new Scene.SceneBuilder("Test scene") //
                .setAmbientLight(new AmbientLight(new Color(java.awt.Color.BLACK), new Double3(0.7)))
                .setBackground(new Color(184,255,154))
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
                .setEmission(new Color(80,255,211)).setMaterial(new Material().setkD(0.2).setkS(0.5).setShininess(500).setKr(0.3));
        Geometry triangle2 = new Triangle(new Point(0,-25,-40), new Point(30,25,-40), new Point(-30,25,-40))
                .setEmission(new Color(80,255,211)).setMaterial(new Material().setkD(0.2).setkS(0.9).setShininess(500).setKr(0.2));
        Geometry triangle3 = new Triangle(new Point(0,-50,-50), new Point(30,0,-50), new Point(-30,0,-50))
                .setEmission(new Color(80,255,211)).setMaterial(new Material().setkD(0.2).setkS(0.4).setShininess(500).setKr(0.4));
        Geometry plane = new Plane(new Point(0,-70,0),new Vector(0,1,0)).setMaterial(new Material().setkD(1))
                        .setEmission(new Color(188,255,87));
        scene.getGeometries().add(triangle1, triangle2, triangle3,plane);
        scene.getLights().add( //
                new SpotLight(new Color(YELLOW), new Point(40, -30, 115), new Vector(-1, -1, -2)) //
                        .setKl(4E-4).setKq(2E-5));
        scene.getLights().add(new SpotLight(new Color(221, 211, 255),new Point(80, -40, 500), new Vector(-3, 1, -4))
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
