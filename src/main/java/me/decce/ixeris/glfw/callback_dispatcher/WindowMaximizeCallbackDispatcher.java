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
import org.lwjgl.glfw.GLFWWindowMaximizeCallbackI;
import org.lwjgl.system.Callback;

public class WindowMaximizeCallbackDispatcher {
    private static final Long2ReferenceMap<WindowMaximizeCallbackDispatcher> instance = Long2ReferenceMaps.synchronize(new Long2ReferenceArrayMap<>(1));

    private final ReferenceArrayList<GLFWWindowMaximizeCallbackI> mainThreadCallbacks = new ReferenceArrayList<>(1);
    private boolean lastCallbackSet;
    public GLFWWindowMaximizeCallbackI lastCallback;
    public long lastCallbackAddress;

    private final long window;
    public volatile boolean suppressChecks;

    private WindowMaximizeCallbackDispatcher(long window) {
        this.window = window;
    }

    public static WindowMaximizeCallbackDispatcher get(long window) {
        return instance.computeIfAbsent(window, WindowMaximizeCallbackDispatcher::new);
    }

    public synchronized void registerMainThreadCallback(GLFWWindowMaximizeCallbackI callback) {
        mainThreadCallbacks.add(callback);
        this.validate();
    }

    public synchronized long update(long newAddress) {
        suppressChecks = true;
        long ret = lastCallbackAddress;
        if (newAddress == 0L && this.mainThreadCallbacks.isEmpty()) {
            GLFW.nglfwSetWindowMaximizeCallback(window, 0L);
        }
        else {
            GLFW.nglfwSetWindowMaximizeCallback(window, CommonCallbacks.windowMaximizeCallback.address());
        }
        lastCallbackAddress = newAddress;
        if (!lastCallbackSet) {
            lastCallback = newAddress == 0L ? null : Callback.get(newAddress);
        }
        lastCallbackSet = false;
        suppressChecks = false;
        return ret;
    }

    public synchronized void update(GLFWWindowMaximizeCallbackI callback) {
        lastCallback = callback;
        lastCallbackSet = true;
    }

    public synchronized void validate() {
        suppressChecks = true;
        var current = GLFW.nglfwSetWindowMaximizeCallback(window, CommonCallbacks.windowMaximizeCallback.address());
        if (current == 0L) {
            if (this.mainThreadCallbacks.isEmpty()) {
                // Remove callback when not needed
                GLFW.nglfwSetWindowMaximizeCallback(window, 0L);
            }
        }
        else if (current != CommonCallbacks.windowMaximizeCallback.address()) {
            // This only happens when mods register callbacks without using LWJGL (e.x. directly in native code)
            lastCallback = Callback.get(current);
            lastCallbackAddress = current;
        }
        suppressChecks = false;
    }

    public void onCallback(long window, boolean maximized) {
        if (this.window != window) {
            return;
        }
        for (int i = 0; i < mainThreadCallbacks.size(); i++) {
            mainThreadCallbacks.get(i).invoke(window, maximized);
        }
        if (lastCallback != null) {
            RenderThreadDispatcher.runLater((DispatchedRunnable) () -> {
                if (lastCallback != null) {
                    lastCallback.invoke(window, maximized);
                }
            });
        }
    }

    @FunctionalInterface
    public interface DispatchedRunnable extends Runnable {
    }
}
