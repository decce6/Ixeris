/*
WARNING: Auto-generated code
Do not edit directly
*/

package me.decce.ixeris.glfw.callback_stack;

import it.unimi.dsi.fastutil.longs.Long2ReferenceMap;
import it.unimi.dsi.fastutil.longs.Long2ReferenceMaps;
import it.unimi.dsi.fastutil.longs.Long2ReferenceOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.objects.ReferenceArrayList;
import me.decce.ixeris.threading.RenderThreadDispatcher;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWMouseButtonCallbackI;

import java.util.function.Consumer;

public class MouseButtonCallbackStack {
    private static final Long2ReferenceMap<MouseButtonCallbackStack> instance = Long2ReferenceMaps.synchronize(new Long2ReferenceOpenHashMap<>(1));

    private final LongArrayList stack = new LongArrayList();
    private final ReferenceArrayList<GLFWMouseButtonCallbackI> mainThreadCallbacks = new ReferenceArrayList<>(1);
    public GLFWMouseButtonCallbackI topCallback;

    private final long window;
    public volatile boolean suppressChecks;

    private MouseButtonCallbackStack(long window) {
        this.window = window;
    }

    public static MouseButtonCallbackStack get(long window) {
        return instance.computeIfAbsent(window, MouseButtonCallbackStack::new);
    }
    
    public static void forEach(Consumer<MouseButtonCallbackStack> consumer) {
        instance.values().forEach(consumer);
    }

    public synchronized void registerMainThreadCallback(GLFWMouseButtonCallbackI callback) {
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
        var current = GLFW.nglfwSetMouseButtonCallback(window, 0L);
        if (current != 0L && current != CommonCallbacks.mouseButtonCallback.address()) {
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
            GLFW.nglfwSetMouseButtonCallback(window, stack.getLong(i));
        }
        GLFW.nglfwSetMouseButtonCallback(window, CommonCallbacks.mouseButtonCallback.address());
        suppressChecks = false;
        return stack.size() > 1 ? stack.peekLong(1) : 0L;
    }

    public void onCallback(long window, int button, int action, int mods) {
        if (this.window != window) {
            return;
        }
        for (int i = 0; i < mainThreadCallbacks.size(); i++) {
            mainThreadCallbacks.get(i).invoke(window, button, action, mods);
        }
        if (topCallback != null) {
            RenderThreadDispatcher.runLater(() -> {
                if (topCallback != null) {
                    topCallback.invoke(window, button, action, mods);
                }
            });
        }
    }
}
