package JVMSYS;

import crossxyed.SHARED.Scene;

import java.util.ArrayList;
import java.util.Arrays;

public class Scheduler {
    private static ArrayList<Model> registrees;

    public static void register(Model model) {
        if (registrees == null) {
            registrees = new ArrayList<>();
        }
        registrees.add(model);
    }

    public static void assemble() {
        for (Model model : registrees) {
            float[] transformed = model.points.clone();
            for (int k = 0; k < transformed.length/3; k++) {
                transformed[k * 3] *= model.s_xyz[0];
                transformed[k * 3 + 1] *= model.s_xyz[1];
                transformed[k * 3 + 2] *= model.s_xyz[2];
            }
            float[] axis = Util.cross(Util.toDirection(model.r_xyz_old), (Util.toDirection(model.r_xyz_new)));
            float cos = Util.dot(Util.toDirection(model.r_xyz_old), Util.toDirection(model.r_xyz_new));
            float sin = (float) Math.sin(Math.acos(cos));
            for (int k = 0; k < transformed.length/3; k++) {
                float k_dot_v = Util.dot(axis, Arrays.copyOfRange(transformed, k * 3, k * 3 + 3));
                float vx = transformed[k * 3];
                float vy = transformed[k * 3 + 1];
                float vz = transformed[k * 3 + 2];

                transformed[k * 3] = cos * vx + (axis[1] * vz - vy * axis[2]) * sin + axis[0] * k_dot_v * (1 - cos);
                transformed[k * 3 + 1] = cos * vy + (axis[2] * vx - vz * axis[0]) * sin + axis[1] * k_dot_v * (1 - cos);
                transformed[k * 3 + 2] = cos * vz + (axis[0] * vy - vx * axis[1]) * sin + axis[2] * k_dot_v * (1 - cos);

            } model.r_xyz_old = new float[] {0, 0, 0};
            for (int k = 0; k < transformed.length/3; k++) {
                transformed[k * 3] += model.xyz[0];
                transformed[k * 3 + 1] += model.xyz[1];
                transformed[k * 3 + 2] += model.xyz[2];
            }
            Scene.INSTANCE.recvRender(transformed);
        }
    }

    public static void orient(float step, float ox, float oy, float oz, Model model) {
        //float[] A = normalize(new float[]{(float)Math.asin(model.x),(float)Math.asin(model.y),(float)Math.asin(model.z)});
        //float[] B = normalize(new float[]{(float)Math.asin(ox),(float)Math.asin(oy),(float)Math.asin(oz)});
        //float[] C = cross(A, B);
        //float theta = 1/step * dot(A, B);

    }
}
