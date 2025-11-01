package me.decce.ixeris.core.glfw.state_caching.util;

import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class BufferHelper {
    public static boolean check(int[] buffer) {
        return buffer != null && buffer.length > 0;
    }

    public static boolean check(IntBuffer buffer) {
        return buffer != null && buffer.remaining() > 0;
    }

    public static boolean check(float[] buffer) {
        return buffer != null && buffer.length > 0;
    }

    public static boolean check(FloatBuffer buffer) {
        return buffer != null && buffer.remaining() > 0;
    }

    public static boolean check(double[] buffer) {
        return buffer != null && buffer.length > 0;
    }

    public static boolean check(DoubleBuffer buffer) {
        return buffer != null && buffer.remaining() > 0;
    }
}
