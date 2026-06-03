package me.decce.ixeris.core.util;

import org.lwjgl.system.APIUtil;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import java.util.function.BiFunction;

public class MemoryHelper {
    public static long copyIntArray(long address, int size) {
        if (address == 0L) {
            return 0L;
        }
        var buffer = MemoryUtil.memIntBuffer(address, size);
        var copied = MemoryUtil.memAllocInt(size);
        copied.put(buffer).flip();
        return MemoryUtil.memAddress(copied);
    }

    public static long copyString(long address) {
        if (address == 0L) {
            return 0L;
        }
        String str = MemoryUtil.memUTF8(address);
        return MemoryUtil.memAddress(MemoryUtil.memUTF8(str));
    }

    public static long copyStringArray(long address, int arrayLength, BiFunction<Long, Integer, String> getter) {
        if (address == 0L) {
            return 0L;
        }
        String[] strings = new String[arrayLength];
        for (int i = 0; i < arrayLength; i++) {
            strings[i] = getter.apply(address, i);
        }
        return APIUtil.apiArrayi(MemoryStack.stackGet(), MemoryUtil::memUTF8, strings);
    }

    public static void free(long address) {
        if (address != 0L) {
            MemoryUtil.nmemFree(address);
        }
    }

    public static void free(long address, int arrayLength) {
        if (address != 0L) {
            APIUtil.apiArrayFree(address, arrayLength);
        }
    }
}
