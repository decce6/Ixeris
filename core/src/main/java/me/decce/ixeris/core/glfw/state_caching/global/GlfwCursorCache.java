package me.decce.ixeris.core.glfw.state_caching.global;

import me.decce.ixeris.core.threading.MainThreadDispatcher;
import me.decce.ixeris.core.util.MemoryHelper;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWImage;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.LongSupplier;

public class GlfwCursorCache extends GlfwGlobalCache {
    private final AtomicLong counter = new AtomicLong(1); // start from 1 to reserve 0 as NULL
    private final ConcurrentHashMap<Long, Long> map = new ConcurrentHashMap<>();

    public GlfwCursorCache() {
        super();
        this.enableCache();
    }

    public long create(LongSupplier supplier) {
        var key = counter.getAndIncrement();
        MainThreadDispatcher.run(() -> {
            var value = supplier.getAsLong();
            map.put(key, value);
        });
        return key;
    }

    public long createStandardCursor(int shape) {
        return create(() -> GLFW.glfwCreateStandardCursor(shape));
    }

    public long createCursor(GLFWImage image, int xhot, int yhot) {
        GLFWImage copiedImage = MemoryHelper.copyGlfwImage(image);
        var ret = create(() -> GLFW.glfwCreateCursor(copiedImage, xhot, yhot));
        MainThreadDispatcher.run(copiedImage::free);
        return ret;
    }

    public long get(long key) {
        return map.getOrDefault(key, key);
    }

    public void apply(long window, long key) {
        MainThreadDispatcher.run(() -> GLFW.glfwSetCursor(window, get(key)));
    }

    public void destroy(long key) {
        var value = map.remove(key);
        if (value == 0L) {
            throw new IllegalStateException("Failed to find cursor for key " + key);
        }
        MainThreadDispatcher.run(() -> GLFW.glfwDestroyCursor(value));
    }
}
