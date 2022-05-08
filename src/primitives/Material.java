package primitives;

/***
 * contain the attenuation and shininess factors
 */
public class Material {
    private Double3 kD = Double3.ZERO;
    private Double3 kS = Double3.ZERO;
    private int nShininess = 0;

    /***
     * set the factor d
     * @param kD object double
     * @return this
     */
    public Material setkD(double kD) {
        this.kD = new Double3(kD);
        return this;
    }
    /***
     * set the factor d
     * @param kd object Double3
     * @return this
     */
    public Material setkD(Double3 kd) {
        this.kD = kd;
        return this;
    }
    /***
     * get the factor d
     * @return d
     */
    public Double3 getkD() {
        return kD;
    }
    /***
     * set the factor d
     * @param kS object double
     * @return this
     */
    public Material setkS(double kS) {
        this.kS = new Double3(kS);
        return this;
    }
    /***
     * set the factor d
     * @param ks object Double3
     * @return this
     */
    public Material setkS(Double3 ks) {
        this.kS = ks;
        return this;
    }

    /***
     * get the factor s
     * @return s
     */
    public Double3 getkS() {
        return kS;
    }

    public Material setShininess(int shininess) {
        this.nShininess = shininess;
        return this;
    }

    public int getShininess() {
        return nShininess;
    }
}
