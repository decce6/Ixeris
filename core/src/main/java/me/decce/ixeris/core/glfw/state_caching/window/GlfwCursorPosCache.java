package me.decce.ixeris.core.glfw.state_caching.window;

import me.decce.ixeris.core.Ixeris;
import me.decce.ixeris.core.glfw.callback_dispatcher.CursorPosCallbackDispatcher;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.system.MemoryStack;

import java.nio.DoubleBuffer;

public class GlfwCursorPosCache extends GlfwWindowCache {
    private volatile double x;
    private volatile double y;
    private volatile boolean hasValue;

    public GlfwCursorPosCache(long window) {
        super(window);
        if (Ixeris.getConfig().isAggressiveCaching()) {
            CursorPosCallbackDispatcher.get(window).registerMainThreadCallback(this::onCursorPosCallback);
            this.enableCache();
        }
    }

    private void onCursorPosCallback(long window, double x, double y) {
        if (this.window == window) {
            this.x = x;
            this.y = y;
            this.hasValue = true;
        }
    }

    public void get(double[] x, double[] y) {
        if (!this.hasValue) {
            blockingGet();
        }
        x[0] = this.x;
        y[0] = this.y;
    }

    public void get(DoubleBuffer x, DoubleBuffer y) {
        if (!this.hasValue) {
            blockingGet();
        }
        x.put(0, this.x);
        y.put(0, this.y);
    }

    private void blockingGet() {
        this.disableCache();
        try (MemoryStack stack = MemoryStack.stackPush()) {
            DoubleBuffer x = stack.mallocDouble(1);
            DoubleBuffer y = stack.mallocDouble(1);
            GLFW.glfwGetCursorPos(window, x, y);
            this.x = x.get();
            this.y = y.get();
            this.hasValue = true;
        }
        this.enableCache();
    }
}
