package me.decce.ixeris;

import com.mojang.blaze3d.systems.RenderSystem;
import me.decce.ixeris.core.EventHandler;
import me.decce.ixeris.core.IxerisMinecraftAccessor;
import me.decce.ixeris.mixins.MouseHandlerAccessor;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.CGL;
//? >=26.3 {
/*import me.decce.ixeris.core.sdl.SdlEventHandler;
*///? } else {
import me.decce.ixeris.core.glfw.GlfwEventHandler;
//? }

public class IxerisMinecraftAccessorImpl implements IxerisMinecraftAccessor {
    @Override
    public long getMinecraftWindow() {
        return VersionCompatUtils.getMinecraftWindow();
    }

    @Override
    public void setIgnoreFirstMouseMove() {
        Minecraft.getInstance().mouseHandler.setIgnoreFirstMove();
    }

    @Override
    public boolean isMouseInternallyGrabbed() {
        return ((MouseHandlerAccessor)Minecraft.getInstance().mouseHandler).isMouseGrabbed();
    }

    @Override
    public boolean isOnRenderThread() {
        return RenderSystem.isOnRenderThread();
    }

    @Override
    public void lockContext() {
        var context = IxerisMod.lockedContext;
        if (context != 0L) {
            CGL.CGLLockContext(context);
        }
    }

    @Override
    public EventHandler createEventHandler() {
        //? >=26.3 {
        /*return new SdlEventHandler();
        *///? } else {
        return new GlfwEventHandler();
        //? }
    }

    @Override
    public void unlockContext() {
        var context = IxerisMod.lockedContext;
        if (context != 0L) {
            CGL.CGLUnlockContext(context);
        }
    }
}
