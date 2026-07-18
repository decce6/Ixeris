package me.decce.ixeris.core.glfw.state_caching.global;

import me.decce.ixeris.core.Ixeris;
import me.decce.ixeris.core.threading.MainThreadDispatcher;
import org.lwjgl.glfw.GLFW;

import it.unimi.dsi.fastutil.ints.Int2ReferenceOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ReferenceOpenHashMap;

public class GlfwStandardCursorCache extends GlfwGlobalCache {
    private final Int2ReferenceOpenHashMap<GlfwCachedStandardCursor> shapes = new Int2ReferenceOpenHashMap<>();
    private final Long2ReferenceOpenHashMap<GlfwCachedStandardCursor> cursors = new Long2ReferenceOpenHashMap<>();

    public GlfwStandardCursorCache() {
        super();
        this.enableCache();
    }

    public long create(int shape) {
        var cache = shapes.get(shape);
        if (cache == null) {
            cache = blockingCreate(shape);
        }else {
            var existing = cache;
            if (!Ixeris.isOnMainThread()) {
                MainThreadDispatcher.runNow(() -> {
                    this.disableCache();
                    existing.recreate(cursors);
                    this.enableCache();
                });
            } else {
                disableCache();
                existing.recreate(cursors);
                enableCache();
            }
        }
        return cache == null ? 0L : cache.cursor();
    }

    public boolean isCached(long cursor) {
        return cursors.containsKey(cursor);
    }

    public void destroy(long cursor) {
        var cache = cursors.get(cursor);
        if (cache != null && (cache.cursor() == cursor)) {
            if (!Ixeris.isOnMainThread()) {
                MainThreadDispatcher.runNow(() -> {
                    this.disableCache();
                    cache.dispose();
                    this.enableCache();
                });
            } else {
                this.disableCache();
                cache.dispose();
                this.enableCache();
            }
        }
    }

    private GlfwCachedStandardCursor blockingCreate(int shape) {
        if (!Ixeris.isOnMainThread()) {
            return MainThreadDispatcher.query(() -> {
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
            });
        }
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
