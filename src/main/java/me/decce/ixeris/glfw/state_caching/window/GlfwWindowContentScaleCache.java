package me.decce.ixeris.glfw.state_caching.window;

import me.decce.ixeris.glfw.callbacks_threading.RedirectedGLFWWindowContentScaleCallbackI;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWWindowContentScaleCallback;
import org.lwjgl.system.MemoryStack;

import java.nio.FloatBuffer;

public class GlfwWindowContentScaleCache extends GlfwWindowCache {
    public static final float VALUE_UNINITIALIZED = Float.NEGATIVE_INFINITY;
    private GLFWWindowContentScaleCallback previousCallback;
    private float xscale = VALUE_UNINITIALIZED;
    private float yscale = VALUE_UNINITIALIZED;

    public GlfwWindowContentScaleCache(long window) {
        super(window);
    }

    public void init() {
        this.previousCallback = GLFW.glfwSetWindowContentScaleCallback(window, (RedirectedGLFWWindowContentScaleCallbackI) this::onWindowContentScaleCallback);
        this.enableCache();
    }

    private void onWindowContentScaleCallback(long window, float xscale, float yscale) {
        if (this.window == window) {
            this.xscale = xscale;
            this.yscale = yscale;
        }
        if (this.previousCallback != null) {
            this.previousCallback.invoke(window, this.xscale, this.yscale);
        }
    }

    public void get(float[] xscale, float[] yscale) {
        if (this.xscale == VALUE_UNINITIALIZED || this.yscale == VALUE_UNINITIALIZED) {
            blockingGet();
        }
        xscale[0] = this.xscale;
        yscale[0] = this.yscale;
    }

    public void get(FloatBuffer xscale, FloatBuffer yscale) {
        if (this.xscale == VALUE_UNINITIALIZED || this.yscale == VALUE_UNINITIALIZED) {
            blockingGet();
        }
        xscale.put(this.xscale).flip();
        yscale.put(this.yscale).flip();
    }

    private void blockingGet() {
        this.disableCache();
        try (MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer xscale = stack.mallocFloat(1);
            FloatBuffer yscale = stack.mallocFloat(1);
            GLFW.glfwGetWindowContentScale(window, xscale, yscale);
            this.xscale = xscale.get();
            this.yscale = yscale.get();
        }
        this.enableCache();
    }
}
