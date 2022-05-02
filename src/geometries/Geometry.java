package geometries;

import primitives.Color;
import primitives.Material;
import primitives.Point;
import primitives.Vector;

/***
 * interface for all graphic 3D objects that are
 * positioned in our 3D
 */
abstract public class  Geometry extends Intersectable {

    protected Color emission=Color.BLACK;
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

    public Geometry setMaterial(Material material) {
        this.material = material;
        return this;
    }

    public Material getMaterial() {
        return material;
    }
}