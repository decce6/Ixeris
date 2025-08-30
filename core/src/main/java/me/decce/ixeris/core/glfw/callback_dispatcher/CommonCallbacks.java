package me.decce.ixeris.core.glfw.callback_dispatcher;

import org.lwjgl.glfw.GLFWCharCallback;
import org.lwjgl.glfw.GLFWCharModsCallback;
import org.lwjgl.glfw.GLFWCursorEnterCallback;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWDropCallback;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWFramebufferSizeCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMonitorCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWScrollCallback;
import org.lwjgl.glfw.GLFWWindowCloseCallback;
import org.lwjgl.glfw.GLFWWindowContentScaleCallback;
import org.lwjgl.glfw.GLFWWindowFocusCallback;
import org.lwjgl.glfw.GLFWWindowIconifyCallback;
import org.lwjgl.glfw.GLFWWindowMaximizeCallback;
import org.lwjgl.glfw.GLFWWindowPosCallback;
import org.lwjgl.glfw.GLFWWindowRefreshCallback;
import org.lwjgl.glfw.GLFWWindowSizeCallback;

public class CommonCallbacks {
    public static final GLFWCharCallback charCallback = GLFWCharCallback.create(CommonCallbacks::onCharCallback);
    public static final GLFWCharModsCallback charModsCallback = GLFWCharModsCallback.create(CommonCallbacks::onCharModsCallback);
    public static final GLFWCursorEnterCallback cursorEnterCallback = GLFWCursorEnterCallback.create(CommonCallbacks::onCursorEnterCallback);
    public static final GLFWCursorPosCallback cursorPosCallback = GLFWCursorPosCallback.create(CommonCallbacks::onCursorPosCallback);
    public static final GLFWDropCallback dropCallback = GLFWDropCallback.create(CommonCallbacks::onDropCallback);
    public static final GLFWErrorCallback errorCallback = GLFWErrorCallback.create(CommonCallbacks::onErrorCallback);
    public static final GLFWFramebufferSizeCallback framebufferSizeCallback = GLFWFramebufferSizeCallback.create(CommonCallbacks::onFramebufferSizeCallback);
    public static final GLFWKeyCallback keyCallback = GLFWKeyCallback.create(CommonCallbacks::onKeyCallback);
    public static final GLFWMonitorCallback monitorCallback = GLFWMonitorCallback.create(CommonCallbacks::onMonitorCallback);
    public static final GLFWMouseButtonCallback mouseButtonCallback = GLFWMouseButtonCallback.create(CommonCallbacks::onMouseButtonCallback);
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
