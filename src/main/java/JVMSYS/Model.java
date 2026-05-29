package JVMSYS;

public class Model {
    int npoints;
    float[] points;
    float lx, ly, lz;
    float lrx, lry, lrz;

    public void setRootPoints(float[] points) {
        this.points = points;
    }

    public void setRootPos(float x, float y, float z) {
        this.lx = x;
        this.ly = y;
        this.lz = z;
    }

    public void setRootOrientation(float x, float y, float z) {
        this.lrx = x;
        this.lry = y;
        this.lrz = z;
    }
}
