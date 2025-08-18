package me.decce.ixeris.workarounds;

import com.mojang.blaze3d.platform.Window;
import me.decce.ixeris.Ixeris;
import me.decce.ixeris.glfw.callback_dispatcher.FramebufferSizeCallbackDispatcher;
import me.decce.ixeris.util.MappingTranslator;
import me.decce.ixeris.util.PlatformHelper;
import net.minecraft.client.Minecraft;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;

public class WindowMinimizedStateWorkaround {
    //? if >=1.21.4 {
    public static VarHandle minimizedVarHandle;

    static {
        try {
            var clazz = Window.class;
            var minimizedFieldName = MappingTranslator.translateField(clazz, "field_55579", "Z");
            minimizedVarHandle = MethodHandles
                    .privateLookupIn(Window.class, MethodHandles.lookup())
                    .findVarHandle(Window.class, minimizedFieldName, boolean.class);
        } catch (Throwable throwable) {
            Ixeris.LOGGER.error("Failed to find minimized field in Window!", throwable);
        }
    }
    //? }

    public static void init() {
        //? if >=1.21.4 {
        if (PlatformHelper.isWindows()) {
            long window = Minecraft.getInstance().getWindow().getWindow();
            FramebufferSizeCallbackDispatcher.get(window).registerMainThreadCallback(WindowMinimizedStateWorkaround::onFramebufferSizeCallback);
        }
        //? }
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
             * Volatile needed because the field is written to on the main thread and read from the render thread.
             */
            if (minimizedVarHandle != null) {
                minimizedVarHandle.setVolatile(minecraftWindow, width == 0 || height == 0);
            }
        }
    }
    //? }
}
