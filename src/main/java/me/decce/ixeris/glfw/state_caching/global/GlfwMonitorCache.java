package me.decce.ixeris.glfw.state_caching.global;

import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongList;
import it.unimi.dsi.fastutil.longs.LongLists;
import me.decce.ixeris.Ixeris;
import me.decce.ixeris.glfw.callback_dispatcher.MonitorCallbackDispatcher;
import org.lwjgl.glfw.GLFW;

public class GlfwMonitorCache extends GlfwGlobalCache {
    private final LongList monitors;
    private final boolean success;

    public GlfwMonitorCache() {
        this.monitors = LongLists.synchronize(new LongArrayList());
        this.success = this.initialize();
        MonitorCallbackDispatcher.get().registerMainThreadCallback(this::onMonitorCallback);
        if (this.success) {
            this.enableCache();
        }
    }

    public long getPrimaryMonitor() {
        if (!monitors.isEmpty()) {
            return monitors.getLong(0);
        }
        else {
            return blockingGetPrimaryMonitor();
        }
    }

    private long blockingGetPrimaryMonitor() {
        this.disableCache();
        var ret = GLFW.glfwGetPrimaryMonitor();
        this.enableCache();
        return ret;
    }

    private boolean initialize() {
        monitors.clear();
        this.disableCache();
        var pointerBuffer = GLFW.glfwGetMonitors();
        this.enableCache();
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
    }
}
