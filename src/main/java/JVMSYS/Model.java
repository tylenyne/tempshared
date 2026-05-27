package JVMSYS;

public class Model {
    int points;
    float[] array;
    float lx, ly, lz;
    float lrx, lry, lrz;

    public void init(float[] points) {
        assert points.length % 3 == 0;
        this.points = points.length / 3;
        array = points.clone();
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
