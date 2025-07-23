package me.decce.ixeris.glfw.state_caching.window;

import me.decce.ixeris.glfw.callbacks_threading.RedirectedGLFWKeyCallbackI;
import me.decce.ixeris.glfw.state_caching.util.InputModeHelper;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;

import java.util.concurrent.atomic.AtomicIntegerArray;

public class GlfwKeyCache extends GlfwWindowCache {
    public static final int KEY_UNINITIALIZED = -1;
    private GLFWKeyCallback previousCallback;
    private final AtomicIntegerArray keys;

    public GlfwKeyCache(long window) {
        // Create instance of RedirectedGLFWKeyCallbackI, to skip our threading check and allow the callback to run on the main thread
        super(window);
        this.keys = new AtomicIntegerArray(GLFW.GLFW_KEY_LAST + 1);
        for (int i = 0; i < this.keys.length(); i++) {
            this.keys.set(i, KEY_UNINITIALIZED);
        }
    }

    public void init() {
        this.previousCallback = GLFW.glfwSetKeyCallback(window, (RedirectedGLFWKeyCallbackI)(this::onKeyCallback));
        this.enableCache();
    }

    public int get(int key) {
        if (InputModeHelper.isStickyKeys(window)) {
            return blockingGet(key); // do not use cached value when using sticky keys mode (Minecraft does not use it)
        }
        if (key < GLFW.GLFW_KEY_SPACE || key > GLFW.GLFW_KEY_LAST) {
            // Illegal. Let GLFW make an error.
            return blockingGet(key);
        }
        int ret = keys.get(key);
        if (ret == KEY_UNINITIALIZED) {
            ret = blockingGet(key);
            keys.set(key, ret);
        }
        return ret;
    }

    private int blockingGet(int key) {
        this.disableCache();
        var ret = GLFW.glfwGetKey(window, key);
        this.enableCache();
        return ret;
    }

    private void onKeyCallback(long window, int key, int scancode, int action, int mods) {
        if (this.window == window && key >= 0 && key <= GLFW.GLFW_KEY_LAST) {
            this.keys.set(key, action);
        }
        if (previousCallback != null) {
            previousCallback.invoke(window, key, scancode, action, mods);
        }
    }
}
