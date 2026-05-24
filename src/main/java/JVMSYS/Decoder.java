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
    public static FloatArrayList vertices = new FloatArrayList();
    public static FloatArrayList simplices = new FloatArrayList();
    public static ShortArrayList indices = new ShortArrayList();

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

        CharBuffer c = Decoder.readAllUnicode(filename);
        Pattern pattern = Pattern.compile("\n");

        for(String line : pattern.split(c)) {
            String[] tokens = line.split("\\s+");
            switch(tokens[0]) {//.obj file encoding?
                case "v":
                    vertices.add(Float.parseFloat(tokens[1]));
                    vertices.add(Float.parseFloat(tokens[2]));
                    vertices.add(Float.parseFloat(tokens[3]));
                    break;
                case "f":
                    String[] indvToken1 = tokens[1].split("/");
                    String[] indvToken2 = tokens[2].split("/");
                    String[] indvToken3 = tokens[3].split("/");

                    //if quad
                    if(tokens.length == 5) {
                        String[] indvToken4 = tokens[4].split("/");
                        if (mode.equals("v")) {
                            simplices.add(vertices.getFloat ((Short.parseShort(indvToken1[0]) - 1) * 3));
                            simplices.add(vertices.getFloat ((Short.parseShort(indvToken1[0]) - 1) * 3 + 1));
                            simplices.add(vertices.getFloat ((Short.parseShort(indvToken1[0]) - 1) * 3 + 2));
                            simplices.add(vertices.getFloat ((Short.parseShort(indvToken3[0]) - 1) * 3));
                            simplices.add(vertices.getFloat ((Short.parseShort(indvToken3[0]) - 1) * 3 + 1));
                            simplices.add(vertices.getFloat ((Short.parseShort(indvToken3[0]) - 1) * 3 + 2));
                            simplices.add(vertices.getFloat ((Short.parseShort(indvToken4[0]) - 1) * 3));
                            simplices.add(vertices.getFloat ((Short.parseShort(indvToken4[0]) - 1) * 3 + 1));
                            simplices.add(vertices.getFloat ((Short.parseShort(indvToken4[0]) - 1) * 3 + 2));
                        } else if (mode.equals("f")) {
                            indices.add((short) (Short.parseShort(indvToken1[0]) - 1));
                            indices.add((short) (Short.parseShort(indvToken3[0]) - 1));
                            indices.add((short) (Short.parseShort(indvToken4[0]) - 1));
                        }
                    }
                    //then
                    if (mode.equals("v")) {
                        simplices.add(vertices.getFloat ((Short.parseShort(indvToken1[0]) - 1) * 3));
                        simplices.add(vertices.getFloat ((Short.parseShort(indvToken1[0]) - 1) * 3 + 1));
                        simplices.add(vertices.getFloat ((Short.parseShort(indvToken1[0]) - 1) * 3 + 2));
                        simplices.add(vertices.getFloat ((Short.parseShort(indvToken2[0]) - 1) * 3));
                        simplices.add(vertices.getFloat ((Short.parseShort(indvToken2[0]) - 1) * 3 + 1));
                        simplices.add(vertices.getFloat ((Short.parseShort(indvToken2[0]) - 1) * 3 + 2));
                        simplices.add(vertices.getFloat ((Short.parseShort(indvToken3[0]) - 1) * 3));
                        simplices.add(vertices.getFloat ((Short.parseShort(indvToken3[0]) - 1) * 3 + 1));
                        simplices.add(vertices.getFloat ((Short.parseShort(indvToken3[0]) - 1) * 3 + 2));
                    } else if (mode.equals("f")) {
                        indices.add((short) (Short.parseShort(indvToken1[0]) - 1));
                        indices.add((short) (Short.parseShort(indvToken2[0]) - 1));
                        indices.add((short) (Short.parseShort(indvToken3[0]) - 1));
                    }
                    break;
                default:
                    break;
            }
        }

    }
}
