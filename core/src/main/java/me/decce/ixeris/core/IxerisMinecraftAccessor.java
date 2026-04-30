package me.decce.ixeris.core;

public interface IxerisMinecraftAccessor {
    long getMinecraftWindow();
    void setIgnoreFirstMouseMove();
    boolean isMouseInternallyGrabbed();
    boolean isOnRenderThread();
    default boolean isMinecraftWindowCreated() {
        return getMinecraftWindow() != 0L;
    }
    void unlockContext();
    void lockContext();
}
