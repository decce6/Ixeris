package me.decce.ixeris.mixins.compat.caramelchat;

import me.decce.ixeris.compat.caramelchat.CaramelChatCompat;
import me.decce.ixeris.core.threading.MainThreadDispatcher;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Pseudo
@Mixin(targets = "moe.caramel.chat.driver.arch.win.WinController", remap = false)
public abstract class CaramelChatWinControllerMixin {
    @Inject(method = "setFocus(Z)V", at = @At("HEAD"), cancellable = true, require = 0, remap = false)
    private void ixeris$setFocusOnWindowThread(boolean focused, CallbackInfo ci) {
        if (!MainThreadDispatcher.isOnThread()) {
            ci.cancel();
            CaramelChatCompat.invokeOnMainThread(this, "setFocus", boolean.class, focused);
        }
    }

    @Inject(
            method = "getKeyboardStatus()Lmoe/caramel/chat/driver/KeyboardStatus;",
            at = @At("HEAD"),
            cancellable = true,
            require = 0,
            remap = false
    )
    private void ixeris$getKeyboardStatusOnWindowThread(CallbackInfoReturnable<Object> cir) {
        if (!MainThreadDispatcher.isOnThread()) {
            cir.setReturnValue(CaramelChatCompat.queryOnMainThread(this, "getKeyboardStatus"));
        }
    }
}
