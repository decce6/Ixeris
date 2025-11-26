package me.decce.ixeris;

public final class IxerisMod {
    public static final String MOD_ID = "ixeris";

    public static Thread renderThread;

    public static void init() {
    }

    public static boolean isOnRenderThread() {
        return Thread.currentThread() == renderThread;
    }
}