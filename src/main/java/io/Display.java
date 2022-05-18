package io;

import ecs.scenes.Scene;
import ecs.scenes.TestScene;
import graphics.Renderer;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;

import static java.sql.Types.NULL;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.stackPush;

public class Display {
    public static int width, height, aspect;
    public String title, version;
    public long window;
    public Scene currentScene = new TestScene();

    public Display(int width, int height, String title, String version){
        Display.width = width;
        Display.height = height;
        Display.aspect = width / height;
        this.title = title;
        this.version = version;

        init();
    }

    public static void WindowResizeCallback(long window, int width, int height)
    {
        int aspectWidth = width;
        int aspectHeight = (int)((float)aspectWidth / Display.aspect);
        if (aspectHeight > height){
            aspectHeight = height;
            aspectWidth = (int)((float)aspectHeight * Display.aspect);
        }

        int vpx = (int)(((float)width / 2) - ((float)aspectWidth / 2));
        int vpy = (int)(((float)height / 2) - ((float)aspectHeight / 2));

        glViewport(vpx, vpy, aspectWidth, aspectHeight);
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

        Input.get();

        window = glfwCreateWindow(width, height, title + " -version: " + version, NULL, NULL);

        glfwSetMouseButtonCallback(window, Input::ButtonCallback);
        glfwSetCursorPosCallback(window, Input::MouseCallback);
        glfwSetKeyCallback(window, Input::KeyCallback);
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

        glEnable(GL_ALPHA_TEST);
        glEnable(GL_DEPTH_TEST);

        Display.WindowResizeCallback(window, Display.width, Display.height);

        glClearColor(0, 1, 0.5f, 1);
        currentScene.init();
        while(!glfwWindowShouldClose(window)){

            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            currentScene.loop();
            Renderer.renderScene(currentScene);

            glfwSwapBuffers(window);
            glfwPollEvents();
        }

        end();
    }

    private void end(){
        currentScene.end();
    }
}
