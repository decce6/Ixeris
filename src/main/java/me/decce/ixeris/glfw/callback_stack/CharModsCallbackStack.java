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
import org.lwjgl.glfw.GLFWCharModsCallbackI;

public class CharModsCallbackStack {
    private static final Long2ReferenceMap<CharModsCallbackStack> instance = Long2ReferenceMaps.synchronize(new Long2ReferenceOpenHashMap<>(1));

    private final LongArrayList stack = new LongArrayList();
    private final ReferenceArrayList<GLFWCharModsCallbackI> mainThreadCallbacks = new ReferenceArrayList<>(1);
    public GLFWCharModsCallbackI topCallback;

    private final long window;
    public volatile boolean suppressChecks;

    private CharModsCallbackStack(long window) {
        this.window = window;
    }

    public static CharModsCallbackStack get(long window) {
        return instance.computeIfAbsent(window, CharModsCallbackStack::new);
    }

    public synchronized void registerMainThreadCallback(GLFWCharModsCallbackI callback) {
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
        var current = GLFW.nglfwSetCharModsCallback(window, 0L);
        if (current != 0L && current != CommonCallbacks.charModsCallback.address()) {
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
            GLFW.nglfwSetCharModsCallback(window, stack.get(i));
        }
        GLFW.nglfwSetCharModsCallback(window, CommonCallbacks.charModsCallback.address());
        suppressChecks = false;
        return stack.size() > 1 ? stack.peekLong(1) : 0L;
    }

    public void onCallback(long window, int codepoint, int mods) {
        if (this.window != window) {
            return;
        }
        for (int i = 0; i < mainThreadCallbacks.size(); i++) {
            mainThreadCallbacks.get(i).invoke(window, codepoint, mods);
        }
        if (topCallback != null) {
            RenderThreadDispatcher.runLater(() -> {
                if (topCallback != null) {
                    topCallback.invoke(window, codepoint, mods);
                }
            });
        }
    }
}
