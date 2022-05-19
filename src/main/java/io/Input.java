package io;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class Input {

    private static double scrollX, scrollY;
    private static double xPos, yPos, lastY, lastX;
    private static boolean mouseButtonPressed[] = new boolean[3];
    private static boolean isDragging;
    private static boolean keyPressed[] = new boolean[350];
    public static boolean scrolling = false;
    public static void keyCallback(long window, int key, int scancode, int action, int mods) {
        if (action == GLFW_PRESS) {
            keyPressed[key] = true;
        } else if (action == GLFW_RELEASE) {
            keyPressed[key] = false;
        }
    }

    public static boolean isKeyPressed(int keyCode) {
        return keyPressed[keyCode];
    }
    
    public static void mousePosCallback(long window, double xpos, double ypos) {
        lastX = xPos;
        lastY = yPos;
        xPos = xpos;
        yPos = ypos;
        isDragging = mouseButtonPressed[0] || mouseButtonPressed[1] || mouseButtonPressed[2];
    }

    public static void mouseButtonCallback(long window, int button, int action, int mods) {
        if (action == GLFW_PRESS) {
            if (button < mouseButtonPressed.length) {
                mouseButtonPressed[button] = true;
            }
        } else if (action == GLFW_RELEASE) {
            if (button < mouseButtonPressed.length) {
                mouseButtonPressed[button] = false;
                isDragging = false;
            }
        }
    }

    public static void mouseScrollCallback(long window, double xOffset, double yOffset) {
        scrollX = xOffset;
        scrollY = yOffset;
    }

    public static void endFrame() {
        scrollX = 0;
        scrollY = 0;
        lastX = xPos;
        lastY = yPos;
    }

    public static float getX() {
        return (float)xPos;
    }

    public static float getY() {
        return (float)yPos;
    }

    public static float getDx() {
        return (float)(lastX - xPos);
    }

    public static float getDy() {
        return (float)(lastY - yPos);
    }

    public static float getScrollX() {
        return (float)scrollX;
    }

    public static float getScrollY() {
        return (float)scrollY;
    }

    public static boolean isDragging() {
        return isDragging;
    }

    public static boolean mouseButtonDown(int button) {
        if (button < mouseButtonPressed.length) {
            return mouseButtonPressed[button];
        } else {
            return false;
        }
    }
}
