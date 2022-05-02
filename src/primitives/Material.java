package primitives;

public class Material {
    private Double3 kD = Double3.ZERO;
    private Double3 kS = Double3.ZERO;
    private int nShininess = 0;

    public Material setkD(double kD) {
        this.kD = new Double3(kD);
        return this;
    }

    public Material setKd(Double3 kd) {
        this.kD = kd;
        return this;
    }

    public Double3 getkD() {
        return kD;
    }

    public Material setkS(double kS) {
        this.kS = new Double3(kS);
        return this;
    }

    public Material setKs(Double3 ks) {
        this.kS = ks;
        return this;
    }

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
