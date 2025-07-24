/*
WARNING: Auto-generated code
Do not edit directly
*/

package me.decce.ixeris.glfw.callback_stack;

import it.unimi.dsi.fastutil.longs.Long2ReferenceMap;
import it.unimi.dsi.fastutil.longs.Long2ReferenceMaps;
import it.unimi.dsi.fastutil.longs.Long2ReferenceArrayMap;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.objects.ReferenceArrayList;
import me.decce.ixeris.threading.RenderThreadDispatcher;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWWindowMaximizeCallbackI;

public class WindowMaximizeCallbackStack {
    private static final Long2ReferenceMap<WindowMaximizeCallbackStack> instance = Long2ReferenceMaps.synchronize(new Long2ReferenceArrayMap<>(1));

    private final LongArrayList stack = new LongArrayList();
    private final ReferenceArrayList<GLFWWindowMaximizeCallbackI> mainThreadCallbacks = new ReferenceArrayList<>(1);
    public GLFWWindowMaximizeCallbackI topCallback;

    private final long window;
    public volatile boolean suppressChecks;

    private WindowMaximizeCallbackStack(long window) {
        this.window = window;
    }

    public static WindowMaximizeCallbackStack get(long window) {
        return instance.computeIfAbsent(window, WindowMaximizeCallbackStack::new);
    }

    public synchronized void registerMainThreadCallback(GLFWWindowMaximizeCallbackI callback) {
        mainThreadCallbacks.add(callback);
    }

    public synchronized void push(long address) {
        stack.push(address);
    }

    public synchronized void clear() {
        stack.clear();
    }

    public synchronized long update() {
        suppressChecks = true;
        var current = GLFW.nglfwSetWindowMaximizeCallback(window, 0L);
        if (current != 0L && current != CommonCallbacks.windowMaximizeCallback.address()) {
            if (stack.isEmpty() || current != stack.topLong()) {
                // This only happens when mods register callbacks without using LWJGL (e.x. directly in native code)
                stack.push(current);
            }
        }
        if (stack.isEmpty()) {
            suppressChecks = false;
            return 0L;
        }
        for (int i = 0; i < stack.size(); i++) {
            GLFW.nglfwSetWindowMaximizeCallback(window, stack.getLong(i));
        }
        GLFW.nglfwSetWindowMaximizeCallback(window, CommonCallbacks.windowMaximizeCallback.address());
        suppressChecks = false;
        return stack.size() > 1 ? stack.peekLong(1) : 0L;
    }

    public void onCallback(long window, boolean maximized) {
        if (this.window != window) {
            return;
        }
        for (int i = 0; i < mainThreadCallbacks.size(); i++) {
            mainThreadCallbacks.get(i).invoke(window, maximized);
        }
        if (topCallback != null) {
            RenderThreadDispatcher.runLater(() -> {
                if (topCallback != null) {
                    topCallback.invoke(window, maximized);
                }
            });
        }
    }
}
