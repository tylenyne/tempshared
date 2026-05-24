package JVMSYS;

import java.util.HashMap;

public abstract class Module {
    protected String moduleId;
    protected String moduleBuffer = "";
    protected HashMap<String, Class> registry;

    public void register(Class c) {
        registry.put(c.getName(), c);
    }
}
