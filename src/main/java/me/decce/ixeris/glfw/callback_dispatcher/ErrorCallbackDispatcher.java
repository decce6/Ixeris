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
import org.lwjgl.glfw.GLFWErrorCallbackI;
import org.lwjgl.system.Callback;

import me.decce.ixeris.util.MemoryHelper;

public class ErrorCallbackDispatcher {
    private static final ErrorCallbackDispatcher instance = new ErrorCallbackDispatcher();

    private final ReferenceArrayList<GLFWErrorCallbackI> mainThreadCallbacks = new ReferenceArrayList<>(1);
    private boolean lastCallbackSet;
    public GLFWErrorCallbackI lastCallback;
    public long lastCallbackAddress;

    public volatile boolean suppressChecks;

    private ErrorCallbackDispatcher() {}

    public static ErrorCallbackDispatcher get() {
        return instance;
    }

    public synchronized void registerMainThreadCallback(GLFWErrorCallbackI callback) {
        mainThreadCallbacks.add(callback);
        this.validate();
    }

    public synchronized long update(long newAddress) {
        suppressChecks = true;
        long ret = lastCallbackAddress;
        if (newAddress == 0L && this.mainThreadCallbacks.isEmpty()) {
            GLFW.nglfwSetErrorCallback(0L);
        }
        else {
            GLFW.nglfwSetErrorCallback(CommonCallbacks.errorCallback.address());
        }
        lastCallbackAddress = newAddress;
        if (!lastCallbackSet) {
            lastCallback = newAddress == 0L ? null : Callback.get(newAddress);
        }
        lastCallbackSet = false;
        suppressChecks = false;
        return ret;
    }

    public synchronized void update(GLFWErrorCallbackI callback) {
        lastCallback = callback;
        lastCallbackSet = true;
    }

    public synchronized void validate() {
        suppressChecks = true;
        var current = GLFW.nglfwSetErrorCallback(CommonCallbacks.errorCallback.address());
        if (current == 0L) {
            if (this.mainThreadCallbacks.isEmpty()) {
                // Remove callback when not needed
                GLFW.nglfwSetErrorCallback(0L);
            }
        }
        else if (current != CommonCallbacks.errorCallback.address()) {
            // This only happens when mods register callbacks without using LWJGL (e.x. directly in native code)
            lastCallback = Callback.get(current);
            lastCallbackAddress = current;
        }
        suppressChecks = false;
    }

    public void onCallback(int error, long description) {
        for (int i = 0; i < mainThreadCallbacks.size(); i++) {
            mainThreadCallbacks.get(i).invoke(error, description);
        }
        if (lastCallback != null) {
            var descriptionCopy = MemoryHelper.deepCopy(description);
            RenderThreadDispatcher.runLater((DispatchedRunnable) () -> {
                if (lastCallback != null) {
                    lastCallback.invoke(error, descriptionCopy);
                }
                MemoryHelper.free(descriptionCopy);
            });
        }
    }

    @FunctionalInterface
    public interface DispatchedRunnable extends Runnable {
    }
}
