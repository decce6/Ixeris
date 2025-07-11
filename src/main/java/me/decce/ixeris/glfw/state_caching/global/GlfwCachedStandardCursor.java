package me.decce.ixeris.glfw.state_caching.global;

import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import org.lwjgl.glfw.GLFW;

public class GlfwCachedStandardCursor {
    private boolean disposed;
    private final LongOpenHashSet using;
    private final int shape;
    private final long cursor;

    public GlfwCachedStandardCursor(int shape, long cursor) {
        this.shape = shape;
        this.cursor = cursor;
        this.using = new LongOpenHashSet(1);
    }

    public long cursor() {
        return cursor;
    }

    public int shape() {
        return shape;
    }

    public boolean isUsing() {
        return !using.isEmpty();
    }

    public void use(long window) {
        this.using.add(window);
    }

    public void unuse(long window) {
        this.using.remove(window);
    }

    public void dispose() {
        if (!disposed) {
            GLFW.glfwDestroyCursor(cursor);
            disposed = true;
        }
    }
}
