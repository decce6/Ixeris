package me.decce.ixeris.core.glfw.state_caching.util;

import org.lwjgl.glfw.GLFW;

public class KeyNameHelper {
    // Reference: https://www.glfw.org/docs/3.4/group__input.html#gaeaed62e69c3bd62b7ff8f7b19913ce4f
    public static boolean isPrintable(int key) {
        return  key == GLFW.GLFW_KEY_APOSTROPHE ||
                key == GLFW.GLFW_KEY_COMMA ||
                key == GLFW.GLFW_KEY_MINUS ||
                key == GLFW.GLFW_KEY_PERIOD ||
                key == GLFW.GLFW_KEY_SLASH ||
                key == GLFW.GLFW_KEY_SEMICOLON ||
                key == GLFW.GLFW_KEY_EQUAL ||
                key == GLFW.GLFW_KEY_LEFT_BRACKET ||
                key == GLFW.GLFW_KEY_RIGHT_BRACKET ||
                key == GLFW.GLFW_KEY_BACKSLASH ||
                key == GLFW.GLFW_KEY_WORLD_1 ||
                key == GLFW.GLFW_KEY_WORLD_2 ||
                (key >= GLFW.GLFW_KEY_0  && key <= GLFW.GLFW_KEY_9) ||
                (key >= GLFW.GLFW_KEY_A && key <= GLFW.GLFW_KEY_Z) ||
                (key >= GLFW.GLFW_KEY_KP_0 && key <= GLFW.GLFW_KEY_KP_9) ||
                key == GLFW.GLFW_KEY_KP_DECIMAL ||
                key == GLFW.GLFW_KEY_KP_DIVIDE ||
                key == GLFW.GLFW_KEY_KP_MULTIPLY ||
                key == GLFW.GLFW_KEY_KP_SUBTRACT ||
                key == GLFW.GLFW_KEY_KP_ADD ||
                key == GLFW.GLFW_KEY_KP_EQUAL;
    }
}
