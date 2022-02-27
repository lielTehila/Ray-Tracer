package primitives;

import java.util.Objects;

public class Ray {
    final private Point p0;
    final private Vector dir;

    /***
     * constractor with params
     * @param  p-Point
     * @param d-Vector
     */
    public Ray( Point p, Vector d)
    {
        p0=new Point(p.get_xyz());
        dir=new Vector(d.get_xyz()).normalize() ;
    }

    /***
     * print the elements
     * @return string
     */
    @Override
    public String toString() {
        return "Ray{" +
                "p0=" + p0 +
                ", dir=" + dir +
                '}';
    }

    /***
     *
     * @return p0
     */
    public Point getP0() {
        return p0;
    }
    /***
     *
     * @return dir
     */
    public Vector getDir() {
        return dir;
    }

    /***
     *
     * @param o-object to compare
     * @return true if they are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ray ray = (Ray) o;
        return Objects.equals(p0, ray.p0) && Objects.equals(dir, ray.dir);
    }

    /*
    @Override
    public int hashCode() {
        return Objects.hash(p0, dir);
    }
     */
}
