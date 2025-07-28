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
import org.lwjgl.glfw.GLFWCharCallbackI;

import java.util.function.Consumer;

public class CharCallbackStack {
    private static final Long2ReferenceMap<CharCallbackStack> instance = Long2ReferenceMaps.synchronize(new Long2ReferenceArrayMap<>(1));

    private final LongArrayList stack = new LongArrayList();
    private final ReferenceArrayList<GLFWCharCallbackI> mainThreadCallbacks = new ReferenceArrayList<>(1);
    public GLFWCharCallbackI topCallback;

    private final long window;
    public volatile boolean suppressChecks;

    private CharCallbackStack(long window) {
        this.window = window;
    }

    public static CharCallbackStack get(long window) {
        return instance.computeIfAbsent(window, CharCallbackStack::new);
    }
    
    public static void forEach(Consumer<CharCallbackStack> consumer) {
        instance.values().forEach(consumer);
    }

    public synchronized void registerMainThreadCallback(GLFWCharCallbackI callback) {
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
        var current = GLFW.nglfwSetCharCallback(window, 0L);
        if (current != 0L && current != CommonCallbacks.charCallback.address()) {
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
            GLFW.nglfwSetCharCallback(window, stack.getLong(i));
        }
        GLFW.nglfwSetCharCallback(window, CommonCallbacks.charCallback.address());
        suppressChecks = false;
        return stack.size() > 1 ? stack.peekLong(1) : 0L;
    }

    public void onCallback(long window, int codepoint) {
        if (this.window != window) {
            return;
        }
        for (int i = 0; i < mainThreadCallbacks.size(); i++) {
            mainThreadCallbacks.get(i).invoke(window, codepoint);
        }
        if (topCallback != null) {
            RenderThreadDispatcher.runLater(() -> {
                if (topCallback != null) {
                    topCallback.invoke(window, codepoint);
                }
            });
        }
    }
}
