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
import org.lwjgl.glfw.GLFWDropCallbackI;
import org.lwjgl.system.Callback;

import me.decce.ixeris.util.MemoryHelper;
import org.lwjgl.glfw.GLFWDropCallback;

public class DropCallbackDispatcher {
    private static final Long2ReferenceMap<DropCallbackDispatcher> instance = Long2ReferenceMaps.synchronize(new Long2ReferenceArrayMap<>(1));

    private final ReferenceArrayList<GLFWDropCallbackI> mainThreadCallbacks = new ReferenceArrayList<>(1);
    private boolean lastCallbackSet;
    public GLFWDropCallbackI lastCallback;
    public long lastCallbackAddress;

    private final long window;
    public volatile boolean suppressChecks;

    private DropCallbackDispatcher(long window) {
        this.window = window;
    }

    public static DropCallbackDispatcher get(long window) {
        return instance.computeIfAbsent(window, DropCallbackDispatcher::new);
    }

    public synchronized void registerMainThreadCallback(GLFWDropCallbackI callback) {
        mainThreadCallbacks.add(callback);
        this.validate();
    }

    public synchronized long update(long newAddress) {
        suppressChecks = true;
        long ret = lastCallbackAddress;
        if (newAddress == 0L && this.mainThreadCallbacks.isEmpty()) {
            GLFW.nglfwSetDropCallback(window, 0L);
        }
        else {
            GLFW.nglfwSetDropCallback(window, CommonCallbacks.dropCallback.address());
        }
        lastCallbackAddress = newAddress;
        if (!lastCallbackSet) {
            lastCallback = newAddress == 0L ? null : Callback.get(newAddress);
        }
        lastCallbackSet = false;
        suppressChecks = false;
        return ret;
    }

    public synchronized void update(GLFWDropCallbackI callback) {
        lastCallback = callback;
        lastCallbackSet = true;
    }

    public synchronized void validate() {
        suppressChecks = true;
        var current = GLFW.nglfwSetDropCallback(window, CommonCallbacks.dropCallback.address());
        if (current == 0L) {
            if (this.mainThreadCallbacks.isEmpty()) {
                // Remove callback when not needed
                GLFW.nglfwSetDropCallback(window, 0L);
            }
        }
        else if (current != CommonCallbacks.dropCallback.address()) {
            // This only happens when mods register callbacks without using LWJGL (e.x. directly in native code)
            lastCallback = Callback.get(current);
            lastCallbackAddress = current;
        }
        suppressChecks = false;
    }

    public void onCallback(long window, int count, long names) {
        if (this.window != window) {
            return;
        }
        for (int i = 0; i < mainThreadCallbacks.size(); i++) {
            mainThreadCallbacks.get(i).invoke(window, count, names);
        }
        if (lastCallback != null) {
            var namesCopy = MemoryHelper.deepCopy(names, count, GLFWDropCallback::getName);
            RenderThreadDispatcher.runLater((DispatchedRunnable) () -> {
                if (lastCallback != null) {
                    lastCallback.invoke(window, count, namesCopy);
                }
                MemoryHelper.free(namesCopy, count);
            });
        }
    }

    @FunctionalInterface
    public interface DispatchedRunnable extends Runnable {
    }
}
