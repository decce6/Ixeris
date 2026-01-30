package me.decce.ixeris.mixins;

import ca.weblite.objc.NSObject;
import com.mojang.blaze3d.platform.Window;
import me.decce.ixeris.core.Ixeris;
import me.decce.ixeris.core.threading.MainThreadDispatcher;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

//? if >=1.18 {
@Mixin(value = com.mojang.blaze3d.platform.MacosUtil.class, remap = false)
public class MacosUtilMixin {
    @Shadow
    //? if >1.20.1 {
    private static void toggleNativeFullscreen(final NSObject nsWindow) {
    //?} else {
    /*private static void toggleFullscreen(final NSObject nsWindow) {
    *///?}
        throw new AssertionError();
    }

    //? if >1.20.1 {
     @Shadow
     private static long getStyleMask(NSObject nSObject) {
         throw new AssertionError();
     }
    //?} else {
    /*@Shadow
    private static boolean isInKioskMode(NSObject nSObject) {
        throw new AssertionError();
    }
    *///?}

    //? if >1.20.1 {
     @Inject(method = "clearResizableBit", at = @At("HEAD"), cancellable = true)
     private static void ixeris$clearResizableBit(/*? if >1.21.8 {*/ Window /*?} else {*/ /*long *//*?}*/ window, CallbackInfo ci) {
         if (!Ixeris.isOnMainThread()) {
             ci.cancel();
             MainThreadDispatcher.runNow(() -> {
                 com.mojang.blaze3d.platform.MacosUtil.clearResizableBit(window);
             });
         }
     }
    
     @Inject(method = "getStyleMask", at = @At("HEAD"), cancellable = true)
     private static void ixeris$getStyleMask(NSObject nsWindow, CallbackInfoReturnable<Long> cir) {
         if (!Ixeris.isOnMainThread()) {
             cir.setReturnValue(MainThreadDispatcher.query(() -> getStyleMask(nsWindow)));
         }
     }
    //?} else {
    /*@Inject(method = "isInKioskMode", at = @At("HEAD"), cancellable = true)
    private static void ixeris$isInKioskMode(NSObject nsWindow, CallbackInfoReturnable<Boolean> cir) {
        if (!Ixeris.isOnMainThread()) {
            cir.setReturnValue(MainThreadDispatcher.query(() -> isInKioskMode(nsWindow)));
        }
    }
    *///?}
    //? if >1.20.1 {
     @Inject(method = "toggleNativeFullscreen", at = @At("HEAD"), cancellable = true)
    //?} else {
    /*@Inject(method = "toggleFullscreen(Lca/weblite/objc/NSObject;)V", at = @At("HEAD"), cancellable = true)
    *///?}
    private static void ixeris$toggleNativeFullscreen(NSObject nsWindow, CallbackInfo ci) {
        if (!Ixeris.isOnMainThread()) {
            ci.cancel();
            //? if >1.20.1 {
            MainThreadDispatcher.runNow(() -> toggleNativeFullscreen(nsWindow));
            //?} else {
            /*MainThreadDispatcher.runNow(() -> toggleFullscreen(nsWindow));
            *///?}
        }
    }
}
//?} else {
/*// No need for this Mixin; cannot comment out the entire class as it is still present in the mixin json
@Mixin(value = com.mojang.blaze3d.platform.Window.class, remap = false)
public class MacosUtilMixin {}
*///?}