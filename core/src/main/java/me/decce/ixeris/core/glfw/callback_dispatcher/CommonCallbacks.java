package me.decce.ixeris.core.glfw.callback_dispatcher;

import org.lwjgl.glfw.*;

public class CommonCallbacks {
    public static final GLFWCharCallback charCallback = GLFWCharCallback.create(CommonCallbacks::onCharCallback);
    public static final GLFWCharModsCallback charModsCallback = GLFWCharModsCallback.create(CommonCallbacks::onCharModsCallback);
    public static final GLFWCursorEnterCallback cursorEnterCallback = GLFWCursorEnterCallback.create(CommonCallbacks::onCursorEnterCallback);
    public static final GLFWCursorPosCallback cursorPosCallback = GLFWCursorPosCallback.create(CommonCallbacks::onCursorPosCallback);
    public static final GLFWDropCallback dropCallback = GLFWDropCallback.create(CommonCallbacks::onDropCallback);
    public static final GLFWErrorCallback errorCallback = GLFWErrorCallback.create(CommonCallbacks::onErrorCallback);
    public static final GLFWFramebufferSizeCallback framebufferSizeCallback = GLFWFramebufferSizeCallback.create(CommonCallbacks::onFramebufferSizeCallback);
    public static final GLFWKeyCallback keyCallback = GLFWKeyCallback.create(CommonCallbacks::onKeyCallback);
    public static final GLFWIMEStatusCallback iMEStatusCallback = GLFWIMEStatusCallback.create(CommonCallbacks::onImeStatusCallback);
    public static final GLFWMonitorCallback monitorCallback = GLFWMonitorCallback.create(CommonCallbacks::onMonitorCallback);
    public static final GLFWMouseButtonCallback mouseButtonCallback = GLFWMouseButtonCallback.create(CommonCallbacks::onMouseButtonCallback);
    public static final GLFWPreeditCallback preeditCallback = GLFWPreeditCallback.create(CommonCallbacks::onPreeditCallback);
    public static final GLFWPreeditCandidateCallback preeditCandidateCallback = GLFWPreeditCandidateCallback.create(CommonCallbacks::onPreeditCandidateCallback);
    public static final GLFWScrollCallback scrollCallback = GLFWScrollCallback.create(CommonCallbacks::onScrollCallback);
    public static final GLFWWindowCloseCallback windowCloseCallback = GLFWWindowCloseCallback.create(CommonCallbacks::onWindowCloseCallback);
    public static final GLFWWindowContentScaleCallback windowContentScaleCallback = GLFWWindowContentScaleCallback.create(CommonCallbacks::onWindowContentScaleCallback);
    public static final GLFWWindowFocusCallback windowFocusCallback = GLFWWindowFocusCallback.create(CommonCallbacks::onWindowFocusCallback);
    public static final GLFWWindowIconifyCallback windowIconifyCallback = GLFWWindowIconifyCallback.create(CommonCallbacks::onWindowIconifyCallback);
    public static final GLFWWindowMaximizeCallback windowMaximizeCallback = GLFWWindowMaximizeCallback.create(CommonCallbacks::onWindowMaximizeCallback);
    public static final GLFWWindowPosCallback windowPosCallback = GLFWWindowPosCallback.create(CommonCallbacks::onWindowPosCallback);
    public static final GLFWWindowRefreshCallback windowRefreshCallback = GLFWWindowRefreshCallback.create(CommonCallbacks::onWindowRefreshCallback);
    public static final GLFWWindowSizeCallback windowSizeCallback = GLFWWindowSizeCallback.create(CommonCallbacks::onWindowSizeCallback);

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
    
    private static void onPreeditCallback(long window, int preedit_count, long preedit_string, int block_count, long block_sizes, int focused_block, int caret) {
        PreeditCallbackDispatcher.get(window).onCallback(window, preedit_count, preedit_string, block_count, block_sizes, focused_block, caret);
    }

    private static void onPreeditCandidateCallback(long window, int candidates_count, int selected_index, int page_start, int page_size) {
        PreeditCandidateCallbackDispatcher.get(window).onCallback(window, candidates_count, selected_index, page_start, page_size);
    }

    private static void onImeStatusCallback(long window) {
        IMEStatusCallbackDispatcher.get(window).onCallback(window);
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
