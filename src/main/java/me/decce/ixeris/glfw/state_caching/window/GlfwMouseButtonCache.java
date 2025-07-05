package me.decce.ixeris.glfw.state_caching.window;

import me.decce.ixeris.glfw.callbacks_threading.RedirectedGLFWMouseButtonCallbackI;
import me.decce.ixeris.glfw.state_caching.GlfwWindowCacheManager;
import me.decce.ixeris.glfw.state_caching.util.InputModeHelper;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWMouseButtonCallback;

import java.util.concurrent.atomic.AtomicIntegerArray;

public class GlfwMouseButtonCache {
    public static final int MOUSE_BUTTON_UNINITIALIZED = -1;
    private final long window;
    private final GLFWMouseButtonCallback previousCallback;
    private final AtomicIntegerArray buttons;

    public GlfwMouseButtonCache(long window) {
        // Create instance of RedirectedGLFWKeyCallbackI, to skip our threading check and allow the callback to run on the main thread
        this.previousCallback = GLFW.glfwSetMouseButtonCallback(window, (RedirectedGLFWMouseButtonCallbackI)(this::onMouseButtonCallback));
        this.window = window;
        this.buttons = new AtomicIntegerArray(GLFW.GLFW_MOUSE_BUTTON_LAST + 1);
        for (int i = 0; i < this.buttons.length(); i++) {
            this.buttons.set(i, MOUSE_BUTTON_UNINITIALIZED);
        }
    }

    public int get(int button) {
        if (InputModeHelper.isStickyMouseButtons(window)) {
            return blockingGet(button); // do not use cached value when using sticky keys mode (Minecraft does not use it)
        }
        if (button < GLFW.GLFW_MOUSE_BUTTON_1 || button > GLFW.GLFW_MOUSE_BUTTON_LAST) {
            // Illegal. Let GLFW make an error.
            return blockingGet(button);
        }
        int ret = buttons.get(button);
        if (ret == MOUSE_BUTTON_UNINITIALIZED) {
            ret = blockingGet(button);
            buttons.set(button, ret);
        }
        return ret;
    }

    private int blockingGet(int button) {
        GlfwWindowCacheManager.useMouseButtonCache = false;
        var ret = GLFW.glfwGetMouseButton(window, button);
        GlfwWindowCacheManager.useMouseButtonCache = true;
        return ret;
    }

    private void onMouseButtonCallback(long window, int button, int action, int mods) {
        if (this.window == window && button >= GLFW.GLFW_MOUSE_BUTTON_1 && button <= GLFW.GLFW_MOUSE_BUTTON_LAST) {
            this.buttons.set(button, action);
        }
        if (previousCallback != null) {
            previousCallback.invoke(window, button, action, mods);
        }
    }
}
