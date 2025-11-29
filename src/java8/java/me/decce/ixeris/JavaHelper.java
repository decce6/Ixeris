package me.decce.ixeris;

public class JavaHelper {
    public static final boolean JAVA_SUPPORTED;

    static {
        String javaVersion = System.getProperty("java.version");
        JAVA_SUPPORTED = !javaVersion.startsWith("1.") && Integer.parseInt(javaVersion.substring(0, 2)) >= 17;
    }
}
