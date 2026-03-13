package me.decce.ixeris.core.input;

import me.decce.ixeris.core.Ixeris;
import org.lwjgl.glfw.GLFW;

public class InputManager {
    private boolean rawInputEnabled; // Determined by the vanilla setting for raw mouse input
    private RawInputHandler rawInput;
    private long glfwWindow;

    public void setup(long window) {
        if (window == 0L) {
            throw new IllegalArgumentException("window cannot be null");
        }

        this.glfwWindow = window;
        this.rawInput = RawInputHandler.create(window);
    }

    private boolean buffered() {
        return this.rawInputEnabled && this.rawInput != null;
    }

    public void pollEvents() {
        if (buffered()) {
            rawInput.pollEvents();
        }
        else {
            GLFW.glfwPollEvents();
        }
    }

    public void grab(long window) {
        if (!buffered() || window != this.glfwWindow) {
            return;
        }
        rawInput.grab();
    }

    public void release(long window) {
        if (!buffered() || window != this.glfwWindow) {
            return;
        }
        rawInput.release();
    }

    public boolean isRawInputEnabled() {
        return this.rawInputEnabled;
    }

    public void setRawInput(boolean enabled) {
        Ixeris.LOGGER.info("setRawInput {}", enabled);
        this.rawInputEnabled = enabled;
        if (this.rawInput != null && !enabled) {
            // Unregister raw input devices
            this.rawInput.release();
        }
    }
}
