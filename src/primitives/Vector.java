package primitives;

/**
 *The department represents a Vector.
 */
public class Vector extends Point {

    /**
     * Constructor with parameters.
     * @param x value.
     * @param y value.
     * @param z value.
     */
    public Vector(double x, double y, double z) {
        this(new Double3(x, y, z));
    }

    /**
     * primary  constructor for Vector class
     * @param xyz head of vector starting from origin Point(0.0.0)
     */
    public Vector(Double3 xyz) {
        super(xyz);
        if (_xyz.equals(Double3.ZERO)) {
            throw new IllegalArgumentException("Vector(0,0,0) is not allowed");
        }
    }

    /***
     * adding vectors by using Point func add
     * @param v-vector to add
     * @return new vector that created by adding two vectors
     */
    public Vector add(Vector v)
    {
        Point p= super.add(v);
        return new Vector(p.get_xyz());
    }

    /**
     * @return The length is squared.
     */
    public double lengthSquared() {
        return _xyz.d1 * _xyz.d1
                + _xyz.d2 * _xyz.d2
                + _xyz.d3 * _xyz.d3;
    }

    /**
     * @return The length.
     */
    public double length() {
        return Math.sqrt(lengthSquared());
    }

    /**
     * The function calculates a scleral fold.
     * @param rhs scleral
     * @return The  calculates a scleral fold.
     */
    public Vector scale(double rhs)
    {
        return new Vector(this.get_xyz().scale(rhs));
    }
    /**
     * dot product between two vectors (scalar product)
     * machpela skalarit
     * @param other the right vector of U.V
     * @return scalar value of the dot product
     * @link https://www.mathsisfun.com/algebra/vectors-dot-product.html
     */
    public double dotProduct(Vector other) {
        return _xyz.d1 * other._xyz.d1
                + _xyz.d2 * other._xyz.d2
                + _xyz.d3 * other._xyz.d3;
    }

    /**
     * cross product between two vectors (vectorial product)
     * @param other other the right vector of U.V
     * @return the vector resulting from the cross product (Right-hand rule)
     * @link https://www.mathsisfun.com/algebra/vectors-cross-product.html
     */
    public Vector crossProduct(Vector other) {
        double ax = _xyz.d1;
        double ay = _xyz.d2;
        double az = _xyz.d3;

        double bx = other._xyz.d1;
        double by = other._xyz.d2;
        double bz = other._xyz.d3;

        double cx = ay * bz - az * by;
        double cy = az * bx - ax * bz;
        double cz = ax * by - ay * bx;


        return new Vector(cx, cy, cz);
    }

    /**
     * The function normalizes the vector.
     * @return
     */
    public Vector normalize() {
        double len = length();
        return new Vector(_xyz.reduce(len));
    }

    @Override
    public Double3 get_xyz() {
        return super.get_xyz();
    }
}