package me.decce.ixeris.glfw.state_caching.window;

import me.decce.ixeris.glfw.state_caching.GlfwWindowCacheManager;
import me.decce.ixeris.glfw.state_caching.util.InputModeHelper;
import org.lwjgl.glfw.GLFW;

import java.util.concurrent.atomic.AtomicIntegerArray;

public class GlfwInputModeCache {
    public static final int INPUT_MODE_UNINITIALIZED = -1;
    private final long window;
    private final AtomicIntegerArray modes;

    public GlfwInputModeCache(long window) {
        this.window = window;
        this.modes = new AtomicIntegerArray(InputModeHelper.NUMBER_OF_MODES);
        for (int i = 0; i < this.modes.length(); i++) {
            this.modes.set(i, INPUT_MODE_UNINITIALIZED);
        }
    }

    public int get(int mode) {
        var value = this.modes.get(InputModeHelper.indexFromMode(mode));
        if (value == INPUT_MODE_UNINITIALIZED) {
            value = blockingGet(mode);
            this.set(mode, value);
        }
        return value;
    }

    private int blockingGet(int mode) {
        GlfwWindowCacheManager.useInputModeCache.getAndDecrement();
        var ret = GLFW.glfwGetInputMode(window, mode);
        GlfwWindowCacheManager.useInputModeCache.getAndIncrement();
        return ret;
    }

    public void set(int mode, int value) {
        this.modes.set(InputModeHelper.indexFromMode(mode), value);
    }
}
