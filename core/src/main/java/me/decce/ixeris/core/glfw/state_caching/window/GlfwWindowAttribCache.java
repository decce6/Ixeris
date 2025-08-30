package me.decce.ixeris.core.glfw.state_caching.window;

import me.decce.ixeris.core.util.PlatformHelper;
import me.decce.ixeris.core.glfw.callback_dispatcher.CursorEnterCallbackDispatcher;
import me.decce.ixeris.core.glfw.callback_dispatcher.WindowFocusCallbackDispatcher;
import me.decce.ixeris.core.glfw.callback_dispatcher.WindowIconifyCallbackDispatcher;
import org.lwjgl.glfw.GLFW;

public class GlfwWindowAttribCache extends GlfwWindowCache {
    public static final int VALUE_UNINITIALIZED = -1;
    private volatile int focused = VALUE_UNINITIALIZED;
    private volatile int iconified = VALUE_UNINITIALIZED;
    private volatile int hovered = VALUE_UNINITIALIZED;

    public GlfwWindowAttribCache(long window) {
        super(window);
        CursorEnterCallbackDispatcher.get(window).registerMainThreadCallback(this::onCursorEnterCallback);
        WindowFocusCallbackDispatcher.get(window).registerMainThreadCallback(this::onWindowFocusCallback);
        WindowIconifyCallbackDispatcher.get(window).registerMainThreadCallback(this::onWindowIconifyCallback);
        this.enableCache();
    }

    private void onCursorEnterCallback(long window, boolean entered) {
        if (this.window == window) {
            this.hovered = entered ? 1 : 0;
        }
    }

    private void onWindowFocusCallback(long window, boolean focused) {
        if (this.window == window) {
            this.focused = focused ? 1 : 0;
        }
    }

    private void onWindowIconifyCallback(long window, boolean iconified) {
        if (this.window == window) {
            this.iconified = iconified ? 1 : 0;
        }
    }

    public int get(int attrib) {
        switch (attrib) {
            case GLFW.GLFW_HOVERED -> {
                if (this.hovered == VALUE_UNINITIALIZED) {
                    this.hovered = blockingGet(attrib);
                }
                return this.hovered;
            }
            case GLFW.GLFW_FOCUSED -> {
                if (this.focused == VALUE_UNINITIALIZED) {
                    this.focused = blockingGet(attrib);
                }
                return this.focused;
            }
            case GLFW.GLFW_ICONIFIED -> {
                if (this.iconified == VALUE_UNINITIALIZED) {
                    if (PlatformHelper.isLinux()) {
                        // Return directly without storing the value, as on Wayland the iconified callback does not run.
                        // Ideally we want to distinguish whether we're on X11 or on Wayland, but glfwGetPlatform() is
                        // not available until GLFW 3.4.
                        return blockingGet(attrib);
                    }
                    this.iconified = blockingGet(attrib);
                }
                return this.iconified;
            }
            default -> {
                return blockingGet(attrib);
            }
        }
    }

    private int blockingGet(int attrib) {
        this.disableCache();
        var ret = GLFW.glfwGetWindowAttrib(window, attrib);
        this.enableCache();
        return ret;
    }
}
