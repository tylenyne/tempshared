package JVMSYS;

import crossxyed.SHARED.SPClass;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.system.MemoryUtil;
import static org.lwjgl.glfw.GLFWVulkan.glfwVulkanSupported;

//Large Spaces represent Section
public class Window extends Module {

    private static final String Title = "BigTest";

    private static int width = 800, height = 600;

    private static long window;

    private static int fb0;

    private static boolean resized, vSync;

    public static void main(String[] args) {
        init();
        Decoder.decodeOBJ("basic_bunny.obj", null);
        SPClass.INSTANCE.recvWindow();
        Model Basic_Bunny = Animator.load(0, 0, 0, Decoder.simplices);
        Animator.plus(000f,000f, 0, Basic_Bunny);
        Animator.scale(40f,40f, 40f, Basic_Bunny);
        //Animator.plus(-5000, -5000, 0, Basic_Bunny);
        SPClass.INSTANCE.recvRender(Basic_Bunny.array);
        while (true) {
            if (isKeyPressed(GLFW.GLFW_KEY_ESCAPE)) {
                break;
            } if (isKeyPressed(GLFW.GLFW_KEY_W)) {
                Animator.plus(0f,.1f, 0f, Basic_Bunny);
            } if (isKeyPressed(GLFW.GLFW_KEY_S)) {
                Animator.plus(0f,-.1f, -0f, Basic_Bunny);
            } if (isKeyPressed(GLFW.GLFW_KEY_A)) {
                Animator.plus(.1f,0f, 0, Basic_Bunny);
            } if (isKeyPressed(GLFW.GLFW_KEY_D)) {
                Animator.plus(-.1f,0f, 0, Basic_Bunny);
            } if (isKeyPressed(GLFW.GLFW_KEY_Q)) {
                Animator.plus(0f,0f, -.1f, Basic_Bunny);
            } if (isKeyPressed(GLFW.GLFW_KEY_E)) {
                Animator.plus(0f,0f, .1f, Basic_Bunny);
            }
            SPClass.INSTANCE.recvRender(Basic_Bunny.array);
            SPClass.INSTANCE.Loop();
            SPClass.INSTANCE.buffer();
            swapBuffer();
        }
        cleanup();
    }

    public static void init() {
        GLFWErrorCallback.createPrint(System.err).set();

        if (!GLFW.glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        if (!glfwVulkanSupported()) {
            throw new RuntimeException("Cannot find a compatible Vulkan installable client driver (ICD)");
        }


        //WindowHints apply window attributes
        GLFW.glfwDefaultWindowHints();
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);// Major/greatest version that opengl context can be
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 3);// Minor/least version that opengl context can be
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_CORE_PROFILE); //Sets the GLFW JVM.Window Hint/setting to the Core profile which allow mainly only Modern opengl Features
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_FORWARD_COMPAT, GLFW.GLFW_TRUE);//This is self explanatory toggle a windowHint to true

        boolean maximized = false;
        if (width == 0 || height == 0) {
            width = 100;
            height = 100;
            GLFW.glfwWindowHint(GLFW.GLFW_MAXIMIZED, GLFW.GLFW_TRUE);
            maximized = true;
        }
        window = GLFW.glfwCreateWindow(width, height, Title, MemoryUtil.NULL, MemoryUtil.NULL);
        if (window == MemoryUtil.NULL) {
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
            if (key == GLFW.GLFW_KEY_ESCAPE && action == GLFW.GLFW_RELEASE) {
                GLFW.glfwSetWindowShouldClose(window, true);
            }

        });

        if (maximized) {
            GLFW.glfwMaximizeWindow(window);
        } else {
            GLFWVidMode vidMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
            GLFW.glfwSetWindowPos(window, ((vidMode.width() - width) / 2), ((vidMode.height() - height) / 2));
        }

        GLFW.glfwMakeContextCurrent(window);

        if (vSync) {
            GLFW.glfwSwapInterval(1);
        }

        GLFW.glfwShowWindow(window);
    }

    public static boolean isKeyPressed(int keycode) {
        return GLFW.glfwGetKey(window, keycode) == GLFW.GLFW_PRESS;
    }

    public boolean windowShouldClose() {
        return GLFW.glfwWindowShouldClose(window);
    }

    public static void swapBuffer() {
        GLFW.glfwSwapBuffers(window);
        GLFW.glfwPollEvents();
    }

    public static void cleanup() {
        GLFW.glfwDestroyWindow(window);
    }

}

