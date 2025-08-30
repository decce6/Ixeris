package me.decce.ixeris.core.glfw.state_caching.window;

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
        var value = this.modes.get(InputModeHelper.indexFromMode(mode));
        if (value == INPUT_MODE_UNINITIALIZED) {
            value = blockingGet(mode);
            this.set(mode, value);
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
        this.modes.set(InputModeHelper.indexFromMode(mode), value);
    }
}
