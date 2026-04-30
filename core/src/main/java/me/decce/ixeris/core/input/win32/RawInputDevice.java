package me.decce.ixeris.core.input.win32;

import me.decce.ixeris.core.Ixeris;
import me.decce.ixeris.core.IxerisConfig;
import me.decce.ixeris.core.natives.win32.RAWINPUTDEVICE;
import me.decce.ixeris.core.natives.win32.User32Ex;
import me.decce.ixeris.core.natives.win32.Win32Exception;
import org.lwjgl.system.windows.WinBase;

public class RawInputDevice {
    public static final RawInputDevice MOUSE = new RawInputDevice(User32Ex.HID_USAGE_GENERIC_MOUSE, 0);
    public static final RawInputDevice MOUSE_NOLEGACY = new RawInputDevice(User32Ex.HID_USAGE_GENERIC_MOUSE, User32Ex.RIDEV_NOLEGACY);
    public static final RawInputDevice KEYBOARD = new RawInputDevice(User32Ex.HID_USAGE_GENERIC_KEYBOARD, 0);
    public static final RawInputDevice KEYBOARD_NOLEGACY = new RawInputDevice(User32Ex.HID_USAGE_GENERIC_KEYBOARD, User32Ex.RIDEV_NOLEGACY);

    private static final RAWINPUTDEVICE device = RAWINPUTDEVICE.create().usUsagePage(User32Ex.HID_USAGE_PAGE_GENERIC);

    private final short usage;
    private final int flags;

    private RawInputDevice(short usage, int flags) {
        this.usage = usage;
        this.flags = flags;
    }

    public void register(long hwndTarget) {
        device.usUsage(this.usage).dwFlags(this.flags).hwndTarget(hwndTarget);
        _register();
    }

    public void unregister() {
        device.usUsage(this.usage).dwFlags(User32Ex.RIDEV_REMOVE).hwndTarget(0);
        _register();
    }

    private static void _register() {
        if (!User32Ex.RegisterRawInputDevices(device, 1, device.sizeof())) {
            throw new Win32Exception("Failed to register raw input device! %s %s %s %s".formatted(device.usUsagePage(), device.usUsage(), device.dwFlags(), device.hwndTarget()), WinBase.GetLastError());
        }
    }

    public static RawInputDevice getMouseDevice() {
        if (Ixeris.getConfig().getMessageOptimizationStrategy() == IxerisConfig.MessageOptimizationStrategy.NOLEGACY) {
            return MOUSE_NOLEGACY;
        }
        return MOUSE;
    }

    public static RawInputDevice getKeyboardDevice() {
        if (Ixeris.getConfig().getMessageOptimizationStrategy() == IxerisConfig.MessageOptimizationStrategy.NOLEGACY) {
            return KEYBOARD_NOLEGACY;
        }
        return KEYBOARD;
    }
}
