package JVMSYS;

import java.io.*;
import java.util.ArrayList;

import static org.lwjgl.system.windows.WinBase.*;

public class MasterClass extends Module implements AutoCloseable {

    private static ArrayList<Long> dlls;

    public static void main(String[] args) {

    }

    public static void init() {

        File[] dir = new File("system").listFiles();
        for (File f : dir) {
            dlls.add(LoadLibrary(null, f.getName()));
        }

    }

    public static void bufferImmediate(String msg) {
        System.out.println(msg);
    }

    private static void buffer() {
        System.out.println();
    }

    @Override
    public void close() throws Exception {

    }
}
