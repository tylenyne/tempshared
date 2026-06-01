package JVMSYS;

public class Model {
    int npoints;
    float[] points;
    public float[] xyz;
    public float[] r_xyz_old;
    public float[] r_xyz_new;
    public float[] s_xyz;
    public float delta_t, t;

    public void setRootVel(float delta_t, float dx, float dy, float dz) {

    }

    public void setRootTorque(float delta_t, float theta, float x, float y, float z) {

    }

    public void setRootPoints(float[] points) {
        this.points = points;
    }

    public void setRootPos(float x, float y, float z) {
        this.xyz = new float[] { x, y, z };
    }

    public void setRootPos(float[] xyz) {
        this.xyz = xyz;
    }

    public void setRootScale(float sx, float sy, float sz) {
        this.s_xyz = new float[] { sx, sy, sz };
    }

    public void setRootScale(float[] s_xyz) {
        this.s_xyz = s_xyz;
    }

    public void setRootOrientation(float rx, float ry, float rz) {
        this.r_xyz_new = new float[] { rx, ry, rz };
    }

    public void setRootOrientation(float[] r_xyz) {
        this.r_xyz_new = r_xyz;
    }
}
