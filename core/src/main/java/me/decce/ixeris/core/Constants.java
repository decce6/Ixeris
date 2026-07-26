package me.decce.ixeris.core;

import java.util.List;

public class Constants {
    private static final String MIXIN_PACKAGE = "me.decce.ixeris.core.mixins";
    private static final String FORGE_TRANSFORMER_PACKAGE = "me.decce.ixeris.forge.transformers";
    private static final boolean HAS_GLFW = true;
    private static final boolean HAS_SDL = true;

    private static final List<String> MIXINS = List.of(
            "glfw.GLFWMixin",
            "glfw.callback_dispatcher.GLFWMixin",
            "glfw.callback_dispatcher_334.GLFWMixin",
            "glfw.flexible_threading.GLFWMixin",
            "glfw.glfw_state_caching.GLFWMixin",
            "glfw.glfw_threading.GLFWMixin",
            "glfw.glfw_threading_330.GLFWMixin",
            "glfw.glfw_threading_334.GLFWMixin",
            "sdl.threading.SDLEventsMixin",
            "sdl.threading.SDLInitMixin",
            "sdl.threading.SDLKeyboardMixin",
            "sdl.threading.SDLMessageBoxMixin",
            "sdl.threading.SDLVideoMixin",
            "sdl.SDLInitMixin",
            "sdl.SDLEventsMixin"
    );

    public static List<String> getMixins() {
        return MIXINS;
    }

    public static Class<?>[] getMixinClasses() {
        return getMixins().stream()
                .filter(mixin -> (mixin.contains("glfw") && HAS_GLFW) || (mixin.contains("sdl") && HAS_SDL))
                .map(mixin -> MIXIN_PACKAGE + "." + mixin)
                .map(Constants::toClassUnchecked)
                .toArray(Class[]::new);
    }

    public static Class<?>[] getForgeTransformerClasses(ClassLoader classLoader) {
        return MIXINS.stream()
                .filter(mixin -> (mixin.contains("glfw") && HAS_GLFW) || (mixin.contains("sdl") && HAS_SDL))
                .map(mixin -> FORGE_TRANSFORMER_PACKAGE + "." + mixin.replace("Mixin", "Transformer"))
                .map(mixin -> toClassUnchecked(mixin, classLoader))
                .toArray(Class[]::new);
    }

    private static Class<?> toClassUnchecked(String name) {
        return toClassUnchecked(name, Constants.class.getClassLoader());
    }

    private static Class<?> toClassUnchecked(String name, ClassLoader classLoader) {
        try {
            return Class.forName(name, false, classLoader);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
