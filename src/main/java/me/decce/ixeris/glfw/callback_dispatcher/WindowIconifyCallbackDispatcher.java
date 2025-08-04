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
import org.lwjgl.glfw.GLFWWindowIconifyCallbackI;
import org.lwjgl.system.Callback;

public class WindowIconifyCallbackDispatcher {
    private static final Long2ReferenceMap<WindowIconifyCallbackDispatcher> instance = Long2ReferenceMaps.synchronize(new Long2ReferenceArrayMap<>(1));

    private final ReferenceArrayList<GLFWWindowIconifyCallbackI> mainThreadCallbacks = new ReferenceArrayList<>(1);
    private boolean lastCallbackSet;
    public GLFWWindowIconifyCallbackI lastCallback;
    public long lastCallbackAddress;

    private final long window;
    public volatile boolean suppressChecks;

    private WindowIconifyCallbackDispatcher(long window) {
        this.window = window;
    }

    public static WindowIconifyCallbackDispatcher get(long window) {
        return instance.computeIfAbsent(window, WindowIconifyCallbackDispatcher::new);
    }

    public synchronized void registerMainThreadCallback(GLFWWindowIconifyCallbackI callback) {
        mainThreadCallbacks.add(callback);
    }

    public synchronized long update(long newAddress) {
        suppressChecks = true;
        long ret = lastCallbackAddress;
        GLFW.nglfwSetWindowIconifyCallback(window, newAddress);
        if (newAddress != 0L) {
            GLFW.nglfwSetWindowIconifyCallback(window, CommonCallbacks.windowIconifyCallback.address());
        }
        lastCallbackAddress = newAddress;
        if (!lastCallbackSet) {
            lastCallback = newAddress == 0L ? null : Callback.get(newAddress);
        }
        lastCallbackSet = false;
        suppressChecks = false;
        return ret;
    }

    public synchronized void update(GLFWWindowIconifyCallbackI callback) {
        lastCallback = callback;
        lastCallbackSet = true;
    }

    public synchronized void validate() {
        suppressChecks = true;
        var current = GLFW.nglfwSetWindowIconifyCallback(window, 0L);
        if (current != 0L && current != CommonCallbacks.windowIconifyCallback.address()) {
            // This only happens when mods register callbacks without using LWJGL (e.x. directly in native code)
            lastCallback = Callback.get(current);
            lastCallbackAddress = current;
        }
        GLFW.nglfwSetWindowIconifyCallback(window, current);
        suppressChecks = false;
    }

    public void onCallback(long window, boolean iconified) {
        if (this.window != window) {
            return;
        }
        for (int i = 0; i < mainThreadCallbacks.size(); i++) {
            mainThreadCallbacks.get(i).invoke(window, iconified);
        }
        if (lastCallback != null) {
            RenderThreadDispatcher.runLater((DispatchedRunnable) () -> {
                if (lastCallback != null) {
                    lastCallback.invoke(window, iconified);
                }
            });
        }
    }

    @FunctionalInterface
    public interface DispatchedRunnable extends Runnable {
    }
}
