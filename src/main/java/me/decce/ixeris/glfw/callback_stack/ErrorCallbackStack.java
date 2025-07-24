/*
WARNING: Auto-generated code
Do not edit directly
*/

package me.decce.ixeris.glfw.callback_stack;

import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.objects.ReferenceArrayList;
import me.decce.ixeris.threading.RenderThreadDispatcher;
import me.decce.ixeris.util.MemoryHelper;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallbackI;

public class ErrorCallbackStack {
    private static final ErrorCallbackStack instance = new ErrorCallbackStack();

    private final LongArrayList stack = new LongArrayList();
    private final ReferenceArrayList<GLFWErrorCallbackI> mainThreadCallbacks = new ReferenceArrayList<>(1);
    public GLFWErrorCallbackI topCallback;

    public volatile boolean suppressChecks;

    private ErrorCallbackStack() {
    }

    public static ErrorCallbackStack get() {
        return instance;
    }

    public synchronized void registerMainThreadCallback(GLFWErrorCallbackI callback) {
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
        var current = GLFW.nglfwSetErrorCallback(0L);
        if (current != 0L && current == CommonCallbacks.errorCallback.address()) {
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
            GLFW.nglfwSetErrorCallback(stack.getLong(i));
        }
        GLFW.nglfwSetErrorCallback(CommonCallbacks.errorCallback.address());
        suppressChecks = false;
        return stack.size() > 1 ? stack.peekLong(1) : 0L;
    }

    public void onCallback(int error, long description) {
        for (int i = 0; i < mainThreadCallbacks.size(); i++) {
            mainThreadCallbacks.get(i).invoke(error, description);
        }
        if (topCallback != null) {
            var descriptionCopy = MemoryHelper.deepCopy(description);
            RenderThreadDispatcher.runLater(() -> {
                if (topCallback != null) {
                    topCallback.invoke(error, descriptionCopy);
                }
                MemoryHelper.free(descriptionCopy);
            });
        }
    }
}
