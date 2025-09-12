package me.decce.ixeris.core;

public class IxerisNoopAccessor implements IxerisMinecraftAccessor {
    @Override
    public long getMinecraftWindow() {
        return 0;
    }

    @Override
    public void setIgnoreFirstMouseMove() {

    }
}
