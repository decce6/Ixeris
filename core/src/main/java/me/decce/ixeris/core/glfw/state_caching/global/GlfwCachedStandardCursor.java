package me.decce.ixeris.core.glfw.state_caching.global;

import org.lwjgl.glfw.GLFW;

import it.unimi.dsi.fastutil.longs.Long2ReferenceOpenHashMap;

public class GlfwCachedStandardCursor {
    private int count;
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
        if (count++ < 0) {
            cursor = GLFW.glfwCreateStandardCursor(shape);
            cursors.put(cursor, this);
        }
    }

    public void dispose() {
        if (count-- <= 0) {
            GLFW.glfwDestroyCursor(cursor);
            cursor = -1;
        }
    }
}
