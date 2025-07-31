package me.decce.ixeris.glfw.state_caching;

import java.util.concurrent.atomic.AtomicInteger;

public abstract class GlfwCache {
    protected AtomicInteger enabled;

    protected GlfwCache() {
        this.enabled = new AtomicInteger();
    }

    public boolean isCacheEnabled() {
        return enabled.get() > 0;
    }

    public void enableCache() {
        enabled.getAndIncrement();
    }

    public void disableCache() {
        enabled.getAndDecrement();
    }
}
