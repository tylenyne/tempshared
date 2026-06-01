package JVMSYS;

import it.unimi.dsi.fastutil.floats.FloatArrayList;
import it.unimi.dsi.fastutil.shorts.ShortArrayList;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.CharBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Pattern;

import static JVMSYS.Util.*;

public class Decoder {
    private static float[] vertices;
    private static float[] simplices;
    private static short[] indices;

    private static HashMap<String, Model> cache;

    public static CharBuffer readAllUnicode(String filename) {
        URL url = Decoder.class.getClassLoader().getResource(filename);
        try (FileReader reader = new FileReader(url.getFile())) {
            CharBuffer cb = CharBuffer.allocate((int) Files.size(Paths.get(url.toURI())));
            reader.read(cb);
            cb.flip();
            return cb;
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public static Model loadOBJ(String name, String mode) {
        if (cache == null) {
            cache = new HashMap<>();
        }
        decodeOBJ(name + ".obj", mode);
        float[] center = getCentroid();
        float[] centerObj = centerOBJ(center[0], center[1], center[2]);
        Model ret = new Model();
        ret.setRootPoints(centerObj);
        ret.setRootScale(1, 1, 1);
        ret.setRootPos(0, 0, 0);
        ret.r_xyz_old = new float[] {0, 0, 0};
        ret.r_xyz_new = new float[] {0, 0, 0};
        if (!cache.containsKey(name)) {
            cache.put(name, ret);
        }

        return ret;
    }

    public static void decodeOBJ(String filename, String mode) {
        if (mode == null) {
            mode = "v";
        }
        FloatArrayList v = new FloatArrayList();
        FloatArrayList s = new FloatArrayList();
        ShortArrayList i = new ShortArrayList();

        CharBuffer c = Decoder.readAllUnicode(filename);
        Pattern pattern = Pattern.compile("\n");

        for (String line : pattern.split(c)) {
            String[] tokens = line.split("\\s+");
            switch (tokens[0]) {//.obj file encoding?
                case "v":
                    v.add(Float.parseFloat(tokens[1]));
                    v.add(Float.parseFloat(tokens[2]));
                    v.add(Float.parseFloat(tokens[3]));
                    break;
                case "f":
                    String[] indvToken1 = tokens[1].split("/");
                    String[] indvToken2 = tokens[2].split("/");
                    String[] indvToken3 = tokens[3].split("/");

                    //if quad
                    if (tokens.length == 5) {
                        String[] indvToken4 = tokens[4].split("/");
                        if (mode.equals("v")) {
                            s.add(v.getFloat((Short.parseShort(indvToken1[0]) - 1) * 3));
                            s.add(v.getFloat((Short.parseShort(indvToken1[0]) - 1) * 3 + 1));
                            s.add(v.getFloat((Short.parseShort(indvToken1[0]) - 1) * 3 + 2));
                            s.add(v.getFloat((Short.parseShort(indvToken3[0]) - 1) * 3));
                            s.add(v.getFloat((Short.parseShort(indvToken3[0]) - 1) * 3 + 1));
                            s.add(v.getFloat((Short.parseShort(indvToken3[0]) - 1) * 3 + 2));
                            s.add(v.getFloat((Short.parseShort(indvToken4[0]) - 1) * 3));
                            s.add(v.getFloat((Short.parseShort(indvToken4[0]) - 1) * 3 + 1));
                            s.add(v.getFloat((Short.parseShort(indvToken4[0]) - 1) * 3 + 2));
                        } else if (mode.equals("f")) {
                            i.add((short) (Short.parseShort(indvToken1[0]) - 1));
                            i.add((short) (Short.parseShort(indvToken3[0]) - 1));
                            i.add((short) (Short.parseShort(indvToken4[0]) - 1));
                        }
                    }
                    //then
                    if (mode.equals("v")) {
                        s.add(v.getFloat((Short.parseShort(indvToken1[0]) - 1) * 3));
                        s.add(v.getFloat((Short.parseShort(indvToken1[0]) - 1) * 3 + 1));
                        s.add(v.getFloat((Short.parseShort(indvToken1[0]) - 1) * 3 + 2));
                        s.add(v.getFloat((Short.parseShort(indvToken2[0]) - 1) * 3));
                        s.add(v.getFloat((Short.parseShort(indvToken2[0]) - 1) * 3 + 1));
                        s.add(v.getFloat((Short.parseShort(indvToken2[0]) - 1) * 3 + 2));
                        s.add(v.getFloat((Short.parseShort(indvToken3[0]) - 1) * 3));
                        s.add(v.getFloat((Short.parseShort(indvToken3[0]) - 1) * 3 + 1));
                        s.add(v.getFloat((Short.parseShort(indvToken3[0]) - 1) * 3 + 2));
                    } else if (mode.equals("f")) {
                        i.add((short) (Short.parseShort(indvToken1[0]) - 1));
                        i.add((short) (Short.parseShort(indvToken2[0]) - 1));
                        i.add((short) (Short.parseShort(indvToken3[0]) - 1));
                    }
                    break;
                default:
                    break;
            }
        }
        vertices = v.toFloatArray();
        simplices = s.toFloatArray();
        indices = i.toShortArray();
    }

    public static float[] centerOBJ(float centerx, float centery, float centerz) {
        float[] ret = new float[simplices.length];

        for (int k = 0; k < ret.length / 3; k++) {
            ret[k * 3] = simplices[k * 3] - centerx;
            ret[k * 3 + 1] = simplices[k * 3 + 1] - centery;
            ret[k * 3 + 2] = simplices[k * 3 + 2] - centerz;
        }
        System.out.println("centerx: " + centerx + " centery: " + centery + " centerz: " + centerz);
        return ret;
    }

    public static float[] getCentroid() {
        float[] ret = new float[3];
        float totalArea = 0;
        for (int k = 0; k < simplices.length / 9; k++) {
            float[] a = Arrays.copyOfRange(simplices, k * 9, k * 9 + 3);
            float[] b = Arrays.copyOfRange(simplices, k * 9 + 3, k * 9 + 6);
            float[] c = Arrays.copyOfRange(simplices, k * 9 + 6, k * 9 + 9);
            float area = .5f * magnitude(cross(minus(c, a), minus(b, a)));
            totalArea += area;
            ret[0] += simplices[k * 9] / 3 * area;
            ret[1] += simplices[k * 9 + 1] / 3 * area;
            ret[2] += simplices[k * 9 + 2] / 3 * area;
            ret[0] += simplices[k * 9 + 3] / 3 * area;
            ret[1] += simplices[k * 9 + 4] / 3 * area;
            ret[2] += simplices[k * 9 + 5] / 3 * area;
            ret[0] += simplices[k * 9 + 6] / 3 * area;
            ret[1] += simplices[k * 9 + 7] / 3 * area;
            ret[2] += simplices[k * 9 + 8] / 3 * area;
        }
        ret[0] /= totalArea;
        ret[1] /= totalArea;
        ret[2] /= totalArea;
        return ret;
    }
}
