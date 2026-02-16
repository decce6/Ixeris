package me.decce.ixeris;

public class JavaHelper {
    public static final boolean JAVA_SUPPORTED;
    public static final int MINIMUM_MAJOR = 17;

    static {
        JAVA_SUPPORTED = tryGetMajor() >= MINIMUM_MAJOR;
    }

    @SuppressWarnings("Since15")
    private static int tryGetMajor() {
        try {
            return Runtime.version().feature();
        }
        catch (Throwable ignored) {
            // The methods may not exist on older versions of Java
            return 0;
        }
    }
}
