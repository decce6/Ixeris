package me.decce.ixeris.mixins.workarounds;

import com.mojang.blaze3d.platform.Window;
import me.decce.ixeris.workarounds.WindowMinimizedStateWorkaround;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Window.class)
public class WindowMixin {
    //? if >=1.21.4 {
    @Shadow private boolean minimized;

    /**
     * @author decce
     * @reason We write to the minimized field from the main thread. Volatile is needed to ensure visibility from the
     * render thread and other threads.
     * @see WindowMinimizedStateWorkaround
     */
    @Overwrite
    public boolean isMinimized() {
        if (WindowMinimizedStateWorkaround.minimizedVarHandle == null) {
            return minimized;
        }
        return (boolean) WindowMinimizedStateWorkaround.minimizedVarHandle.getVolatile((Window)(Object)this);
    }
    //? }
}
