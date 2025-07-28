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
import org.lwjgl.glfw.GLFWWindowCloseCallbackI;

import java.util.function.Consumer;

public class WindowCloseCallbackStack {
    private static final Long2ReferenceMap<WindowCloseCallbackStack> instance = Long2ReferenceMaps.synchronize(new Long2ReferenceArrayMap<>(1));

    private final LongArrayList stack = new LongArrayList();
    private final ReferenceArrayList<GLFWWindowCloseCallbackI> mainThreadCallbacks = new ReferenceArrayList<>(1);
    public GLFWWindowCloseCallbackI topCallback;

    private final long window;
    public volatile boolean suppressChecks;

    private WindowCloseCallbackStack(long window) {
        this.window = window;
    }

    public static WindowCloseCallbackStack get(long window) {
        return instance.computeIfAbsent(window, WindowCloseCallbackStack::new);
    }
    
    public static void forEach(Consumer<WindowCloseCallbackStack> consumer) {
        instance.values().forEach(consumer);
    }

    public synchronized void registerMainThreadCallback(GLFWWindowCloseCallbackI callback) {
        mainThreadCallbacks.add(callback);
    }

    public synchronized void push(long address) {
        stack.push(address);
    }

    public synchronized void clear() {
        stack.clear();
    }

    public synchronized void invalidate(long address) {
        stack.replaceAll(original -> original == address ? 0L : original);
    }

    public synchronized long update() {
        suppressChecks = true;
        var current = GLFW.nglfwSetWindowCloseCallback(window, 0L);
        if (current != 0L && current != CommonCallbacks.windowCloseCallback.address()) {
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
            GLFW.nglfwSetWindowCloseCallback(window, stack.getLong(i));
        }
        GLFW.nglfwSetWindowCloseCallback(window, CommonCallbacks.windowCloseCallback.address());
        suppressChecks = false;
        return stack.size() > 1 ? stack.peekLong(1) : 0L;
    }

    public void onCallback(long window) {
        if (this.window != window) {
            return;
        }
        for (int i = 0; i < mainThreadCallbacks.size(); i++) {
            mainThreadCallbacks.get(i).invoke(window);
        }
        if (topCallback != null) {
            RenderThreadDispatcher.runLater(() -> {
                if (topCallback != null) {
                    topCallback.invoke(window);
                }
            });
        }
    }
}
