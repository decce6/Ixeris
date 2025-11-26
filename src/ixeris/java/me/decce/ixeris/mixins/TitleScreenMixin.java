package me.decce.ixeris.mixins;

import me.decce.ixeris.VersionCompatUtils;
import me.decce.ixeris.core.Ixeris;
import me.decce.ixeris.core.glfw.callback_dispatcher.CallbackDispatchers;
import me.decce.ixeris.core.threading.MainThreadDispatcher;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class TitleScreenMixin {
    @Inject(method = "init", at = @At("HEAD"))
    private void ixeris$init(CallbackInfo ci) {
        MainThreadDispatcher.runLater(() -> CallbackDispatchers.validateAll(VersionCompatUtils.getMinecraftWindow()));

        Ixeris.inEarlyDisplay = false;
    }
}
