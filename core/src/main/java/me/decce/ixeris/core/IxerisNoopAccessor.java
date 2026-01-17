package me.decce.ixeris.core;

public class IxerisNoopAccessor implements IxerisMinecraftAccessor {
    @Override
    public long getMinecraftWindow() {
        return 0;
    }

    @Override
    public void setIgnoreFirstMouseMove() {
        // no-op
    }

    @Override
    public boolean isMouseInternallyGrabbed() {
        return false;
    }
}
