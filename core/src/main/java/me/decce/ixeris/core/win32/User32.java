package me.decce.ixeris.core.win32;

import org.lwjgl.system.APIUtil;
import org.lwjgl.system.Checks;
import org.lwjgl.system.NativeType;
import org.lwjgl.system.SharedLibrary;
import org.lwjgl.system.windows.POINT;

import java.nio.IntBuffer;

import static org.lwjgl.system.APIUtil.apiGetFunctionAddress;
import static org.lwjgl.system.JNI.*;
import static org.lwjgl.system.MemoryUtil.memAddress;

public class User32 {
    public static final int RIDEV_NOLEGACY = 0x00000030;
    public static final int RIDEV_REMOVE = 0x00000001;
    public static final int RIM_TYPEMOUSE = 0;
    public static final short MOUSE_MOVE_RELATIVE = 0x00;
    public static final short MOUSE_MOVE_ABSOLUTE = 0x01;
    public static final short MOUSE_VIRTUAL_DESKTOP = 0x02;
    public static final short MOUSE_ATTRIBUTES_CHANGED = 0x04;
    public static final short MOUSE_MOVE_NOCOALESCE = 0x08;
    public static final short RI_MOUSE_BUTTON_1_DOWN = 0x0001;
    public static final short RI_MOUSE_LEFT_BUTTON_DOWN = 0x0001;
    public static final short RI_MOUSE_BUTTON_1_UP = 0x0002;
    public static final short RI_MOUSE_LEFT_BUTTON_UP = 0x0002;
    public static final short RI_MOUSE_BUTTON_2_DOWN = 0x0004;
    public static final short RI_MOUSE_RIGHT_BUTTON_DOWN = 0x0004;
    public static final short RI_MOUSE_BUTTON_2_UP = 0x0008;
    public static final short RI_MOUSE_RIGHT_BUTTON_UP = 0x0008;
    public static final short RI_MOUSE_BUTTON_3_DOWN = 0x0010;
    public static final short RI_MOUSE_MIDDLE_BUTTON_DOWN = 0x0010;
    public static final short RI_MOUSE_BUTTON_3_UP = 0x0020;
    public static final short RI_MOUSE_MIDDLE_BUTTON_UP = 0x0020;
    public static final short RI_MOUSE_BUTTON_4_DOWN = 0x0040;
    public static final short RI_MOUSE_BUTTON_4_UP = 0x0080;
    public static final short RI_MOUSE_BUTTON_5_DOWN = 0x0100;
    public static final short RI_MOUSE_BUTTON_5_UP = 0x0200;
    public static final short RI_MOUSE_WHEEL = 0x0400;
    public static final short RI_MOUSE_HWHEEL = 0x0800;

    private static final SharedLibrary USER32 = APIUtil.apiCreateLibrary("user32");

    public static final long GetRawInputBuffer = apiGetFunctionAddress(USER32, "GetRawInputBuffer");
    public static final long RegisterRawInputDevices = apiGetFunctionAddress(USER32, "RegisterRawInputDevices");
    public static final long ScreenToClient = apiGetFunctionAddress(USER32, "ScreenToClient");
    public static final long GetKeyState = apiGetFunctionAddress(USER32, "GetKeyState");

    public static int nGetRawInputBuffer(long pData, long pcbSize, int cbSizeHeader) {
        return callPPI(pData, pcbSize, cbSizeHeader, GetRawInputBuffer);
    }

    @NativeType("UINT")
    public static int GetRawInputBuffer(@NativeType("LPRAWINPUT") RAWINPUT pData, @NativeType("PUINT") IntBuffer pcbSize, @NativeType("UINT") int cbSizeHeader) {
        return nGetRawInputBuffer(pData.address(), memAddress(pcbSize), cbSizeHeader);
    }

    @NativeType("BOOL")
    public static boolean RegisterRawInputDevices(@NativeType("PCRAWINPUTDEVICE") RAWINPUTDEVICE pRawInputDevices, @NativeType("UINT") int uiNumDevices, @NativeType("UINT") int cbSize) {
        return callPI(pRawInputDevices.address(), uiNumDevices, cbSize, RegisterRawInputDevices) != 0;
    }

    public static int nScreenToClient(long hWnd, long lpPoint) {
        if (Checks.CHECKS) {
            Checks.check(hWnd);
        }
        return callPPI(hWnd, lpPoint, ScreenToClient);
    }

    @NativeType("BOOL")
    public static boolean ScreenToClient(@NativeType("HWND") long hWnd, @NativeType("LPPOINT") POINT lpPoint) {
        return nScreenToClient(hWnd, lpPoint.address()) != 0;
    }

    @NativeType("SHORT")
    public static short GetKeyState(@NativeType("INT") int nVirtKey) {
        return callS(nVirtKey, GetKeyState);
    }
}
