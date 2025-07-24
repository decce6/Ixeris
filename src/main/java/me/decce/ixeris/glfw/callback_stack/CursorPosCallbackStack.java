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
import org.lwjgl.glfw.GLFWCursorPosCallbackI;

public class CursorPosCallbackStack {
    private static final Long2ReferenceMap<CursorPosCallbackStack> instance = Long2ReferenceMaps.synchronize(new Long2ReferenceArrayMap<>(1));

    private final LongArrayList stack = new LongArrayList();
    private final ReferenceArrayList<GLFWCursorPosCallbackI> mainThreadCallbacks = new ReferenceArrayList<>(1);
    public GLFWCursorPosCallbackI topCallback;

    private final long window;
    public volatile boolean suppressChecks;

    private CursorPosCallbackStack(long window) {
        this.window = window;
    }

    public static CursorPosCallbackStack get(long window) {
        return instance.computeIfAbsent(window, CursorPosCallbackStack::new);
    }

    public synchronized void registerMainThreadCallback(GLFWCursorPosCallbackI callback) {
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
        var current = GLFW.nglfwSetCursorPosCallback(window, 0L);
        if (current != 0L && current == CommonCallbacks.cursorPosCallback.address()) {
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
            GLFW.nglfwSetCursorPosCallback(window, stack.getLong(i));
        }
        GLFW.nglfwSetCursorPosCallback(window, CommonCallbacks.cursorPosCallback.address());
        suppressChecks = false;
        return stack.size() > 1 ? stack.peekLong(1) : 0L;
    }

    public void onCallback(long window, double xpos, double ypos) {
        if (this.window != window) {
            return;
        }
        for (int i = 0; i < mainThreadCallbacks.size(); i++) {
            mainThreadCallbacks.get(i).invoke(window, xpos, ypos);
        }
        if (topCallback != null) {
            RenderThreadDispatcher.runLater((CursorPosRunnable)() -> {
                if (topCallback != null) {
                    topCallback.invoke(window, xpos, ypos);
                }
            });
        }
    }

    @FunctionalInterface
    public interface CursorPosRunnable extends Runnable {
    }
}
