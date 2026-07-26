package me.decce.ixeris.core.glfw;

import me.decce.ixeris.core.EventHandler;
import me.decce.ixeris.core.Ixeris;
import me.decce.ixeris.core.util.PlatformHelper;

public class GlfwEventHandler implements EventHandler {
    private static final boolean IS_MACOS = PlatformHelper.isMacOs();

    @Override
    public boolean canPollEvents() {
        // Fix: On macOS, do not poll events until window creation, to prevent framebuffer size inconsistencies with
        //  GLFW_COCOA_RETINA_FRAMEBUFFER = GLFW_FALSE.
        // See https://github.com/decce6/Ixeris/issues/40 and https://github.com/glfw/glfw/issues/1968
        return Ixeris.glfwInitialized && (!IS_MACOS || Ixeris.accessor.isMinecraftWindowCreated());
    }

    @Override
    public void pollEvents() {
        if (Ixeris.glfwInitialized) {
            Ixeris.input().pollEvents();
        }
    }
}
