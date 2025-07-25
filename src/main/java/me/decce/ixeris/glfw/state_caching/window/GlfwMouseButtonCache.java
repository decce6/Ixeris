package me.decce.ixeris.glfw.state_caching.window;

import me.decce.ixeris.glfw.callback_stack.MouseButtonCallbackStack;
import me.decce.ixeris.glfw.state_caching.util.InputModeHelper;
import org.lwjgl.glfw.GLFW;

import java.util.concurrent.atomic.AtomicIntegerArray;

public class GlfwMouseButtonCache extends GlfwWindowCache {
    public static final int MOUSE_BUTTON_UNINITIALIZED = -1;
    private final AtomicIntegerArray buttons;

    public GlfwMouseButtonCache(long window) {
        super(window);
        MouseButtonCallbackStack.get(window).registerMainThreadCallback(this::onMouseButtonCallback);
        this.buttons = new AtomicIntegerArray(GLFW.GLFW_MOUSE_BUTTON_LAST + 1);
        for (int i = 0; i < this.buttons.length(); i++) {
            this.buttons.set(i, MOUSE_BUTTON_UNINITIALIZED);
        }
        this.enableCache();
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
        this.disableCache();
        var ret = GLFW.glfwGetMouseButton(window, button);
        this.enableCache();
        return ret;
    }

    private void onMouseButtonCallback(long window, int button, int action, int mods) {
        if (this.window == window && button >= GLFW.GLFW_MOUSE_BUTTON_1 && button <= GLFW.GLFW_MOUSE_BUTTON_LAST) {
            this.buttons.set(button, action);
        }
    }
}
