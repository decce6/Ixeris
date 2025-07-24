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

    public static void invalidate(long address) {
        CharCallbackStack.forEach(stack -> stack.invalidate(address));
        CharModsCallbackStack.forEach(stack -> stack.invalidate(address));
        CursorEnterCallbackStack.forEach(stack -> stack.invalidate(address));
        CursorPosCallbackStack.forEach(stack -> stack.invalidate(address));
        DropCallbackStack.forEach(stack -> stack.invalidate(address));
        ErrorCallbackStack.get().invalidate(address);
        FramebufferSizeCallbackStack.forEach(stack -> stack.invalidate(address));
        KeyCallbackStack.forEach(stack -> stack.invalidate(address));
        MonitorCallbackStack.get().invalidate(address);
        MouseButtonCallbackStack.forEach(stack -> stack.invalidate(address));
        ScrollCallbackStack.forEach(stack -> stack.invalidate(address));
        WindowCloseCallbackStack.forEach(stack -> stack.invalidate(address));
        WindowContentScaleCallbackStack.forEach(stack -> stack.invalidate(address));
        WindowFocusCallbackStack.forEach(stack -> stack.invalidate(address));
        WindowIconifyCallbackStack.forEach(stack -> stack.invalidate(address));
        WindowMaximizeCallbackStack.forEach(stack -> stack.invalidate(address));
        WindowPosCallbackStack.forEach(stack -> stack.invalidate(address));
        WindowRefreshCallbackStack.forEach(stack -> stack.invalidate(address));
        WindowSizeCallbackStack.forEach(stack -> stack.invalidate(address));
    }
}
