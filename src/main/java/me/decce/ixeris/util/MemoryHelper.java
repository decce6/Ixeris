package me.decce.ixeris.util;

import org.lwjgl.system.MemoryUtil;

public class MemoryHelper {
    public static long deepCopy(long address) {
        String str = MemoryUtil.memUTF8(address);
        return MemoryUtil.memAddress(MemoryUtil.memUTF8(str));
    }

    public static void free(long address) {
        MemoryUtil.nmemFree(address);
    }
}
