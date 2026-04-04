package me.decce.ixeris.core.glfw.callback_dispatcher._334;

public class CallbackDispatchers_334 {
    public static void validateAll(long window) {
        IMEStatusCallbackDispatcher.get(window).validate();
        PreeditCallbackDispatcher.get(window).validate();
        PreeditCandidateCallbackDispatcher.get(window).validate();
    }
}
