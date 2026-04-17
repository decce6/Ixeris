package me.decce.ixeris.core.input;

import org.lwjgl.glfw.GLFW;

public class InputManager {
    private boolean rawInputEnabled; // Determined by the vanilla setting for raw mouse input
    private RawInputHandler rawInput;
    private long glfwWindow;

    public void setup(long window) {
        // Note: this method is called from the render thread

        if (window == 0L) {
            throw new IllegalArgumentException("window cannot be null");
        }

        this.glfwWindow = window;
        this.rawInput = RawInputHandler.create(window);
    }

    private boolean buffered() {
        return this.rawInputEnabled && this.rawInput != null && this.rawInput.supported();
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

    public boolean shouldHijackSettingRawInput() {
        return this.rawInput == null || this.rawInput.supported();
    }

    public void setRawInput(boolean enabled) {
        this.rawInputEnabled = enabled;
        if (this.rawInput != null && !enabled) {
            // Unregister raw input devices
            this.rawInput.release();
        }
    }
}
