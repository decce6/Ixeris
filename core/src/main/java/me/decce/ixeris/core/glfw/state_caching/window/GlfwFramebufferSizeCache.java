package me.decce.ixeris.core.glfw.state_caching.window;

import me.decce.ixeris.core.glfw.callback_dispatcher.FramebufferSizeCallbackDispatcher;
import org.lwjgl.glfw.GLFW;

import java.nio.IntBuffer;

public class GlfwFramebufferSizeCache extends GlfwAbstractSizeCache {
    public GlfwFramebufferSizeCache(long window) {
        super(window);
    }

    @Override
    protected void registerCallback() {
        FramebufferSizeCallbackDispatcher.get(window).registerMainThreadCallback(super::onAbstractSizeCallback);
    }

    @Override
    protected void blockingGet(long window, IntBuffer width, IntBuffer height) {
        GLFW.glfwGetFramebufferSize(window, width, height);
    }
}
