package me.decce.ixeris.core;

// Used to print the stacktrace when mods call glfwPollEvents, glfwWaitEvents, or glfwWaitEventsTimeout, if logPollingCalls is enabled in the config
public class PollingException extends Exception {
    public PollingException() {
        super("One of the GLFW event polling functions has been called on non-main thread.");
    }
}
