/*
Auto-generated. See the generator directory in project root.
*/

package me.decce.ixeris.core.glfw.callback_dispatcher;

import it.unimi.dsi.fastutil.longs.Long2ReferenceMap;
import it.unimi.dsi.fastutil.longs.Long2ReferenceMaps;
import it.unimi.dsi.fastutil.longs.Long2ReferenceArrayMap;
import it.unimi.dsi.fastutil.objects.ReferenceArrayList;
import me.decce.ixeris.core.Ixeris;
import me.decce.ixeris.core.threading.MainThreadDispatcher;
import me.decce.ixeris.core.threading.RenderThreadDispatcher;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWMonitorCallbackI;
import org.lwjgl.system.Callback;

import java.util.concurrent.atomic.AtomicInteger;

public class MonitorCallbackDispatcher {
    private static MonitorCallbackDispatcher instance;

    private final ReferenceArrayList<GLFWMonitorCallbackI> mainThreadCallbacks = new ReferenceArrayList<>(1);
    private boolean lastCallbackSet;
    public GLFWMonitorCallbackI lastCallback;
    public GLFWMonitorCallbackI effectiveLastCallback;
    public long lastCallbackAddress;

    public volatile boolean suppressChecks;
    private final AtomicInteger suppressCallbacks = new AtomicInteger();

    private MonitorCallbackDispatcher() {}

    public synchronized static MonitorCallbackDispatcher get() {
        if (instance == null) {
            instance = new MonitorCallbackDispatcher();
            instance.validate();
        }
        return instance;
    }

    public void suppressCallbacks() {
        suppressCallbacks.getAndIncrement();
    }

    public void unsuppressCallbacks() {
        suppressCallbacks.getAndDecrement();
    }

    public synchronized void registerMainThreadCallback(GLFWMonitorCallbackI callback) {
        mainThreadCallbacks.add(callback);
        this.validate();
    }

    public synchronized long update(long newAddress) {
        suppressChecks = true;
        long ret = lastCallbackAddress;
        if (newAddress == 0L && this.mainThreadCallbacks.isEmpty()) {
            GLFW.nglfwSetMonitorCallback(0L);
        }
        else {
            GLFW.nglfwSetMonitorCallback(CommonCallbacks.monitorCallback.address());
        }
        lastCallbackAddress = newAddress;
        if (!lastCallbackSet) {
            lastCallback = newAddress == 0L ? null : Callback.get(newAddress);
        }
        lastCallbackSet = false;
        suppressChecks = false;
        var currentLastCallback = lastCallback;
        MainThreadDispatcher.run(() -> effectiveLastCallback = currentLastCallback);
        return ret;
    }

    public synchronized void update(GLFWMonitorCallbackI callback) {
        lastCallback = callback;
        lastCallbackSet = true;
    }

    public synchronized void validate() {
        suppressChecks = true;
        var current = GLFW.nglfwSetMonitorCallback(CommonCallbacks.monitorCallback.address());
        if (current == 0L) {
            if (this.mainThreadCallbacks.isEmpty()) {
                // Remove callback when not needed
                GLFW.nglfwSetMonitorCallback(0L);
            }
        }
        else if (current != CommonCallbacks.monitorCallback.address()) {
            // This only happens when mods register callbacks without using LWJGL (e.x. directly in native code)
            lastCallback = Callback.get(current);
            lastCallbackAddress = current;
        }
        suppressChecks = false;
    }

    public void onCallback(long monitor, int event) {
        if (this.suppressCallbacks.get() > 0) {
            return;
        }
        for (int i = 0; i < mainThreadCallbacks.size(); i++) {
            mainThreadCallbacks.get(i).invoke(monitor, event);
        }
        if (effectiveLastCallback != null) {
            var callback = effectiveLastCallback; // Keep a reference to the current callback; they are used as FunctionalInterface's so there are no issue even if the callback is already freed when we use it
            RenderThreadDispatcher.runLater((DispatchedRunnable) () -> {
                callback.invoke(monitor, event);
            });
        }
    }

    @FunctionalInterface
    public interface DispatchedRunnable extends Runnable {
    }
}
