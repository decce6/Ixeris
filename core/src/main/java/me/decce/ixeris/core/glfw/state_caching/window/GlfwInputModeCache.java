package me.decce.ixeris.core.glfw.state_caching.window;

import me.decce.ixeris.core.Ixeris;
import me.decce.ixeris.core.glfw.state_caching.util.InputModeHelper;
import org.lwjgl.glfw.GLFW;

import java.util.concurrent.atomic.AtomicIntegerArray;

public class GlfwInputModeCache extends GlfwWindowCache {
    public static final int INPUT_MODE_UNINITIALIZED = -1;
    private final AtomicIntegerArray modes;

    public GlfwInputModeCache(long window) {
        super(window);
        this.modes = new AtomicIntegerArray(InputModeHelper.NUMBER_OF_MODES);
        for (int i = 0; i < this.modes.length(); i++) {
            this.modes.set(i, INPUT_MODE_UNINITIALIZED);
        }
        this.enableCache();
    }

    public int get(int mode) {
        var index = InputModeHelper.indexFromMode(mode);
        if (index == -1) {
            if (Ixeris.getConfig().shouldLogCacheIssues()) {
                Ixeris.LOGGER.warn("glfwGetInputMode has been called with illegal arguments: mode {}", mode);
                Thread.dumpStack();
            }
            return blockingGet(mode);
        }
        var value = this.modes.get(index);
        if (value == INPUT_MODE_UNINITIALIZED) {
            value = blockingGet(mode);
            this.set(mode, value);
        }
        return consider(mode, value);
    }

    public int consider(int mode, int value) {
        // Fixes https://github.com/decce6/Ixeris/issues/41
        // Reference: https://github.com/SpaiR/imgui-java/blob/c80552861d5de1c929dbe3210cba8b72792e4471/imgui-lwjgl3/src/main/java/imgui/glfw/ImGuiImplGlfw.java#L989
        if (mode == GLFW.GLFW_CURSOR && window == Ixeris.accessor.getMinecraftWindow()) {
            if (value == GLFW.GLFW_CURSOR_NORMAL && Ixeris.accessor.isMouseInternallyGrabbed()) {
                return GLFW.GLFW_CURSOR_DISABLED;
            }
        }
        return value;
    }

    private int blockingGet(int mode) {
        this.disableCache();
        var ret = GLFW.glfwGetInputMode(window, mode);
        this.enableCache();
        return ret;
    }

    public void set(int mode, int value) {
        var index = InputModeHelper.indexFromMode(mode);
        if (index == -1)  {
            if (Ixeris.getConfig().shouldLogCacheIssues()) {
                Ixeris.LOGGER.warn("glfwSetInputMode has been called with illegal arguments: mode {}, value {}", mode, value);
                Thread.dumpStack();
            }
            return;
        }
        this.modes.set(index, value);
    }
}
