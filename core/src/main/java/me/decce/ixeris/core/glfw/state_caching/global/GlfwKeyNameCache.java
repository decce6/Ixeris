package me.decce.ixeris.core.glfw.state_caching.global;

import it.unimi.dsi.fastutil.ints.Int2ReferenceMap;
import it.unimi.dsi.fastutil.ints.Int2ReferenceMaps;
import it.unimi.dsi.fastutil.ints.Int2ReferenceOpenHashMap;
import me.decce.ixeris.core.glfw.state_caching.util.KeyNameHelper;
import me.decce.ixeris.core.threading.MainThreadDispatcher;
import org.lwjgl.glfw.GLFW;

import java.util.concurrent.atomic.AtomicReferenceArray;

// The key names depend on the keyboard layout, but unfortunately GLFW currently does not have a keyboard layout
// callback (see https://github.com/glfw/glfw/pull/1696). Some mods call glfwGetKeyName quite frequently. We make the
// function return a cached value, if present, and at the next frame, on the main thread, actually call glfwGetKeyName,
// just in case the keyboard layout changed.
public class GlfwKeyNameCache extends GlfwGlobalCache {
    private final AtomicReferenceArray<String> keyToName;
    private final Int2ReferenceMap<String> scancodeToName;

    public GlfwKeyNameCache() {
        this.keyToName = new AtomicReferenceArray<>(GLFW.GLFW_KEY_LAST + 1);
        this.scancodeToName = Int2ReferenceMaps.synchronize(new Int2ReferenceOpenHashMap<>());
        this.enableCache();
    }

    public String get(int key, int scancode) {
        String ret;
        if (key == GLFW.GLFW_KEY_UNKNOWN) {
            ret = this.scancodeToName.get(scancode);
            if (ret == null) {
                ret = blockingGet(key, scancode);
                this.scancodeToName.put(scancode, ret);
            } else {
                this.updateLater(key, scancode);
            }
        }
        else {
            if (key < GLFW.GLFW_KEY_SPACE || key > GLFW.GLFW_KEY_LAST) {
                // Illegal. Let GLFW make an error.
                return blockingGet(key, scancode);
            }
            if (!KeyNameHelper.isPrintable(key)) {
                // Non-printable key: return null without emitting an error.
                return null;
            }
            ret = this.keyToName.get(key);
            if (ret == null) {
                ret = blockingGet(key, scancode);
                this.keyToName.set(key, ret);
            }
            else {
                this.updateLater(key, scancode);
            }
        }
        return ret;
    }

    private String blockingGet(int key, int scancode) {
        this.disableCache();
        var ret = GLFW.glfwGetKeyName(key, scancode);
        this.enableCache();
        return ret;
    }

    public void setKey(int key, String name) {
        this.keyToName.set(key, name);
    }

    public void setScancode(int scancode, String name) {
        this.scancodeToName.put(scancode, name);
    }

    // TODO: maybe limit update frequency?
    private void updateLater(int key, int scancode) {
        MainThreadDispatcher.runLater(() -> {
            this.disableCache();
            var name = GLFW.glfwGetKeyName(key, scancode);
            this.enableCache();
            if (key == GLFW.GLFW_KEY_UNKNOWN) {
                setKey(key, name);
            }
            else {
                setScancode(scancode, name);
            }
        });
    }
}
