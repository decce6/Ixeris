package me.decce.ixeris.core.glfw.state_caching.util;

import me.decce.ixeris.core.glfw.state_caching.GlfwCacheManager;
import org.lwjgl.glfw.GLFW;

public class InputModeHelper {
    public static final int NUMBER_OF_MODES = 7;
    public static final int INVALID_MODE = -1;
    public static final int UNCACHED_MODE = -2;

    // Returns INVALID_MODE if the provided mode is invalid
    // Returns UNCACHED_MODE if the provided mode should not be cached
    public static int indexFromMode(int mode) {
        return switch (mode) {
            case GLFW.GLFW_CURSOR -> 0;
            case GLFW.GLFW_STICKY_KEYS -> 1;
            case GLFW.GLFW_STICKY_MOUSE_BUTTONS -> 2;
            case GLFW.GLFW_LOCK_KEY_MODS -> 3;
            case GLFW.GLFW_RAW_MOUSE_MOTION -> 4;
            case GLFW.GLFW_UNLIMITED_MOUSE_BUTTONS -> 5;
            // https://github.com/LWJGL-CI/glfw/blob/a6f48b933973e1beedd0be67c85effa2293cc63f/src/input.c#L625-L626
            // GLFW_IME is the only input mode that performs an actual platform call, and thus, cannot be cached
            case GLFW.GLFW_IME -> UNCACHED_MODE;
            default -> INVALID_MODE;
        };
    }

    public static boolean isStickyKeys(long window) {
        return GlfwCacheManager.getWindowCache(window).inputMode().get(GLFW.GLFW_STICKY_KEYS) == GLFW.GLFW_TRUE;
    }

    public static boolean isStickyMouseButtons(long window) {
        return GlfwCacheManager.getWindowCache(window).inputMode().get(GLFW.GLFW_STICKY_MOUSE_BUTTONS) == GLFW.GLFW_TRUE;
    }
}
