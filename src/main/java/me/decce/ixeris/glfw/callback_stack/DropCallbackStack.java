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
import me.decce.ixeris.util.MemoryHelper;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWDropCallback;
import org.lwjgl.glfw.GLFWDropCallbackI;

public class DropCallbackStack {
    private static final Long2ReferenceMap<DropCallbackStack> instance = Long2ReferenceMaps.synchronize(new Long2ReferenceArrayMap<>(1));

    private final LongArrayList stack = new LongArrayList();
    private final ReferenceArrayList<GLFWDropCallbackI> mainThreadCallbacks = new ReferenceArrayList<>(1);
    public GLFWDropCallbackI topCallback;

    private final long window;
    public volatile boolean suppressChecks;

    private DropCallbackStack(long window) {
        this.window = window;
    }

    public static DropCallbackStack get(long window) {
        return instance.computeIfAbsent(window, DropCallbackStack::new);
    }

    public synchronized void registerMainThreadCallback(GLFWDropCallbackI callback) {
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
        var current = GLFW.nglfwSetDropCallback(window, 0L);
        if (current != 0L && current == CommonCallbacks.dropCallback.address()) {
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
            GLFW.nglfwSetDropCallback(window, stack.get(i));
        }
        GLFW.nglfwSetDropCallback(window, CommonCallbacks.dropCallback.address());
        suppressChecks = false;
        return stack.size() > 1 ? stack.peekLong(1) : 0L;
    }

    public void onCallback(long window, int count, long names) {
        if (this.window != window) {
            return;
        }
        for (int i = 0; i < mainThreadCallbacks.size(); i++) {
            mainThreadCallbacks.get(i).invoke(window, count, names);
        }
        if (topCallback != null) {
            var namesCopy = MemoryHelper.deepCopy(names, count, GLFWDropCallback::getName);
            RenderThreadDispatcher.runLater(() -> {
                if (topCallback != null) {
                    topCallback.invoke(window, count, namesCopy);
                }
                MemoryHelper.free(namesCopy, count);
            });
        }
    }
}
