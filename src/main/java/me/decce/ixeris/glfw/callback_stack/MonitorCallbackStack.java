/*
WARNING: Auto-generated code
Do not edit directly
*/

package me.decce.ixeris.glfw.callback_stack;

import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.objects.ReferenceArrayList;
import me.decce.ixeris.threading.RenderThreadDispatcher;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWMonitorCallbackI;

public class MonitorCallbackStack {
    private static final MonitorCallbackStack instance = new MonitorCallbackStack();

    private final LongArrayList stack = new LongArrayList();
    private final ReferenceArrayList<GLFWMonitorCallbackI> mainThreadCallbacks = new ReferenceArrayList<>(1);
    public GLFWMonitorCallbackI topCallback;

    public volatile boolean suppressChecks;

    private MonitorCallbackStack() {
    }

    public static MonitorCallbackStack get() {
        return instance;
    }

    public synchronized void registerMainThreadCallback(GLFWMonitorCallbackI callback) {
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
        var current = GLFW.nglfwSetMonitorCallback(0L);
        if (current != 0L && current != CommonCallbacks.monitorCallback.address()) {
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
            GLFW.nglfwSetMonitorCallback(stack.getLong(i));
        }
        GLFW.nglfwSetMonitorCallback(CommonCallbacks.monitorCallback.address());
        suppressChecks = false;
        return stack.size() > 1 ? stack.peekLong(1) : 0L;
    }

    public void onCallback(long monitor, int event) {
        for (int i = 0; i < mainThreadCallbacks.size(); i++) {
            mainThreadCallbacks.get(i).invoke(monitor, event);
        }
        if (topCallback != null) {
            RenderThreadDispatcher.runLater(() -> {
                if (topCallback != null) {
                    topCallback.invoke(monitor, event);
                }
            });
        }
    }
}
