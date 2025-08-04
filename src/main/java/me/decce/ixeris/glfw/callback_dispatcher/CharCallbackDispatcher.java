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
import org.lwjgl.glfw.GLFWCharCallbackI;
import org.lwjgl.system.Callback;

public class CharCallbackDispatcher {
    private static final Long2ReferenceMap<CharCallbackDispatcher> instance = Long2ReferenceMaps.synchronize(new Long2ReferenceArrayMap<>(1));

    private final ReferenceArrayList<GLFWCharCallbackI> mainThreadCallbacks = new ReferenceArrayList<>(1);
    private boolean lastCallbackSet;
    public GLFWCharCallbackI lastCallback;
    public long lastCallbackAddress;

    private final long window;
    public volatile boolean suppressChecks;

    private CharCallbackDispatcher(long window) {
        this.window = window;
    }

    public static CharCallbackDispatcher get(long window) {
        return instance.computeIfAbsent(window, CharCallbackDispatcher::new);
    }

    public synchronized void registerMainThreadCallback(GLFWCharCallbackI callback) {
        mainThreadCallbacks.add(callback);
    }

    public synchronized long update(long newAddress) {
        suppressChecks = true;
        long ret = lastCallbackAddress;
        GLFW.nglfwSetCharCallback(window, newAddress);
        if (newAddress != 0L) {
            GLFW.nglfwSetCharCallback(window, CommonCallbacks.charCallback.address());
        }
        lastCallbackAddress = newAddress;
        if (!lastCallbackSet) {
            lastCallback = newAddress == 0L ? null : Callback.get(newAddress);
        }
        lastCallbackSet = false;
        suppressChecks = false;
        return ret;
    }

    public synchronized void update(GLFWCharCallbackI callback) {
        lastCallback = callback;
        lastCallbackSet = true;
    }

    public synchronized void validate() {
        suppressChecks = true;
        var current = GLFW.nglfwSetCharCallback(window, 0L);
        if (current != 0L && current != CommonCallbacks.charCallback.address()) {
            // This only happens when mods register callbacks without using LWJGL (e.x. directly in native code)
            lastCallback = Callback.get(current);
            lastCallbackAddress = current;
        }
        GLFW.nglfwSetCharCallback(window, current);
        suppressChecks = false;
    }

    public void onCallback(long window, int codepoint) {
        if (this.window != window) {
            return;
        }
        for (int i = 0; i < mainThreadCallbacks.size(); i++) {
            mainThreadCallbacks.get(i).invoke(window, codepoint);
        }
        if (lastCallback != null) {
            RenderThreadDispatcher.runLater((DispatchedRunnable) () -> {
                if (lastCallback != null) {
                    lastCallback.invoke(window, codepoint);
                }
            });
        }
    }

    @FunctionalInterface
    public interface DispatchedRunnable extends Runnable {
    }
}
