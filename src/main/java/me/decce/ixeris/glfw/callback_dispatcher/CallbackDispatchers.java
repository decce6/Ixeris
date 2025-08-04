package me.decce.ixeris.glfw.callback_dispatcher;

public class CallbackDispatchers {
    public static void validateAll(long window) {
        CharCallbackDispatcher.get(window).validate();
        CharModsCallbackDispatcher.get(window).validate();
        CursorEnterCallbackDispatcher.get(window).validate();
        CursorPosCallbackDispatcher.get(window).validate();
        DropCallbackDispatcher.get(window).validate();
        ErrorCallbackDispatcher.get().validate();
        FramebufferSizeCallbackDispatcher.get(window).validate();
        KeyCallbackDispatcher.get(window).validate();
        MonitorCallbackDispatcher.get().validate();
        MouseButtonCallbackDispatcher.get(window).validate();
        ScrollCallbackDispatcher.get(window).validate();
        WindowCloseCallbackDispatcher.get(window).validate();
        WindowContentScaleCallbackDispatcher.get(window).validate();
        WindowFocusCallbackDispatcher.get(window).validate();
        WindowIconifyCallbackDispatcher.get(window).validate();
        WindowMaximizeCallbackDispatcher.get(window).validate();
        WindowPosCallbackDispatcher.get(window).validate();
        WindowRefreshCallbackDispatcher.get(window).validate();
        WindowSizeCallbackDispatcher.get(window).validate();
    }
}
