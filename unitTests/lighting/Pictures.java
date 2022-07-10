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

//x is right, y is up, z is to
public class Pictures {

    @Test
    public void projectPicture() {
        Scene scene = new Scene.SceneBuilder("Test scene") //
                .setAmbientLight(new AmbientLight(new Color(BLACK), new Double3(0.7)))
                //.setBackground(new Color(184,255,154))
                .setBackground(new Color(171, 255, 255))
                .build();
        //regular
        Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setVPSize(200, 200) //
                .setVPDistance(800);
        //look from side
//        Camera camera = new Camera(new Point(-70, 30, 1200), new Vector(1, 0, -12), new Vector(0, 1, 0)) //
//                .setVPSize(200, 200) //
//                .setVPDistance(800);
        //close to the mirror
//        Camera camera = new Camera(new Point(0, 0, 600), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
//                .setVPSize(200, 200) //
//                .setVPDistance(800);

        //the walls
        Geometry floor = new Plane(new Point(0, -50, 0), new Vector(0, 1, 0)).setMaterial(new Material().setkD(1))
                .setEmission(new Color(255, 195, 96));

//        Geometry rightWall = new Plane(new Point(90, 0, 0), new Vector(1, 0, 0)).setMaterial(new Material().setkD(0.6))
//                .setEmission(new Color(RED).reduce(1.2));
        Geometry rightWallTri1 = new Triangle(new Point(90,-50,-500),new Point(90,100,-500),new Point(90,-50,1300))
                .setEmission(new Color(RED).reduce(1.2));
        Geometry rightWallTri2 = new Triangle(new Point(90,100,1300),new Point(90,100,-500),new Point(90,-50,1300))
                .setEmission(new Color(RED).reduce(1.2));

//        Geometry frontMirrorWall = new Plane(new Point(0, 0, -500), new Vector(0, 0, 1)).setMaterial(new Material().setKr(0.8).setkD(0.1))
//                .setEmission(new Color(BLACK));
        Geometry frontMirrorWallTri1 = new Triangle(new Point(-90,-50,-500),new Point(-90,100,-500),new Point(90,-50,-500))
                .setMaterial(new Material().setKr(0.8).setkD(0.1)).setEmission(new Color(BLACK));
        Geometry frontMirrorWallTri2 = new Triangle(new Point(90,100,-500),new Point(-90,100,-500),new Point(90,-50,-500))
                .setMaterial(new Material().setKr(0.8).setkD(0.1)).setEmission(new Color(BLACK));

//        Geometry ceil = new Plane(new Point(0, 100, 0), new Vector(0, 1, 0)).setMaterial(new Material().setkD(1))
//                .setEmission(new Color(255, 195, 96));
        Geometry ceilTri1 = new Triangle(new Point(-90, 100, -500), new Point(90, 100, -500), new Point(-90, 100, 1300)).setMaterial(new Material().setkD(1))
                .setEmission(new Color(255, 195, 96));
        Geometry ceilTri2 = new Triangle(new Point(90, 100, 1300), new Point(90, 100, -501), new Point(-91, 100, 1300)).setMaterial(new Material().setkD(1))
                .setEmission(new Color(255, 195, 96));

//        Geometry besideWall = new Plane(new Point(0, 0, 1300), new Vector(0, 0, 1)).setMaterial(new Material().setkD(1))
//                .setEmission(new Color(255, 195, 96));
        Geometry besideWallTri1 = new Triangle(new Point(-90, 100, 1300), new Point(-90, -50, 1300), new Point(90, 100, 1300)).setMaterial(new Material().setkD(1))
                .setEmission(new Color(255, 195, 96));
        Geometry besideWallTri2 = new Triangle(new Point(90, -50, 1300), new Point(-90, -50, 1300), new Point(90, 100, 1300)).setMaterial(new Material().setkD(1))
                .setEmission(new Color(255, 195, 96));

//        Geometry leftWall = new Plane(new Point(-90, 0, 0), new Vector(1, 0, 0)).setMaterial(new Material().setkD(0.6))
//                .setEmission(new Color(RED).reduce(1.2));
        Geometry trWallR1 = new Triangle(new Point(-90, 100, -500), new Point(-90, -50, -500), new Point(-90, 100, 50)).setEmission(new Color(RED).reduce(1.2));
        Geometry trWallR2 = new Triangle(new Point(-90, 100, 50), new Point(-90, -50, 50), new Point(-90, -50, -500)).setEmission(new Color(RED).reduce(1.2));
        Geometry trWallL1 = new Triangle(new Point(-90, 100, 250), new Point(-90, -50, 250), new Point(-90, 100, 1300)).setEmission(new Color(RED).reduce(1.2));
        Geometry trWallL2 = new Triangle(new Point(-90, -50, 250), new Point(-90, 100, 1300), new Point(-90, -50, 1300)).setEmission(new Color(RED).reduce(1.2));
        Geometry trDoor1 = new Triangle(new Point(-90, 65, 50), new Point(-90, -50, 50), new Point(-200, 65, 50)).setEmission(new Color(212, 207, 210).reduce(1.2)).setMaterial(new Material().setkD(0.6));
        Geometry trDoor2 = new Triangle(new Point(-200, 65, 50), new Point(-200, -50, 50), new Point(-90, -50, 50)).setEmission(new Color(212, 207, 210).reduce(1.2)).setMaterial(new Material().setkD(0.6));
        Geometry trUpDoor1 = new Triangle(new Point(-90, 65, 50), new Point(-90, 100, 50), new Point(-90, 100, 250)).setEmission(new Color(RED).reduce(1.2));
        Geometry trUpDoor2 = new Triangle(new Point(-90, 65, 50), new Point(-90, 65, 250), new Point(-90, 100, 250)).setEmission(new Color(RED).reduce(1.2));

        //balls
        Geometry greenSphere = new Sphere(new Point(-50, -35, -200), 15d)
                .setEmission(new Color(75, 255, 40))
                .setMaterial(new Material().setkD(1));
        Geometry blackSphere = new Sphere(new Point(-65, -40, -110), 10d)
                .setEmission(new Color(BLACK))
                .setMaterial(new Material().setkD(0.7).setKr(0.3).setkS(1));
        Geometry pinkSphere = new Sphere(new Point(-50, -43, -10), 7d)
                .setEmission(new Color(224, 66, 255))
                .setMaterial(new Material().setkD(1));
        Geometry orangeSphere = new Sphere(new Point(-25, -38, 100), 12d)
                .setEmission(new Color(255, 154, 22))
                .setMaterial(new Material().setkD(0.8));
        Geometry brownSphere = new Sphere(new Point(-5, -40, 160), 10d)
                .setEmission(new Color(new java.awt.Color(207, 154, 72)).reduce(2))
                .setMaterial(new Material().setkS(1).setkD(0.7).setKr(0.3));
        Geometry purpleSphere = new Sphere(new Point(20, -42, 230), 8d)
                .setEmission(new Color(197, 92, 232))
                .setMaterial(new Material().setkD(1));
        Geometry greenSphere2 = new Sphere(new Point(40, -38, 300), 12d)
                .setEmission(new Color(0, 169, 110))
                .setMaterial(new Material().setkD(1));
        Geometry pinkSphere2 = new Sphere(new Point(55, -35, 400), 15d)
                .setEmission(new Color(255, 127, 154))
                .setMaterial(new Material().setkD(1));
        Geometry blackSphere2 = new Sphere(new Point(45, -40, 460), 10d)
                .setEmission(new Color(BLACK))
                .setMaterial(new Material().setkD(0.6).setKr(0.4).setkS(1));
        Geometry pinkSphere3 = new Sphere(new Point(28, -42, 530), 8d)
                .setEmission(new Color(255, 0, 121))
                .setMaterial(new Material().setkD(0.2).setKt(0.5));
        Geometry brownSphere2 = new Sphere(new Point(15, -45, 555), 5d)
                .setEmission(new Color(new java.awt.Color(177, 89, 0)).reduce(2))
                .setMaterial(new Material().setkS(1).setkD(1));
        Geometry orangeSphere3 = new Sphere(new Point(0, -42, 590), 8d)
                .setEmission(new Color(255, 154, 22))
                .setMaterial(new Material().setkD(1));
        Geometry lightBlueSphere = new Sphere(new Point(-17, -42, 620), 8d)
                .setEmission(new Color(62, 203, 198))
                .setMaterial(new Material().setkD(1));
        Geometry blueSphere = new Sphere(new Point(-40, -30, 650), 20d)
                .setEmission(new Color(BLUE).reduce(1.5))
                .setMaterial(new Material().setkD(0.2).setKt(0.6));

        //lamps
        Geometry Lamp1 = new Sphere(new Point(-92, 65, -200), 8d)
                .setEmission(new Color(249, 255, 195))
                .setMaterial(new Material().setKt(0.2).setkD(0.8));
        Geometry Lamp2 = new Sphere(new Point(-92, 65, 400), 8d)
                .setEmission(new Color(249, 255, 195))
                .setMaterial(new Material().setKt(0.2).setkD(0.8));
        Geometry Lamp3 = new Sphere(new Point(-92, 65, 1000), 8d)
                .setEmission(new Color(249, 255, 195))
                .setMaterial(new Material().setKt(0.2).setkD(0.8));
        Geometry Lamp4 = new Sphere(new Point(92, 65, -200), 8d)
                .setEmission(new Color(249, 255, 195))
                .setMaterial(new Material().setKt(0.2).setkD(0.8));
        Geometry Lamp5 = new Sphere(new Point(92, 65, 400), 8d)
                .setEmission(new Color(249, 255, 195))
                .setMaterial(new Material().setKt(0.2).setkD(0.8));
        Geometry Lamp6 = new Sphere(new Point(92, 65, 1000), 8d)
                .setEmission(new Color(249, 255, 195))
                .setMaterial(new Material().setKt(0.2).setkD(0.8));

        //adding the shapes to the scene
        scene.getGeometries().add(
                floor, ceilTri1, ceilTri2, besideWallTri1, besideWallTri2, trWallR1, trWallR2, trWallL1, trWallL2, trDoor1,
                trDoor2, trUpDoor1, trUpDoor2,rightWallTri1,rightWallTri2,frontMirrorWallTri1,frontMirrorWallTri2);
        scene.getGeometries().add(blueSphere, orangeSphere3, pinkSphere3, lightBlueSphere, brownSphere2,
                blackSphere2, pinkSphere, greenSphere, greenSphere2, pinkSphere2, purpleSphere,
                orangeSphere, blackSphere, brownSphere);
        scene.getGeometries().add( Lamp1, Lamp2, Lamp3, Lamp4, Lamp5, Lamp6);


        //adding the lights to the scene
  //      scene.getLights().add(new PointLight(new Color(WHITE), new Point(0, 50, 1200)).setKl(0.0001).setKq(0.0005));

        scene.getLights().add(new SpotLight(new Color(249, 255, 195), new Point(-88, 65, -200), new Vector(1, -1, 0)).setKl(4E-5).setKq(2E-7));
//        scene.getLights().add(new SpotLight(new Color(249, 255, 195), new Point(-88, 65, 400), new Vector(1, -1, 0)).setKl(4E-5).setKq(2E-7));
        scene.getLights().add(new SpotLight(new Color(249, 255, 195), new Point(-88, 65, 1000), new Vector(1, -1, 0)).setKl(4E-5).setKq(2E-7));
//        scene.getLights().add(new SpotLight(new Color(249, 255, 195), new Point(88, 65, -200), new Vector(-1, -1, 0)).setKl(4E-5).setKq(2E-7));
        scene.getLights().add(new SpotLight(new Color(249, 255, 195), new Point(88, 65, 400), new Vector(-1, -1, 0)).setKl(4E-5).setKq(2E-7));
//        scene.getLights().add(new SpotLight(new Color(249, 255, 195), new Point(88, 65, 1000), new Vector(-1, -1, 0)).setKl(4E-5).setKq(2E-7));

        scene.getLights().add(new DirectionalLight(new Color(YELLOW), new Vector(1, -1, -1)));


        //render scene
        ImageWriter imageWriter = new ImageWriter("OurProjectPictureRegular9", 500, 500);
        camera.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage(); //
    }

}
