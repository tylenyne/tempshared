package JVMSYS;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryUtil;

//Large Spaces represent Section
public class Window extends Module {

    private static final String Title = "BigTest";

    private static int width = 800, height = 600;

    private static long window;

    private static long videobuffer;

    private static boolean resized, vSync;

    public static void main(String[] args) {
        init();
        while(true) {
            update();
        } //cleanup();
    }

    public static void init(){
        GLFWErrorCallback.createPrint(System.err).set();

        if(!GLFW.glfwInit()){
            throw new IllegalStateException("Unable to initialize GLFW");
        }


        //WindowHints apply window attributes
        GLFW.glfwDefaultWindowHints();
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GL30.GL_FALSE);//Imma just copyPaste chatGPTs explanation GLFW.GLFW_VISIBLE: This hint can be set to either GLFW.GLFW_TRUE or GLFW.GLFW_FALSE.GLFW.GLFW_TRUE: If set to GLFW_TRUE, the window will be created in a visible state, meaning it will be shown immediately after creation.GLFW.GLFW_FALSE: If set to GLFW_FALSE, the window will be created in a hidden state. This can be useful if you want to perform some initialization or setup before showing the window to the user.
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GL30.GL_TRUE);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);// Major/greatest version that opengl context can be
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 3);// Minor/least version that opengl context can be
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_CORE_PROFILE); //Sets the GLFW JVM.Window Hint/setting to the Core profile which allow mainly only Modern opengl Features
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_FORWARD_COMPAT, GL30.GL_TRUE);//This is self explanatory toggle a windowHint to true

        boolean maximized = false;
        if(width == 0 || height == 0){
            width = 100;
            height = 100;
            GLFW.glfwWindowHint(GLFW.GLFW_MAXIMIZED, GLFW.GLFW_TRUE);
            maximized = true;
        }
        window = GLFW.glfwCreateWindow(width, height, Title, MemoryUtil.NULL, MemoryUtil.NULL);
        if(window == MemoryUtil.NULL) {
            throw new RuntimeException("Failed to create the GLFW Window");
        }


        GLFW.glfwSetFramebufferSizeCallback(window, (window, width, height) ->
        { //window is also different, probably supplied by glfw
            width = width;//different width, lambda function
            height = height;//different height, lambda function
            resized = true;
        });

        GLFW.glfwSetKeyCallback(window, (window, key, scanc, action, mods) ->
        {
            if(key == GLFW.GLFW_KEY_ESCAPE && action == GLFW.GLFW_RELEASE){
                GLFW.glfwSetWindowShouldClose(window, true);
            }

        });

        if(maximized){
            GLFW.glfwMaximizeWindow(window);
        }
        else{
            GLFWVidMode vidMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
            GLFW.glfwSetWindowPos(window, ((vidMode.width()-width)/2), ((vidMode.height()-height)/2));
        }

        GLFW.glfwMakeContextCurrent(window);

        if(vSync){
            GLFW.glfwSwapInterval(1);
        }

        //This is exactly where glfwshowWindow should be, if no work copy/paste the rest of this method to showWindow
        GL.createCapabilities();
        //this.setClearColor(1,1,1,0);
        GL30.glEnable(GL30.GL_DEPTH_TEST);
        GL30.glEnable(GL30.GL_STENCIL_TEST);
        GL30.glEnable(GL30.GL_CULL_FACE);
        GL30.glCullFace(GL30.GL_BACK);
    }

    public boolean isKeyPressed(int keycode){
        return GLFW.glfwGetKey(window, keycode) == GLFW.GLFW_PRESS;
    }

    public boolean windowShouldClose(){
        return GLFW.glfwWindowShouldClose(window);
    }

    public static void update(){
        GLFW.glfwSwapBuffers(window);
        GLFW.glfwPollEvents();
    }

    public static void setClearColor(float r, float g, float b, float a){
        GL30.glClearColor(r,g,b,a);
    }//Temporary

    public static void cleanup(){
        GLFW.glfwDestroyWindow(window);
    }

}

