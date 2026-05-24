package JVMSYS;

import it.unimi.dsi.fastutil.floats.FloatArrayList;
import it.unimi.dsi.fastutil.shorts.ShortArrayList;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.CharBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Pattern;

public class Decoder {
    public static float[] vertices;
    public static float[] simplices;
    public static short[] indices;

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

    public static void decodeOBJ(String filename, String mode) {
        if (mode == null) {
            mode = "v";
        }
        FloatArrayList v = new FloatArrayList();
        FloatArrayList s = new FloatArrayList();
        ShortArrayList i = new ShortArrayList();

        CharBuffer c = Decoder.readAllUnicode(filename);
        Pattern pattern = Pattern.compile("\n");

        for(String line : pattern.split(c)) {
            String[] tokens = line.split("\\s+");
            switch(tokens[0]) {//.obj file encoding?
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
                    if(tokens.length == 5) {
                        String[] indvToken4 = tokens[4].split("/");
                        if (mode.equals("v")) {
                            s.add(v.getFloat ((Short.parseShort(indvToken1[0]) - 1) * 3));
                            s.add(v.getFloat ((Short.parseShort(indvToken1[0]) - 1) * 3 + 1));
                            s.add(v.getFloat ((Short.parseShort(indvToken1[0]) - 1) * 3 + 2));
                            s.add(v.getFloat ((Short.parseShort(indvToken3[0]) - 1) * 3));
                            s.add(v.getFloat ((Short.parseShort(indvToken3[0]) - 1) * 3 + 1));
                            s.add(v.getFloat ((Short.parseShort(indvToken3[0]) - 1) * 3 + 2));
                            s.add(v.getFloat ((Short.parseShort(indvToken4[0]) - 1) * 3));
                            s.add(v.getFloat ((Short.parseShort(indvToken4[0]) - 1) * 3 + 1));
                            s.add(v.getFloat ((Short.parseShort(indvToken4[0]) - 1) * 3 + 2));
                        } else if (mode.equals("f")) {
                            i.add((short) (Short.parseShort(indvToken1[0]) - 1));
                            i.add((short) (Short.parseShort(indvToken3[0]) - 1));
                            i.add((short) (Short.parseShort(indvToken4[0]) - 1));
                        }
                    }
                    //then
                    if (mode.equals("v")) {
                        s.add(v.getFloat ((Short.parseShort(indvToken1[0]) - 1) * 3));
                        s.add(v.getFloat ((Short.parseShort(indvToken1[0]) - 1) * 3 + 1));
                        s.add(v.getFloat ((Short.parseShort(indvToken1[0]) - 1) * 3 + 2));
                        s.add(v.getFloat ((Short.parseShort(indvToken2[0]) - 1) * 3));
                        s.add(v.getFloat ((Short.parseShort(indvToken2[0]) - 1) * 3 + 1));
                        s.add(v.getFloat ((Short.parseShort(indvToken2[0]) - 1) * 3 + 2));
                        s.add(v.getFloat ((Short.parseShort(indvToken3[0]) - 1) * 3));
                        s.add(v.getFloat ((Short.parseShort(indvToken3[0]) - 1) * 3 + 1));
                        s.add(v.getFloat ((Short.parseShort(indvToken3[0]) - 1) * 3 + 2));
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
}
