package io;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class Input {
    private static Input instance;
    private static boolean[] keysDown = new boolean[350];
    private static boolean[] buttonsDown = new boolean[3];
    public static int mousex, mousey;
    public static int ratex, ratey;
    public static boolean dragging;

    public static Input get(){
        if(instance == null){
            return new Input();
        }
        return null;
    }

    public static void KeyCallback(long window, int key, int scancode, int action, int mods){
        if(action == GLFW_PRESS){
            keysDown[key] = true;
        }
        else if(action == GLFW_RELEASE){
            keysDown[key] = false;
        }
    }

    public static void MouseCallback(long window, double xPos, double yPos){
        ratex = (int)xPos - mousex;
        ratey = (int)yPos - mousey;
        mousex = (int)xPos;
        mousex = (int)yPos;
    }

    public static void ButtonCallback(long window, int button, int action, int mods){
        if(button <= buttonsDown.length){
            if(action == GLFW_PRESS){
                buttonsDown[button] = true;
                if(dragging == false) {
                    dragging = true;
                }
            }
            else if(action == GLFW_RELEASE){
                buttonsDown[button] = false;
                if(dragging == true){
                    dragging = false;
                }
            }
        }
    }

    public static boolean isKeyDown(int key){
        if(key <= keysDown.length){
            return keysDown[key];
        }
        return false;
    }

    public static boolean isButtonDown(int button) {
        if (button <= buttonsDown.length) {
            return buttonsDown[button];
        }
        return false;
    }
}
