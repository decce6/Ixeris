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
import org.lwjgl.glfw.GLFWWindowPosCallbackI;

public class WindowPosCallbackStack {
    private static final Long2ReferenceMap<WindowPosCallbackStack> instance = Long2ReferenceMaps.synchronize(new Long2ReferenceArrayMap<>(1));

    private final LongArrayList stack = new LongArrayList();
    private final ReferenceArrayList<GLFWWindowPosCallbackI> mainThreadCallbacks = new ReferenceArrayList<>(1);
    public GLFWWindowPosCallbackI topCallback;

    private final long window;
    public volatile boolean suppressChecks;

    private WindowPosCallbackStack(long window) {
        this.window = window;
    }

    public static WindowPosCallbackStack get(long window) {
        return instance.computeIfAbsent(window, WindowPosCallbackStack::new);
    }

    public synchronized void registerMainThreadCallback(GLFWWindowPosCallbackI callback) {
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
        var current = GLFW.nglfwSetWindowPosCallback(window, 0L);
        if (current != 0L && current != CommonCallbacks.windowPosCallback.address()) {
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
            GLFW.nglfwSetWindowPosCallback(window, stack.getLong(i));
        }
        GLFW.nglfwSetWindowPosCallback(window, CommonCallbacks.windowPosCallback.address());
        suppressChecks = false;
        return stack.size() > 1 ? stack.peekLong(1) : 0L;
    }

    public void onCallback(long window, int xpos, int ypos) {
        if (this.window != window) {
            return;
        }
        for (int i = 0; i < mainThreadCallbacks.size(); i++) {
            mainThreadCallbacks.get(i).invoke(window, xpos, ypos);
        }
        if (topCallback != null) {
            RenderThreadDispatcher.runLater(() -> {
                if (topCallback != null) {
                    topCallback.invoke(window, xpos, ypos);
                }
            });
        }
    }
}
