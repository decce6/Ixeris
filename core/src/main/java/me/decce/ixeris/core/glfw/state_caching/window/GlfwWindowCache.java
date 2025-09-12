package me.decce.ixeris.core.glfw.state_caching.window;

import me.decce.ixeris.core.glfw.state_caching.GlfwCache;

public abstract class GlfwWindowCache extends GlfwCache {
    protected long window;

    protected GlfwWindowCache(long window) {
        super();
        this.window = window;
    }
}
