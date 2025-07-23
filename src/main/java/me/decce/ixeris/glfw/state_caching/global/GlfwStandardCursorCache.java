package me.decce.ixeris.glfw.state_caching.global;

import it.unimi.dsi.fastutil.ints.Int2ReferenceOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ReferenceOpenHashMap;
import me.decce.ixeris.threading.MainThreadDispatcher;
import org.lwjgl.glfw.GLFW;

public class GlfwStandardCursorCache extends GlfwGlobalCache {
    private final Int2ReferenceOpenHashMap<GlfwCachedStandardCursor> shapes = new Int2ReferenceOpenHashMap<>();
    private final Long2ReferenceOpenHashMap<GlfwCachedStandardCursor> cursors = new Long2ReferenceOpenHashMap<>();

    public GlfwStandardCursorCache() {
        super();
        this.enableCache();
    }

    public synchronized long create(int shape) {
        var cache = shapes.get(shape);
        if (cache == null) {
            cache = blockingCreate(shape);
        }
        return cache == null ? 0L : cache.cursor();
    }

    public synchronized boolean isCached(long cursor) {
        return cursors.containsKey(cursor);
    }

    public void destroy(long cursor) {
        // Destroy standard cursors later, and while on the main thread, create one for later use
        MainThreadDispatcher.runLater(() -> {
            synchronized (this) {
                var cache = cursors.get(cursor);
                if (cache != null && !cache.isUsing()) {
                    this.disableCache();
                    cache.dispose();
                    this.enableCache();
                    cursors.remove(cursor);
                    shapes.values().removeIf(value -> value.cursor() == cursor);
                    blockingCreate(cache.shape());
                }
            }
        });
    }

    public synchronized void onSet(long window, long cursor) {
        var cache = cursors.get(cursor);
        if (cache != null) {
            cursors.values().forEach(value -> value.unuse(window));
            cache.use(window);
        }
    }

    private GlfwCachedStandardCursor blockingCreate(int shape) {
        this.disableCache();
        var cursor = GLFW.glfwCreateStandardCursor(shape);
        this.enableCache();
        if (cursor != 0L) {
            var ret = new GlfwCachedStandardCursor(shape, cursor);
            shapes.put(shape, ret);
            cursors.put(cursor, ret);
            return ret;
        }
        return null;
    }
}
