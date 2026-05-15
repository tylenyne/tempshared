package JVMSYS;

import java.util.HashMap;

public abstract class Module {
    private static String moduleId;
    private static String moduleBuffer = "";
    private static HashMap<String, Class> registry;
}
