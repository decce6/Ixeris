package me.decce.ixeris.glfw.state_caching.window;

import me.decce.ixeris.glfw.callbacks_threading.RedirectedGLFWWindowSizeCallbackI;
import me.decce.ixeris.glfw.state_caching.GlfwWindowCacheManager;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;

public class GlfwWindowSizeCache {
    public static final int VALUE_UNINITIALIZED = -1;
    private final long window;
    private final GLFWWindowSizeCallback previousCallback;
    private int width = VALUE_UNINITIALIZED;
    private int height = VALUE_UNINITIALIZED;

    public GlfwWindowSizeCache(long window) {
        this.window = window;
        this.previousCallback = GLFW.glfwSetWindowSizeCallback(window, (RedirectedGLFWWindowSizeCallbackI) this::onWindowSizeCallback);
    }

    private void onWindowSizeCallback(long window, int width, int height) {
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
        GlfwWindowCacheManager.useWindowSizeCache.getAndDecrement();
        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer width = stack.mallocInt(1);
            IntBuffer height = stack.mallocInt(1);
            GLFW.glfwGetWindowSize(window, width, height);
            this.width = width.get();
            this.height = height.get();
        }
        GlfwWindowCacheManager.useWindowSizeCache.getAndIncrement();
    }
}
