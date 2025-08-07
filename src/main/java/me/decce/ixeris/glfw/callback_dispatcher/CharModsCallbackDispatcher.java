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
import org.lwjgl.glfw.GLFWCharModsCallbackI;
import org.lwjgl.system.Callback;

public class CharModsCallbackDispatcher {
    private static final Long2ReferenceMap<CharModsCallbackDispatcher> instance = Long2ReferenceMaps.synchronize(new Long2ReferenceArrayMap<>(1));

    private final ReferenceArrayList<GLFWCharModsCallbackI> mainThreadCallbacks = new ReferenceArrayList<>(1);
    private boolean lastCallbackSet;
    public GLFWCharModsCallbackI lastCallback;
    public long lastCallbackAddress;

    private final long window;
    public volatile boolean suppressChecks;

    private CharModsCallbackDispatcher(long window) {
        this.window = window;
    }

    public static CharModsCallbackDispatcher get(long window) {
        return instance.computeIfAbsent(window, CharModsCallbackDispatcher::new);
    }

    public synchronized void registerMainThreadCallback(GLFWCharModsCallbackI callback) {
        mainThreadCallbacks.add(callback);
        this.validate();
    }

    public synchronized long update(long newAddress) {
        suppressChecks = true;
        long ret = lastCallbackAddress;
        if (newAddress == 0L && this.mainThreadCallbacks.isEmpty()) {
            GLFW.nglfwSetCharModsCallback(window, 0L);
        }
        else {
            GLFW.nglfwSetCharModsCallback(window, CommonCallbacks.charModsCallback.address());
        }
        lastCallbackAddress = newAddress;
        if (!lastCallbackSet) {
            lastCallback = newAddress == 0L ? null : Callback.get(newAddress);
        }
        lastCallbackSet = false;
        suppressChecks = false;
        return ret;
    }

    public synchronized void update(GLFWCharModsCallbackI callback) {
        lastCallback = callback;
        lastCallbackSet = true;
    }

    public synchronized void validate() {
        suppressChecks = true;
        var current = GLFW.nglfwSetCharModsCallback(window, CommonCallbacks.charModsCallback.address());
        if (current == 0L) {
            if (this.mainThreadCallbacks.isEmpty()) {
                // Remove callback when not needed
                GLFW.nglfwSetCharModsCallback(window, 0L);
            }
        }
        else if (current != CommonCallbacks.charModsCallback.address()) {
            // This only happens when mods register callbacks without using LWJGL (e.x. directly in native code)
            lastCallback = Callback.get(current);
            lastCallbackAddress = current;
        }
        suppressChecks = false;
    }

    public void onCallback(long window, int codepoint, int mods) {
        if (this.window != window) {
            return;
        }
        for (int i = 0; i < mainThreadCallbacks.size(); i++) {
            mainThreadCallbacks.get(i).invoke(window, codepoint, mods);
        }
        if (lastCallback != null) {
            RenderThreadDispatcher.runLater((DispatchedRunnable) () -> {
                if (lastCallback != null) {
                    lastCallback.invoke(window, codepoint, mods);
                }
            });
        }
    }

    @FunctionalInterface
    public interface DispatchedRunnable extends Runnable {
    }
}
