package me.decce.ixeris.glfw.callback_stack;

public class CallbackStacks {
    public static void updateAll(long window) {
        CharCallbackStack.get(window).update();
        CharModsCallbackStack.get(window).update();
        CursorEnterCallbackStack.get(window).update();
        CursorPosCallbackStack.get(window).update();
        DropCallbackStack.get(window).update();
        ErrorCallbackStack.get().update();
        FramebufferSizeCallbackStack.get(window).update();
        KeyCallbackStack.get(window).update();
        MonitorCallbackStack.get().update();
        MouseButtonCallbackStack.get(window).update();
        ScrollCallbackStack.get(window).update();
        WindowCloseCallbackStack.get(window).update();
        WindowContentScaleCallbackStack.get(window).update();
        WindowFocusCallbackStack.get(window).update();
        WindowIconifyCallbackStack.get(window).update();
        WindowMaximizeCallbackStack.get(window).update();
        WindowPosCallbackStack.get(window).update();
        WindowRefreshCallbackStack.get(window).update();
        WindowSizeCallbackStack.get(window).update();
    }
}
