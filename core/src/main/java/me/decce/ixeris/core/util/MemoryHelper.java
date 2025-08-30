package me.decce.ixeris.core.util;

import org.lwjgl.system.APIUtil;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import java.util.function.BiFunction;

public class MemoryHelper {
    public static long deepCopy(long address) {
        String str = MemoryUtil.memUTF8(address);
        return MemoryUtil.memAddress(MemoryUtil.memUTF8(str));
    }

    public static long deepCopy(long address, int arrayLength, BiFunction<Long, Integer, String> getter) {
        String[] strings = new String[arrayLength];
        for (int i = 0; i < arrayLength; i++) {
            strings[i] = getter.apply(address, i);
        }
        return APIUtil.apiArrayi(MemoryStack.stackGet(), MemoryUtil::memUTF8, strings);
    }

    public static void free(long address) {
        MemoryUtil.nmemFree(address);
    }

    public static void free(long address, int arrayLength) {
        APIUtil.apiArrayFree(address, arrayLength);
    }
}
