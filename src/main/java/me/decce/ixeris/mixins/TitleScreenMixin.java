package me.decce.ixeris.mixins;

import me.decce.ixeris.glfw.callback_dispatcher.CallbackDispatchers;
import me.decce.ixeris.threading.MainThreadDispatcher;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class TitleScreenMixin {
    // This is only needed when mods register GLFW callbacks in native code (i.e. without using LWJGL)
    // See: https://github.com/decce6/Ixeris/issues/14
    @Inject(method = "init", at = @At("HEAD"))
    private void ixeris$init(CallbackInfo ci) {
        MainThreadDispatcher.runLater(() -> CallbackDispatchers.validateAll(Minecraft.getInstance().getWindow().getWindow()));
    }
}
