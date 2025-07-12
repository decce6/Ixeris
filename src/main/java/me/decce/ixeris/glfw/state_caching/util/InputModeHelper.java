package me.decce.ixeris.glfw.state_caching.util;

import me.decce.ixeris.glfw.state_caching.GlfwCacheManager;
import org.lwjgl.glfw.GLFW;

public class InputModeHelper {
    public static final int NUMBER_OF_MODES = 5;

    public static int indexFromMode(int mode) {
        return switch (mode) {
            case GLFW.GLFW_CURSOR -> 0;
            case GLFW.GLFW_STICKY_KEYS -> 1;
            case GLFW.GLFW_STICKY_MOUSE_BUTTONS -> 2;
            case GLFW.GLFW_LOCK_KEY_MODS -> 3;
            case GLFW.GLFW_RAW_MOUSE_MOTION -> 4;
            default -> throw new IllegalArgumentException("Unexpected input mode: " + mode);
        };
    }

    public static boolean isStickyKeys(long window) {
        return GlfwCacheManager.getWindowCache(window).inputMode().get(GLFW.GLFW_STICKY_KEYS) == GLFW.GLFW_TRUE;
    }

    public static boolean isStickyMouseButtons(long window) {
        return GlfwCacheManager.getWindowCache(window).inputMode().get(GLFW.GLFW_STICKY_MOUSE_BUTTONS) == GLFW.GLFW_TRUE;
    }
}
