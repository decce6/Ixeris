package me.decce.ixeris.core.input.win32;

import me.decce.ixeris.core.natives.win32.User32Ex;
import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;

public class KeyboardStateHelper {
    private static final ByteBuffer buffer = BufferUtils.createByteBuffer(256);

    public static void set(int vKey, byte state) {
        User32Ex.GetKeyboardState(buffer);
        buffer.put(vKey, state);
        User32Ex.SetKeyboardState(buffer);
    }

    public static void setDown(int vKey) {
        // Set the high-order bit to 1 to specify that the vKey is down
        // https://learn.microsoft.com/en-us/windows/win32/api/winuser/nf-winuser-getkeystate
        set(vKey, (byte) (1 << 7));
    }

    public static void setUp(int vKey) {
        set(vKey, (byte) 0);
    }
}
