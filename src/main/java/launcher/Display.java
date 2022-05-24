package launcher;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;

import static java.sql.Types.NULL;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_MULTISAMPLE;
import static org.lwjgl.system.MemoryStack.stackPush;

public class Display {
    private LauncherGui gui = new LauncherGui();
    public static int width, height, aspect, aspectWidth, aspectHeight;
    public String title, version;
    public long window;

    public Display(int width, int height, String title, String version){
        Display.width = width;
        Display.height = height;
        Display.aspect = width / height;
        this.title = title;
        this.version = version;

        init();
    }

    public static void WindowResizeCallback(long window, int screenWidth, int screenHeight)
    {
        Display.width = screenWidth;
        Display.height = screenHeight;

        // Figure out the largest area that fits this target aspect ratio
        int aspectWidth = screenWidth;
        int aspectHeight = (int)((float)aspectWidth / Display.aspect);

        // Center rectangle
        int vpX = (int)(((float)screenWidth / 2f) - ((float)aspectWidth / 2f));
        int vpY = (int)(((float)screenHeight / 2f) - ((float)aspectHeight / 2f));

        Display.aspectWidth = aspectWidth;
        Display.aspectHeight = aspectHeight;

        glViewport(vpX, vpY, aspectWidth, aspectHeight);
    }

    private void init(){
        GLFWErrorCallback.createPrint(System.err).set();

        if(!glfwInit()){
            throw new IllegalStateException("Unable to initialize glfw");
        }

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_FALSE);
        glfwWindowHint(GLFW_SAMPLES, 4);

        window = glfwCreateWindow(width, height, title + " -version: " + version, NULL, NULL);

        glfwSetMouseButtonCallback(window, Input::mouseButtonCallback);
        glfwSetScrollCallback(window, Input::mouseScrollCallback);
        glfwSetCursorPosCallback(window, Input::mousePosCallback);
        glfwSetKeyCallback(window, Input::keyCallback);
        glfwSetFramebufferSizeCallback(window, Display::WindowResizeCallback);

        if(window == NULL){
            throw new RuntimeException("failed to create glfw window");
        }

        try ( MemoryStack stack = stackPush() ) {
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*

            // Get the window size passed to glfwCreateWindow
            glfwGetWindowSize(window, pWidth, pHeight);

            // Get the resolution of the primary monitor
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            // Center the window
            glfwSetWindowPos(
                    window,
                    (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2
            );
        }

        glfwMakeContextCurrent(window);
        glfwSwapInterval(1);

        glfwShowWindow(window);

        loop();
    }

    private void loop(){
        GL.createCapabilities();
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_CULL_FACE);
        glEnable(GL_MULTISAMPLE);
        gui.init();

        Display.WindowResizeCallback(window, Display.width, Display.height);

        System.out.println("OpenGL version " + glGetString(GL_VERSION));
        while(!glfwWindowShouldClose(window)){
            glfwPollEvents();

            glClearColor(0,0,0,1);
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            gui.loop();

            Input.endFrame();
            glfwSwapBuffers(window);
        }

        gui.end();
    }
}
