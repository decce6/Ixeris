package me.decce.ixeris.core.sdl.state_caching;

import me.decce.ixeris.core.threading.MainThreadDispatcher;
import me.decce.ixeris.core.util.VolatileObjectHolder;

import java.util.function.IntFunction;

public class BasicSdlInt2ObjectCache<T> {
    protected final int key;
    protected final IntFunction<T> function;
    private VolatileObjectHolder<T> cached;
    public BasicSdlInt2ObjectCache(int key, IntFunction<T> function) {
        this.key = key;
        this.function = function;
    }

    public T get() {
        if (cached == null) {
            return blockingGet();
        }
        scheduleUpdate();
        return cached.getValue();
    }

    protected T blockingGet() {
        this.cached = new VolatileObjectHolder<>(MainThreadDispatcher.query(() -> function.apply(key)));
        return this.cached.getValue();
    }

    protected void scheduleUpdate() {
        MainThreadDispatcher.run(() -> this.cached.setValue(function.apply(key)));
    }
}
