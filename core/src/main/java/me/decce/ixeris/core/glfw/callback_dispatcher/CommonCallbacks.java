package me.decce.ixeris.core.glfw.callback_dispatcher;

import org.lwjgl.glfw.*;

public class CommonCallbacks {
    public static GLFWCharCallback charCallback;
    public static GLFWCharModsCallback charModsCallback;
    public static GLFWCursorEnterCallback cursorEnterCallback;
    public static GLFWCursorPosCallback cursorPosCallback;
    public static GLFWDropCallback dropCallback;
    public static GLFWErrorCallback errorCallback;
    public static GLFWFramebufferSizeCallback framebufferSizeCallback;
    public static GLFWKeyCallback keyCallback;
    public static GLFWMonitorCallback monitorCallback;
    public static GLFWMouseButtonCallback mouseButtonCallback;
    public static GLFWScrollCallback scrollCallback;
    public static GLFWWindowCloseCallback windowCloseCallback;
    public static GLFWWindowContentScaleCallback windowContentScaleCallback;
    public static GLFWWindowFocusCallback windowFocusCallback;
    public static GLFWWindowIconifyCallback windowIconifyCallback;
    public static GLFWWindowMaximizeCallback windowMaximizeCallback;
    public static GLFWWindowPosCallback windowPosCallback;
    public static GLFWWindowRefreshCallback windowRefreshCallback;
    public static GLFWWindowSizeCallback windowSizeCallback;

    static {
        initCallbacks();
    }

    private static void initCallbacks() {
        charCallback = GLFWCharCallback.create(CommonCallbacks::onCharCallback);
        charModsCallback = GLFWCharModsCallback.create(CommonCallbacks::onCharModsCallback);
        cursorEnterCallback = GLFWCursorEnterCallback.create(CommonCallbacks::onCursorEnterCallback);
        cursorPosCallback = GLFWCursorPosCallback.create(CommonCallbacks::onCursorPosCallback);
        dropCallback = GLFWDropCallback.create(CommonCallbacks::onDropCallback);
        errorCallback = GLFWErrorCallback.create(CommonCallbacks::onErrorCallback);
        framebufferSizeCallback = GLFWFramebufferSizeCallback.create(CommonCallbacks::onFramebufferSizeCallback);
        keyCallback = GLFWKeyCallback.create(CommonCallbacks::onKeyCallback);
        monitorCallback = GLFWMonitorCallback.create(CommonCallbacks::onMonitorCallback);
        mouseButtonCallback = GLFWMouseButtonCallback.create(CommonCallbacks::onMouseButtonCallback);
        scrollCallback = GLFWScrollCallback.create(CommonCallbacks::onScrollCallback);
        windowCloseCallback = GLFWWindowCloseCallback.create(CommonCallbacks::onWindowCloseCallback);
        windowContentScaleCallback = GLFWWindowContentScaleCallback.create(CommonCallbacks::onWindowContentScaleCallback);
        windowFocusCallback = GLFWWindowFocusCallback.create(CommonCallbacks::onWindowFocusCallback);
        windowIconifyCallback = GLFWWindowIconifyCallback.create(CommonCallbacks::onWindowIconifyCallback);
        windowMaximizeCallback = GLFWWindowMaximizeCallback.create(CommonCallbacks::onWindowMaximizeCallback);
        windowPosCallback = GLFWWindowPosCallback.create(CommonCallbacks::onWindowPosCallback);
        windowRefreshCallback = GLFWWindowRefreshCallback.create(CommonCallbacks::onWindowRefreshCallback);
        windowSizeCallback = GLFWWindowSizeCallback.create(CommonCallbacks::onWindowSizeCallback);
    }

    private static void onCharCallback(long window, int codepoint) {
        CharCallbackDispatcher.get(window).onCallback(window, codepoint);
    }
    
    private static void onCharModsCallback(long window, int codepoint, int mods) {
        CharModsCallbackDispatcher.get(window).onCallback(window, codepoint, mods);
    }
    
    private static void onCursorEnterCallback(long window, boolean entered) {
        CursorEnterCallbackDispatcher.get(window).onCallback(window, entered);
    }
    
    private static void onCursorPosCallback(long window, double xpos, double ypos) {
        CursorPosCallbackDispatcher.get(window).onCallback(window, xpos, ypos);
    }
    
    private static void onDropCallback(long window, int count, long names) {
        DropCallbackDispatcher.get(window).onCallback(window, count, names);
    }
    
    private static void onErrorCallback(int error, long description) {
        ErrorCallbackDispatcher.get().onCallback(error, description);
    }
    
    private static void onFramebufferSizeCallback(long window, int width, int height) {
        FramebufferSizeCallbackDispatcher.get(window).onCallback(window, width, height);
    }
    
    private static void onKeyCallback(long window, int key, int scancode, int action, int mods) {
        KeyCallbackDispatcher.get(window).onCallback(window, key, scancode, action, mods);
    }
    
    private static void onMonitorCallback(long monitor, int event) {
        MonitorCallbackDispatcher.get().onCallback(monitor, event);
    }
    
    private static void onMouseButtonCallback(long window, int button, int action, int mods) {
        MouseButtonCallbackDispatcher.get(window).onCallback(window, button, action, mods);
    }

    private static void onScrollCallback(long window, double xoffset, double yoffset) {
        ScrollCallbackDispatcher.get(window).onCallback(window, xoffset, yoffset);
    }

    private static void onWindowCloseCallback(long window) {
        WindowCloseCallbackDispatcher.get(window).onCallback(window);
    }
    
    private static void onWindowContentScaleCallback(long window, float xscale, float yscale) {
        WindowContentScaleCallbackDispatcher.get(window).onCallback(window, xscale, yscale);
    }
    
    private static void onWindowFocusCallback(long window, boolean focused) {
        WindowFocusCallbackDispatcher.get(window).onCallback(window, focused);
    }
    
    private static void onWindowIconifyCallback(long window, boolean iconified) {
        WindowIconifyCallbackDispatcher.get(window).onCallback(window, iconified);
    }
    
    private static void onWindowMaximizeCallback(long window, boolean maximized) {
        WindowMaximizeCallbackDispatcher.get(window).onCallback(window, maximized);
    }
    
    private static void onWindowPosCallback(long window, int xpos, int ypos) {
        WindowPosCallbackDispatcher.get(window).onCallback(window, xpos, ypos);
    }
    
    private static void onWindowRefreshCallback(long window) {
        WindowRefreshCallbackDispatcher.get(window).onCallback(window);
    }
    
    private static void onWindowSizeCallback(long window, int width, int height) {
        WindowSizeCallbackDispatcher.get(window).onCallback(window, width, height);
    }
    
}
