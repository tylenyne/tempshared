package JVMSYS;

public class Animator {
    public static void plus(float dx, float dy, float dz, float[] model) {
        for (int k = 0; k < model.length/3; k++) {
            model[k * 3] += dx;
            model[k * 3 + 1] += dy;
            model[k * 3 + 2] += dz;
        }
    }
}
