package me.decce.ixeris.core.input.win32;

import me.decce.ixeris.core.win32.RAWINPUTDEVICE;
import me.decce.ixeris.core.win32.User32;
import me.decce.ixeris.core.win32.Win32Exception;
import org.lwjgl.system.windows.WinBase;

public class RawInputDevices {
    public static final RawInputDevices MOUSE = new RawInputDevices(User32.HID_USAGE_GENERIC_MOUSE, User32.RIDEV_NOLEGACY);
    public static final RawInputDevices KEYBOARD = new RawInputDevices(User32.HID_USAGE_GENERIC_KEYBOARD, User32.RIDEV_NOLEGACY);

    private static final RAWINPUTDEVICE device = RAWINPUTDEVICE.create().usUsagePage(User32.HID_USAGE_PAGE_GENERIC);

    private final short usage;
    private final int flags;

    private RawInputDevices(short usage, int flags) {
        this.usage = usage;
        this.flags = flags;
    }

    public void register(long hwndTarget) {
        device.usUsage(this.usage).dwFlags(this.flags).hwndTarget(hwndTarget);
        _register();
    }

    public void unregister() {
        device.usUsage(this.usage).dwFlags(User32.RIDEV_REMOVE).hwndTarget(0);
        _register();
    }

    private static void _register() {
        if (!User32.RegisterRawInputDevices(device, 1, device.sizeof())) {
            throw new Win32Exception("Failed to register raw input device! %s %s %s %s".formatted(device.usUsagePage(), device.usUsage(), device.dwFlags(), device.hwndTarget()), WinBase.GetLastError());
        }
    }
}
