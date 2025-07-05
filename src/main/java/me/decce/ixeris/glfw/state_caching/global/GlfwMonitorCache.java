package me.decce.ixeris.glfw.state_caching.global;

import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongList;
import it.unimi.dsi.fastutil.longs.LongLists;
import me.decce.ixeris.Ixeris;
import me.decce.ixeris.glfw.state_caching.GlfwGlobalCacheManager;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWMonitorCallback;

public class GlfwMonitorCache {
    private final LongList monitors;
    private final boolean success;
    private final GLFWMonitorCallback previousCallback;

    public GlfwMonitorCache() {
        this.monitors = LongLists.synchronize(new LongArrayList());
        this.success = this.initialize();
        this.previousCallback = GLFW.glfwSetMonitorCallback(this::onMonitorCallback);
    }

    public long getPrimaryMonitor() {
        if (!monitors.isEmpty()) {
            return monitors.getFirst();
        }
        else {
            return blockingGetPrimaryMonitor();
        }
    }

    private long blockingGetPrimaryMonitor() {
        GlfwGlobalCacheManager.useMonitorCache = false;
        var ret = GLFW.glfwGetPrimaryMonitor();
        GlfwGlobalCacheManager.useMonitorCache = true;
        return ret;
    }

    private boolean initialize() {
        monitors.clear();
        GlfwGlobalCacheManager.useMonitorCache = false;
        var pointerBuffer = GLFW.glfwGetMonitors();
        GlfwGlobalCacheManager.useMonitorCache = true;
        if (pointerBuffer != null) {
            for (int i = 0; i < pointerBuffer.limit(); i++) {
                monitors.add(pointerBuffer.get(i));
            }
        }
        if (monitors.isEmpty()) {
            // No need to include GLFW error code here since Minecraft already has a GLFWErrorCallback that prints the error code and description to the log
            Ixeris.LOGGER.warn("Failed to initialize monitor cache! You might experience degraded performance.");
        }
        return !monitors.isEmpty();
    }

    private void onMonitorCallback(long monitor, int event) {
        if (success) {
            if (event == GLFW.GLFW_CONNECTED) {
                this.monitors.add(monitor);
            }
            else if (event == GLFW.GLFW_DISCONNECTED) {
                this.monitors.removeIf(m -> m == monitor);
            }
        }
        this.previousCallback.invoke(monitor, event);
    }
}
