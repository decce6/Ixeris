package me.decce.ixeris.core.input;

import org.lwjgl.glfw.GLFW;

public class InputManager {
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
        return this.rawInput != null;
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
}
