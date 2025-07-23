package me.decce.ixeris.glfw.state_caching.window;

import me.decce.ixeris.glfw.callbacks_threading.RedirectedGLFWFramebufferSizeCallbackI;
import me.decce.ixeris.glfw.state_caching.GlfwWindowCacheManager;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWFramebufferSizeCallback;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;

public class GlfwFramebufferSizeCache {
    public static final int VALUE_UNINITIALIZED = -1;
    private final long window;
    private final GLFWFramebufferSizeCallback previousCallback;
    private int width;
    private int height;

    public GlfwFramebufferSizeCache(long window) {
        this.window = window;
        this.previousCallback = GLFW.glfwSetFramebufferSizeCallback(window, (RedirectedGLFWFramebufferSizeCallbackI) this::onFramebufferSizeCallback);
    }

    private void onFramebufferSizeCallback(long window, int width, int height) {
        if (this.window == window) {
            this.width = width;
            this.height = height;
        }
        if (this.previousCallback != null) {
            this.previousCallback.invoke(window, width, height);
        }
    }

    public void get(int[] width, int[] height) {
        if (this.width == VALUE_UNINITIALIZED || this.height == VALUE_UNINITIALIZED) {
            blockingGet();
        }
        width[0] = this.width;
        height[0] = this.height;
    }

    public void get(IntBuffer width, IntBuffer height) {
        if (this.width == VALUE_UNINITIALIZED || this.height == VALUE_UNINITIALIZED) {
            blockingGet();
        }
        width.put(this.width).flip();
        height.put(this.height).flip();
    }

    private void blockingGet() {
        GlfwWindowCacheManager.useFramebufferSizeCache = false;
        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer width = stack.mallocInt(1);
            IntBuffer height = stack.mallocInt(1);
            GLFW.glfwGetFramebufferSize(window, width, height);
            this.width = width.get();
            this.height = height.get();
        }
        GlfwWindowCacheManager.useFramebufferSizeCache = true;
    }
}
