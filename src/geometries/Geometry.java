package geometries;

import primitives.Color;
import primitives.Material;
import primitives.Point;
import primitives.Vector;

/***
 * interface for all graphic 3D objects that are
 * positioned in our 3D
 */
abstract public class Geometry extends Intersectable {

    /**
     *The color of the Geometry is initialized in black
     */
    protected Color emission = Color.BLACK;
    /**
     *The properties of the Geometry
     */
    private Material material = new Material();

    /***
     * return the emission light of the geometry
     * @return emission
     */
    public Color getEmission() {
        return emission;
    }

    /***
     *
     * @param emission get the value of the emission light
     * @return the geometry
     */
    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }

    /***
     * normal vector from a specific Point {@link Point}
     * @param point point outside the grophic shape
     * @return rornal vector {@link Vector}
     */
    abstract public Vector getNormal(Point point);

    /***
     * set the material of the object
     * @param material of this geometry
     * @return this
     */
    public Geometry setMaterial(Material material) {
        this.material = material;
        return this;
    }

    /***
     * get the material of the geometry
     * @return the geomery material
     */
    public Material getMaterial() {
        return material;
    }
}