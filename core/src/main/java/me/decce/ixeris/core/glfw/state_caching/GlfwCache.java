package me.decce.ixeris.core.glfw.state_caching;

import me.decce.ixeris.core.Ixeris;

import java.util.concurrent.atomic.AtomicInteger;

public abstract class GlfwCache {
    protected AtomicInteger enabled;

    protected GlfwCache() {
        this.enabled = new AtomicInteger();
    }

    public boolean isCacheEnabled() {
        return enabled.get() > 0 && Ixeris.glfwInitialized;
    }

    public void enableCache() {
        enabled.getAndIncrement();
    }

    public void disableCache() {
        enabled.getAndDecrement();
    }
}
