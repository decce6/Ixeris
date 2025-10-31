# Simple script to make changes to generated classes that need special care.
# Only used by generate.py. You should not run this directly.

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
        "me.decce.ixeris.core.util.MemoryHelper", "org.lwjgl.glfw.GLFWDropCallback"),
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
        "me.decce.ixeris.core.util.MemoryHelper")
]


def remove_window(callback_class: str):
    tmp = ((re.sub(
        "private static final Long2ReferenceMap<(.+)> instance = (.+);",
        r"private static \1 instance;",
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
        if (window == Ixeris.accessor.getMinecraftWindow()) {
            return instance;
        }""", "")
            .replace("""
        if (this.window != window) {
            return;
        }""", ""))
            .replace("if (!instance.containsKey()) {", "if (instance == null) {"))
    tmp = re.sub("instance[.]put[(]new (.+)[(][)][)]",
                 r"instance = new \1()", tmp)
    tmp = tmp.replace("instance.get().validate", "instance.validate")
    return re.sub("return instance.get(.+);", 'return instance;', tmp)


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
