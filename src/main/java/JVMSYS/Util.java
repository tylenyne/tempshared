package JVMSYS;

public class Util {
    public static float[] minus(float[] a, float[] b) {
        return new float[] {
                a[0] - b[0],
                a[1] - b[1],
                a[2] - b[2],
        };
    }

    public static float[] plus(float[] a, float[] b) {
        return new float[] {
                a[0] + b[0],
                a[1] + b[1],
                a[2] + b[2],
        };
    }

    public static float[] cross(float[] a, float[] b) {
        return new float[] {
                a[1] * b[2] - a[2] * b[1],
                a[2] * b[0] - a[0] * b[2],
                a[0] * b[1] - a[1] * b[0],
        };
    }

    public static float dot(float[] a, float[] b) {
        return respective(a, b, 0) + respective(a, b, 1) + respective(a, b, 2);
    }

    public static float magnitude(float[] a) {
        return (float) Math.sqrt(a[0] * a[0] + a[1] * a[1] + a[2] * a[2]);
    }

    public static float[] normalize(float[] a) {
        if (magnitude(a) == 0) {
            return a;
        }
        return new float[]{
                a[0] / magnitude(a),
                a[1] / magnitude(a),
                a[2] / magnitude(a)
        };
    }

    public static float[] toDirection(float[] euler) {
        float pitch = euler[0];
        float yaw   = euler[1];
        return new float[]{
                (float)(Math.cos(pitch) * Math.cos(yaw)),
                (float)(Math.cos(pitch) * Math.sin(yaw)),
                (float)(Math.sin(pitch))
        }; //Full Ai
    }

    public static float determinant(float[] a, float[] b, int index) {
        return a[index] * b[(index + 1) % a.length]; //Not finished
    }

    public static float respective(float[] a, float[] b, int index) {
        return a[index] * b[index];
    }
}
