# Used by generate.py.

import re

patches = [
    # Drop callback: clone method parameters
    (
        """
            RenderThreadDispatcher.runLater((DispatchedRunnable) () -> {
                if (lastCallback != null) {
                    lastCallback.invoke(window, count, names);
                }
            });
    """,
        """
            var namesCopy = MemoryHelper.deepCopy(names, count, GLFWDropCallback::getName);
            RenderThreadDispatcher.runLater((DispatchedRunnable) () -> {
                if (lastCallback != null) {
                    lastCallback.invoke(window, count, namesCopy);
                }
                MemoryHelper.free(namesCopy, count);
            });
    """,
        "me.decce.ixeris.util.MemoryHelper", "org.lwjgl.glfw.GLFWDropCallback"),
    # Error callback: clone method parameters
    (
        """
            RenderThreadDispatcher.runLater((DispatchedRunnable) () -> {
                if (lastCallback != null) {
                    lastCallback.invoke(error, description);
                }
            });
    """,
        """
            var descriptionCopy = MemoryHelper.deepCopy(description);
            RenderThreadDispatcher.runLater((DispatchedRunnable) () -> {
                if (lastCallback != null) {
                    lastCallback.invoke(error, descriptionCopy);
                }
                MemoryHelper.free(descriptionCopy);
            });
    """,
        "me.decce.ixeris.util.MemoryHelper")
]


def remove_window(callback_class: str):
    tmp = (re.sub(
        "private static final Long2ReferenceMap<(.+)> instance = (.+);",
        r"private static final \1 instance = new \1();",
        callback_class)
            .replace('    private final long window;\n','')
            .replace('get(long window) {', 'get() {')
            .replace('long window, ', '')
            .replace('window, ', '')
            .replace('(window)', '()')
            .replace("""(long window) {
        this.window = window;
    }""", "() {}")
            .replace("""
        if (this.window != window) {
            return;
        }""", ""))
    return re.sub("return instance.computeIfAbsent(.+);", 'return instance;', tmp)


def add_import(callback_class: str, to_import: list):
    return callback_class.replace("public class", '\n'.join(map(lambda x : "import " + x + ";", to_import))+"\n\npublic class")


def patch_class(callback_class: str, no_window: bool):
    for entry in patches:
        if len(entry) >= 3 and entry[0] in callback_class:
            callback_class = add_import(callback_class, entry[2:])
        callback_class = callback_class.replace(entry[0], entry[1])
        if no_window:
            callback_class = remove_window(callback_class)
    return callback_class
