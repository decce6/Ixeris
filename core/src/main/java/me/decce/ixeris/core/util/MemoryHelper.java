package me.decce.ixeris.core.util;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.system.APIUtil;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import java.nio.ByteBuffer;
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

    public static ByteBuffer copyString(ByteBuffer original) {
        if (original == null) {
            return original;
        }
        return MemoryUtil.memUTF8(MemoryUtil.memUTF8(original));
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

    public static ByteBuffer copyByteBuffer(ByteBuffer buffer) {
        if (buffer == null) {
            return null;
        }
        var copied = BufferUtils.createByteBuffer(buffer.capacity());
        MemoryUtil.memCopy(buffer, copied);
        copied.position(buffer.position());
        copied.limit(buffer.limit());
        return copied;
    }

    public static GLFWImage copyGlfwImage(GLFWImage image) {
        int width = image.width();
        int height = image.height();
        return GLFWImage.malloc()
                .height(height)
                .width(width)
                .pixels(copyByteBuffer(image.pixels(width * height * 4)));
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
