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
import org.lwjgl.glfw.GLFWKeyCallbackI;

import java.util.function.Consumer;

public class KeyCallbackStack {
    private static final Long2ReferenceMap<KeyCallbackStack> instance = Long2ReferenceMaps.synchronize(new Long2ReferenceArrayMap<>(1));

    private final LongArrayList stack = new LongArrayList();
    private final ReferenceArrayList<GLFWKeyCallbackI> mainThreadCallbacks = new ReferenceArrayList<>(1);
    public GLFWKeyCallbackI topCallback;

    private final long window;
    public volatile boolean suppressChecks;

    private KeyCallbackStack(long window) {
        this.window = window;
    }

    public static KeyCallbackStack get(long window) {
        return instance.computeIfAbsent(window, KeyCallbackStack::new);
    }
    
    public static void forEach(Consumer<KeyCallbackStack> consumer) {
        instance.values().forEach(consumer);
    }

    public synchronized void registerMainThreadCallback(GLFWKeyCallbackI callback) {
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
        var current = GLFW.nglfwSetKeyCallback(window, 0L);
        if (current != 0L && current != CommonCallbacks.keyCallback.address()) {
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
            GLFW.nglfwSetKeyCallback(window, stack.getLong(i));
        }
        GLFW.nglfwSetKeyCallback(window, CommonCallbacks.keyCallback.address());
        suppressChecks = false;
        return stack.size() > 1 ? stack.peekLong(1) : 0L;
    }

    public void onCallback(long window, int key, int scancode, int action, int mods) {
        if (this.window != window) {
            return;
        }
        for (int i = 0; i < mainThreadCallbacks.size(); i++) {
            mainThreadCallbacks.get(i).invoke(window, key, scancode, action, mods);
        }
        if (topCallback != null) {
            RenderThreadDispatcher.runLater(() -> {
                if (topCallback != null) {
                    topCallback.invoke(window, key, scancode, action, mods);
                }
            });
        }
    }
}
