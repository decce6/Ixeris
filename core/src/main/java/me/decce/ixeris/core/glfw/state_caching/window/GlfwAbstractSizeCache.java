package me.decce.ixeris.core.glfw.state_caching.window;

import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;

public abstract class GlfwAbstractSizeCache extends GlfwWindowCache {
    public static final int VALUE_UNINITIALIZED = -1;
    protected volatile int width = VALUE_UNINITIALIZED;
    protected volatile int height = VALUE_UNINITIALIZED;

    protected GlfwAbstractSizeCache(long window) {
        super(window);
        registerCallback();
        this.enableCache();
    }

    protected void onAbstractSizeCallback(long window, int width, int height) {
        if (this.window == window) {
            this.width = width;
            this.height = height;
        }
    }

    public int width() {
        if (this.width == VALUE_UNINITIALIZED) {
            blockingGet();
        }
        return this.width;
    }

    public int height() {
        if (this.height == VALUE_UNINITIALIZED) {
            blockingGet();
        }
        return this.height;
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
        width.put(width.position(), this.width);
        height.put(height.position(), this.height);
    }

    private void blockingGet() {
        this.disableCache();
        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer width = stack.mallocInt(1);
            IntBuffer height = stack.mallocInt(1);
            blockingGet(window, width, height);
            this.width = width.get();
            this.height = height.get();
        }
        this.enableCache();
    }

    protected abstract void registerCallback();
    protected abstract void blockingGet(long window, IntBuffer width, IntBuffer height);
}
