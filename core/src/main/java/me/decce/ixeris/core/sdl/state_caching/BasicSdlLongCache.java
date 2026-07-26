package me.decce.ixeris.core.sdl.state_caching;

import me.decce.ixeris.core.threading.MainThreadDispatcher;
import me.decce.ixeris.core.util.VolatileObjectHolder;

import java.util.function.LongFunction;

public class BasicSdlLongCache<T> {
    protected final long window;
    protected final LongFunction<T> function;
    private VolatileObjectHolder<T> cached;
    public BasicSdlLongCache(long window, LongFunction<T> function) {
        this.window = window;
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
        this.cached = new VolatileObjectHolder<>(MainThreadDispatcher.query(() -> function.apply(window)));
        return this.cached.getValue();
    }

    protected void scheduleUpdate() {
        MainThreadDispatcher.run(() -> this.cached.setValue(function.apply(window)));
    }
}
