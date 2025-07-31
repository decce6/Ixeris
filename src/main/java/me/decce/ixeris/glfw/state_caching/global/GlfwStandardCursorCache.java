package me.decce.ixeris.glfw.state_caching.global;

import it.unimi.dsi.fastutil.ints.Int2ReferenceOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ReferenceOpenHashMap;
import me.decce.ixeris.threading.MainThreadDispatcher;
import org.lwjgl.glfw.GLFW;

public class GlfwStandardCursorCache extends GlfwGlobalCache {
    private final Int2ReferenceOpenHashMap<GlfwCachedStandardCursor> shapes = new Int2ReferenceOpenHashMap<>();
    private final Long2ReferenceOpenHashMap<GlfwCachedStandardCursor> cursors = new Long2ReferenceOpenHashMap<>();
    private final Long2ReferenceOpenHashMap<GlfwCachedStandardCursor> windows = new Long2ReferenceOpenHashMap<>();

    public GlfwStandardCursorCache() {
        super();
        this.enableCache();
    }

    public synchronized long create(int shape) {
        var cache = shapes.get(shape);
        if (cache == null) {
            cache = blockingCreate(shape);
        }else {
            disableCache();
            cache.recreate(cursors);
            enableCache();
        }
        return cache == null ? 0L : cache.cursor();
    }

    public synchronized boolean isCached(long cursor) {
        return cursors.containsKey(cursor);
    }

    public void destroy(long cursor) {
        MainThreadDispatcher.runNow(() -> {
            synchronized (this) {
                var cache = cursors.get(cursor);
                if (cache != null && (cache.cursor() == cursor)) {
                    this.disableCache();
                    cache.dispose(windows);
                    this.enableCache();
                }
            }
        });
    }

    public synchronized void onSet(long window, long cursor) {
        var cache = windows.get(window);
        if(cache != null) {
            cache.unuse(window);
        }
        GlfwCachedStandardCursor newCache = cursors.get(cursor);
        if(newCache != null) {
            windows.put(window, newCache);
            newCache.use(window);
        }else {
            windows.put(window, null);
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
