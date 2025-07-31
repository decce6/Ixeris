package me.decce.ixeris.glfw.state_caching.global;

import it.unimi.dsi.fastutil.longs.Long2ReferenceOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongArraySet;
import org.lwjgl.glfw.GLFW;

public class GlfwCachedStandardCursor {
    private boolean disposed;
    private final LongArraySet using;
    private final int shape;
    private long cursor;

    public GlfwCachedStandardCursor(int shape, long cursor) {
        this.shape = shape;
        this.cursor = cursor;
        this.using = new LongArraySet(1);
    }

    public long cursor() {
        return cursor;
    }

    public int shape() {
        return shape;
    }

    public void use(long window) {
        this.using.add(window);
    }

    public void unuse(long window) {
        this.using.remove(window);
    }
    
    public void recreate(Long2ReferenceOpenHashMap<GlfwCachedStandardCursor> cursors) {
        if(disposed) {
            cursor = GLFW.glfwCreateStandardCursor(shape);
            cursors.put(cursor, this);
            disposed = false;
        }
    }

    public void dispose(Long2ReferenceOpenHashMap<GlfwCachedStandardCursor> windows) {
        if (!disposed) {
            using.forEach(window -> windows.put(window, null));
            using.clear();
            GLFW.glfwDestroyCursor(cursor);
            disposed = true;
        }
    }
}
