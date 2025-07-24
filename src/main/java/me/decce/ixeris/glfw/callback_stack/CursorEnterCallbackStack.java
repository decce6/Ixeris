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
import org.lwjgl.glfw.GLFWCursorEnterCallbackI;

public class CursorEnterCallbackStack {
    private static final Long2ReferenceMap<CursorEnterCallbackStack> instance = Long2ReferenceMaps.synchronize(new Long2ReferenceOpenHashMap<>(1));

    private final LongArrayList stack = new LongArrayList();
    private final ReferenceArrayList<GLFWCursorEnterCallbackI> mainThreadCallbacks = new ReferenceArrayList<>(1);
    public GLFWCursorEnterCallbackI topCallback;

    private final long window;
    public volatile boolean suppressChecks;

    private CursorEnterCallbackStack(long window) {
        this.window = window;
    }

    public static CursorEnterCallbackStack get(long window) {
        return instance.computeIfAbsent(window, CursorEnterCallbackStack::new);
    }

    public synchronized void registerMainThreadCallback(GLFWCursorEnterCallbackI callback) {
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
        var current = GLFW.nglfwSetCursorEnterCallback(window, 0L);
        if (current != 0L && current != CommonCallbacks.cursorEnterCallback.address()) {
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
            GLFW.nglfwSetCursorEnterCallback(window, stack.get(i));
        }
        GLFW.nglfwSetCursorEnterCallback(window, CommonCallbacks.cursorEnterCallback.address());
        suppressChecks = false;
        return stack.size() > 1 ? stack.peekLong(1) : 0L;
    }

    public void onCallback(long window, boolean entered) {
        if (this.window != window) {
            return;
        }
        for (int i = 0; i < mainThreadCallbacks.size(); i++) {
            mainThreadCallbacks.get(i).invoke(window, entered);
        }
        if (topCallback != null) {
            RenderThreadDispatcher.runLater(() -> {
                if (topCallback != null) {
                    topCallback.invoke(window, entered);
                }
            });
        }
    }
}
