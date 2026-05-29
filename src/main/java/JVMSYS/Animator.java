package JVMSYS;

public class Animator {

    public static void plus(float dx, float dy, float dz, Model model) {
        for (int k = 0; k < model.points.length/3; k++) {
            model.points[k * 3] += dx;
            model.points[k * 3 + 1] += dy;
            model.points[k * 3 + 2] += dz;
        } model.setRootPos(model.lx + dx,model.ly + dy,model.lz + dz);
    }

    public static void scale(float sx, float sy, float sz, Model model) {
        for (int k = 0; k < model.points.length/3; k++) {
            model.points[k * 3] *= sx;
            model.points[k * 3 + 1] *= sy;
            model.points[k * 3 + 2] *= sz;
        }
    }

    public static void orient(float step, float ox, float oy, float oz, Model model) {
        float[] A = normalize(new float[]{(float)Math.asin(model.lx),(float)Math.asin(model.ly),(float)Math.asin(model.lz)});
        float[] B = normalize(new float[]{(float)Math.asin(ox),(float)Math.asin(oy),(float)Math.asin(oz)});
        float[] C = cross(A, B);
        float theta = 1/step * dot(A, B);

    }

    public static float[] cross(float[] a, float[] b) {
        return new float[] {
                a[1] * b[2] - a[2] * b[1],
                a[2] * b[0] - a[0] * b[2],
                a[0] * b[1] - a[1] * b[0],
        };
    }

    public static float dot(float[] a, float[] b) {
        return a[0] * b[0] + a[1] * b[1] + a[2] * b[2];
    }

    public static float[] normalize(float[] a) {
        if (a.equals(new float[]{0, 0, 0})) {
            return a;
        } return new float[] {
               a[0] / (a[0] * a[0] + a[1] * a[1] + a[2] * a[2]),
               a[1] / (a[0] * a[0] + a[1] * a[1] + a[2] * a[2]),
               a[2] / (a[0] * a[0] + a[1] * a[1] + a[2] * a[2])
        };
    }

}
