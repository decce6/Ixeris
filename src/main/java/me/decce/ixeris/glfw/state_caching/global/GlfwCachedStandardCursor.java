package me.decce.ixeris.glfw.state_caching.global;

import org.lwjgl.glfw.GLFW;

import it.unimi.dsi.fastutil.longs.Long2ReferenceOpenHashMap;

public class GlfwCachedStandardCursor {
    private boolean disposed;
    private final int shape;
    private long cursor;

    public GlfwCachedStandardCursor(int shape, long cursor) {
        this.shape = shape;
        this.cursor = cursor;
    }

    public long cursor() {
        return cursor;
    }

    public int shape() {
        return shape;
    }
    
    public void recreate(Long2ReferenceOpenHashMap<GlfwCachedStandardCursor> cursors) {
        if(disposed) {
            cursor = GLFW.glfwCreateStandardCursor(shape);
            cursors.put(cursor, this);
            disposed = false;
        }
    }

    public void dispose() {
        if (!disposed) {
            GLFW.glfwDestroyCursor(cursor);
            disposed = true;
        }
    }
}
