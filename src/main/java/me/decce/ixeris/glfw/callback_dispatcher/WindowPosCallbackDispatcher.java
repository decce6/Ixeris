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
import org.lwjgl.glfw.GLFWWindowPosCallbackI;
import org.lwjgl.system.Callback;

public class WindowPosCallbackDispatcher {
    private static final Long2ReferenceMap<WindowPosCallbackDispatcher> instance = Long2ReferenceMaps.synchronize(new Long2ReferenceArrayMap<>(1));

    private final ReferenceArrayList<GLFWWindowPosCallbackI> mainThreadCallbacks = new ReferenceArrayList<>(1);
    private boolean lastCallbackSet;
    public GLFWWindowPosCallbackI lastCallback;
    public long lastCallbackAddress;

    private final long window;
    public volatile boolean suppressChecks;

    private WindowPosCallbackDispatcher(long window) {
        this.window = window;
    }

    public static WindowPosCallbackDispatcher get(long window) {
        return instance.computeIfAbsent(window, WindowPosCallbackDispatcher::new);
    }

    public synchronized void registerMainThreadCallback(GLFWWindowPosCallbackI callback) {
        mainThreadCallbacks.add(callback);
        this.validate();
    }

    public synchronized long update(long newAddress) {
        suppressChecks = true;
        long ret = lastCallbackAddress;
        if (newAddress == 0L && this.mainThreadCallbacks.isEmpty()) {
            GLFW.nglfwSetWindowPosCallback(window, 0L);
        }
        else {
            GLFW.nglfwSetWindowPosCallback(window, CommonCallbacks.windowPosCallback.address());
        }
        lastCallbackAddress = newAddress;
        if (!lastCallbackSet) {
            lastCallback = newAddress == 0L ? null : Callback.get(newAddress);
        }
        lastCallbackSet = false;
        suppressChecks = false;
        return ret;
    }

    public synchronized void update(GLFWWindowPosCallbackI callback) {
        lastCallback = callback;
        lastCallbackSet = true;
    }

    public synchronized void validate() {
        suppressChecks = true;
        var current = GLFW.nglfwSetWindowPosCallback(window, CommonCallbacks.windowPosCallback.address());
        if (current == 0L) {
            if (this.mainThreadCallbacks.isEmpty()) {
                // Remove callback when not needed
                GLFW.nglfwSetWindowPosCallback(window, 0L);
            }
        }
        else if (current != CommonCallbacks.windowPosCallback.address()) {
            // This only happens when mods register callbacks without using LWJGL (e.x. directly in native code)
            lastCallback = Callback.get(current);
            lastCallbackAddress = current;
        }
        suppressChecks = false;
    }

    public void onCallback(long window, int xpos, int ypos) {
        if (this.window != window) {
            return;
        }
        for (int i = 0; i < mainThreadCallbacks.size(); i++) {
            mainThreadCallbacks.get(i).invoke(window, xpos, ypos);
        }
        if (lastCallback != null) {
            RenderThreadDispatcher.runLater((DispatchedRunnable) () -> {
                if (lastCallback != null) {
                    lastCallback.invoke(window, xpos, ypos);
                }
            });
        }
    }

    @FunctionalInterface
    public interface DispatchedRunnable extends Runnable {
    }
}
