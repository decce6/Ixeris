package me.decce.ixeris;

import me.decce.ixeris.core.IxerisMinecraftAccessor;
import me.decce.ixeris.mixins.MouseHandlerAccessor;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.CGL;

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
    public void lockContext() {
        var context = IxerisMod.lockedContext;
        if (context != 0L) {
            CGL.CGLLockContext(context);
        }
    }

    @Override
    public void unlockContext() {
        var context = IxerisMod.lockedContext;
        if (context != 0L) {
            CGL.CGLUnlockContext(context);
        }
    }
}
