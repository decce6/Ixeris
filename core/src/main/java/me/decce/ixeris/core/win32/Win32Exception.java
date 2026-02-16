package me.decce.ixeris.core.win32;

public class Win32Exception extends RuntimeException {
    public Win32Exception(String message, int errorCode) {
        super(message + " (%d)".formatted(errorCode));
    }

    public Win32Exception(int errorCode) {
        this("Win32 exception", errorCode);
    }
}
