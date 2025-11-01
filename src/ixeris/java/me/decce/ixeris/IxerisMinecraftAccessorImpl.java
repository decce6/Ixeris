package me.decce.ixeris;

import me.decce.ixeris.core.IxerisMinecraftAccessor;
import me.decce.ixeris.mixins.MouseHandlerAccessor;
import net.minecraft.client.Minecraft;

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
}
