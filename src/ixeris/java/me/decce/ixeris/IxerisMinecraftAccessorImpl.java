package me.decce.ixeris;

import me.decce.ixeris.core.IxerisMinecraftAccessor;
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
}
