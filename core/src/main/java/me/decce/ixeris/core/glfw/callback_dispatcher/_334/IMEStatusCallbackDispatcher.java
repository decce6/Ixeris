/*
Auto-generated. See the generator directory in project root.
*/

package me.decce.ixeris.core.glfw.callback_dispatcher._334;

import it.unimi.dsi.fastutil.longs.Long2ReferenceMap;
import it.unimi.dsi.fastutil.longs.Long2ReferenceMaps;
import it.unimi.dsi.fastutil.longs.Long2ReferenceArrayMap;
import it.unimi.dsi.fastutil.objects.ReferenceArrayList;
import me.decce.ixeris.core.Ixeris;
import me.decce.ixeris.core.threading.MainThreadDispatcher;
import me.decce.ixeris.core.threading.RenderThreadDispatcher;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWIMEStatusCallbackI;
import org.lwjgl.system.Callback;

import java.util.concurrent.atomic.AtomicInteger;

public class IMEStatusCallbackDispatcher {
    private static final Long2ReferenceMap<IMEStatusCallbackDispatcher> instance = new Long2ReferenceArrayMap<>(1);

    private final ReferenceArrayList<GLFWIMEStatusCallbackI> mainThreadCallbacks = new ReferenceArrayList<>(1);
    private boolean lastCallbackSet;
    public GLFWIMEStatusCallbackI lastCallback;
    public GLFWIMEStatusCallbackI effectiveLastCallback;
    public long lastCallbackAddress;

    private final long window;
    public volatile boolean suppressChecks;
    private final AtomicInteger suppressCallbacks = new AtomicInteger();

    private IMEStatusCallbackDispatcher(long window) {
        this.window = window;
    }

    public synchronized static IMEStatusCallbackDispatcher get(long window) {
        if (!instance.containsKey(window)) {
            instance.put(window, new IMEStatusCallbackDispatcher(window));
            instance.get(window).validate();
        }
        return instance.get(window);
    }

    public void suppressCallbacks() {
        suppressCallbacks.getAndIncrement();
    }

    public void unsuppressCallbacks() {
        suppressCallbacks.getAndDecrement();
    }

    public synchronized void registerMainThreadCallback(GLFWIMEStatusCallbackI callback) {
        mainThreadCallbacks.add(callback);
        this.validate();
    }

    public synchronized long update(long newAddress) {
        suppressChecks = true;
        long ret = lastCallbackAddress;
        if (newAddress == 0L && this.mainThreadCallbacks.isEmpty()) {
            GLFW.nglfwSetIMEStatusCallback(window, 0L);
        }
        else {
            GLFW.nglfwSetIMEStatusCallback(window, CommonCallbacks_334.iMEStatusCallback.address());
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

    public synchronized void update(GLFWIMEStatusCallbackI callback) {
        lastCallback = callback;
        lastCallbackSet = true;
    }

    public synchronized void validate() {
        suppressChecks = true;
        var current = GLFW.nglfwSetIMEStatusCallback(window, CommonCallbacks_334.iMEStatusCallback.address());
        if (current == 0L) {
            if (this.mainThreadCallbacks.isEmpty()) {
                // Remove callback when not needed
                GLFW.nglfwSetIMEStatusCallback(window, 0L);
            }
        }
        else if (current != CommonCallbacks_334.iMEStatusCallback.address()) {
            // This only happens when mods register callbacks without using LWJGL (e.x. directly in native code)
            lastCallback = Callback.get(current);
            lastCallbackAddress = current;
        }
        suppressChecks = false;
    }

    public void onCallback(long window) {
        if (this.window != window) {
            return;
        }
        if (this.suppressCallbacks.get() > 0) {
            return;
        }
        for (int i = 0; i < mainThreadCallbacks.size(); i++) {
            mainThreadCallbacks.get(i).invoke(window);
        }
        if (effectiveLastCallback != null) {
            var callback = effectiveLastCallback; // Keep a reference to the current callback; they are used as FunctionalInterface's so there are no issue even if the callback is already freed when we use it
            RenderThreadDispatcher.runLater((DispatchedRunnable) () -> {
                callback.invoke(window);
            });
        }
    }

    @FunctionalInterface
    public interface DispatchedRunnable extends Runnable {
    }
}
