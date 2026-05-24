package JVMSYS;

import java.io.*;
import java.util.ArrayList;

import static org.lwjgl.system.windows.WinBase.*;

public class MasterClass extends Module implements AutoCloseable {

    private static ArrayList<Long> dlls;

    public static void init() {

        File[] dir = new File("system").listFiles();
        for (File f : dir) {
            dlls.add(LoadLibrary(null, f.getName()));
        }

    }

    private void buffer() throws NoSuchFieldException {
        for (Class c : super.registry.values()){
            Object mid = c.getDeclaredField("moduleId");
            Object mbuffer = c.getDeclaredField("moduleBuffer");
            System.out.println("_" + mid + "__" + mbuffer + "___");
        }
    }

    @Override
    public void close() throws Exception {

    }
}
