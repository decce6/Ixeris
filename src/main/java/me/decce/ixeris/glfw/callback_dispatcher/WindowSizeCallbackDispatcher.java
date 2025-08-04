/*
Auto-generated. See the generator directory in project root.
*/

package me.decce.ixeris.glfw.callback_dispatcher;

import it.unimi.dsi.fastutil.longs.Long2ReferenceMap;
import it.unimi.dsi.fastutil.longs.Long2ReferenceMaps;
import it.unimi.dsi.fastutil.longs.Long2ReferenceArrayMap;
import it.unimi.dsi.fastutil.objects.ReferenceArrayList;
import me.decce.ixeris.threading.RenderThreadDispatcher;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWWindowSizeCallbackI;
import org.lwjgl.system.Callback;

public class WindowSizeCallbackDispatcher {
    private static final Long2ReferenceMap<WindowSizeCallbackDispatcher> instance = Long2ReferenceMaps.synchronize(new Long2ReferenceArrayMap<>(1));

    private final ReferenceArrayList<GLFWWindowSizeCallbackI> mainThreadCallbacks = new ReferenceArrayList<>(1);
    private boolean lastCallbackSet;
    public GLFWWindowSizeCallbackI lastCallback;
    public long lastCallbackAddress;

    private final long window;
    public volatile boolean suppressChecks;

    private WindowSizeCallbackDispatcher(long window) {
        this.window = window;
    }

    public static WindowSizeCallbackDispatcher get(long window) {
        return instance.computeIfAbsent(window, WindowSizeCallbackDispatcher::new);
    }

    public synchronized void registerMainThreadCallback(GLFWWindowSizeCallbackI callback) {
        mainThreadCallbacks.add(callback);
    }

    public synchronized long update(long newAddress) {
        suppressChecks = true;
        long ret = lastCallbackAddress;
        GLFW.nglfwSetWindowSizeCallback(window, newAddress);
        if (newAddress != 0L) {
            GLFW.nglfwSetWindowSizeCallback(window, CommonCallbacks.windowSizeCallback.address());
        }
        lastCallbackAddress = newAddress;
        if (!lastCallbackSet) {
            lastCallback = newAddress == 0L ? null : Callback.get(newAddress);
        }
        lastCallbackSet = false;
        suppressChecks = false;
        return ret;
    }

    public synchronized void update(GLFWWindowSizeCallbackI callback) {
        lastCallback = callback;
        lastCallbackSet = true;
    }

    public synchronized void validate() {
        suppressChecks = true;
        var current = GLFW.nglfwSetWindowSizeCallback(window, 0L);
        if (current != 0L && current != CommonCallbacks.windowSizeCallback.address()) {
            // This only happens when mods register callbacks without using LWJGL (e.x. directly in native code)
            lastCallback = Callback.get(current);
            lastCallbackAddress = current;
        }
        GLFW.nglfwSetWindowSizeCallback(window, current);
        suppressChecks = false;
    }

    public void onCallback(long window, int width, int height) {
        if (this.window != window) {
            return;
        }
        for (int i = 0; i < mainThreadCallbacks.size(); i++) {
            mainThreadCallbacks.get(i).invoke(window, width, height);
        }
        if (lastCallback != null) {
            RenderThreadDispatcher.runLater((DispatchedRunnable) () -> {
                if (lastCallback != null) {
                    lastCallback.invoke(window, width, height);
                }
            });
        }
    }

    @FunctionalInterface
    public interface DispatchedRunnable extends Runnable {
    }
}
