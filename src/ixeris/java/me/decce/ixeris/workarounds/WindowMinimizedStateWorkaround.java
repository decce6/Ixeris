package me.decce.ixeris.workarounds;

import com.mojang.blaze3d.platform.Window;
import me.decce.ixeris.core.glfw.callback_dispatcher.FramebufferSizeCallbackDispatcher;
import me.decce.ixeris.core.util.PlatformHelper;
import me.decce.ixeris.mixins.workarounds.WindowAccessor;
import net.minecraft.client.Minecraft;

public class WindowMinimizedStateWorkaround {
    public static void init() {
        //? if >=1.21.4 {
        if (PlatformHelper.isWindows()) {
            long window = Minecraft.getInstance().getWindow().getWindow();
            FramebufferSizeCallbackDispatcher.get(window).registerMainThreadCallback(WindowMinimizedStateWorkaround::onFramebufferSizeCallback);
        }
        //?}
    }

    //? if >=1.21.4 {
    private static void onFramebufferSizeCallback(long window, int width, int height) {
        Window minecraftWindow = Minecraft.getInstance().getWindow();
        if (window == minecraftWindow.getWindow()) {
            /*
             * Minecraft uses the minimized field in the Window to decide whether to blit the framebuffer to screen.
             * This seems to be a workaround for an Intel driver bug where glBlitFramebuffer crashes when the window is
             * minimized. The minimized field is updated by the GLFW framebuffer size callback.
             *
             * Because we queue callbacks and execute them later on the render thread, it so happens the callbacks are
             * executed *after* the game checks the minimized state of the window. Thus, we update the minimized state
             * manually here, to make sure the game can get the actual state of the window.
             *
             * This is not a complete fix for the vanilla bug, but should make the crash less frequent than vanilla.
             */
            ((WindowAccessor)(Object)minecraftWindow).setMinimized(width == 0 || height == 0);
        }
    }
    //?}
}
