package me.decce.ixeris.core.glfw.state_caching.window;

import me.decce.ixeris.core.glfw.callback_dispatcher.WindowSizeCallbackDispatcher;
import org.lwjgl.glfw.GLFW;

import java.nio.IntBuffer;

public class GlfwWindowSizeCache extends GlfwAbstractSizeCache {
    public GlfwWindowSizeCache(long window) {
        super(window);
    }

    @Override
    protected void registerCallback() {
        WindowSizeCallbackDispatcher.get(window).registerMainThreadCallback(super::onAbstractSizeCallback);
    }

    @Override
    protected void blockingGet(long window, IntBuffer width, IntBuffer height) {
        GLFW.glfwGetWindowSize(window, width, height);
    }
}
