package me.decce.ixeris;

import java.util.regex.Pattern;

public class JavaHelper {
    public static final boolean JAVA_SUPPORTED;

    static {
        String javaVersion = System.getProperty("java.version");
        JAVA_SUPPORTED = !javaVersion.startsWith("1.") && Integer.parseInt(javaVersion.split(Pattern.quote("."))[0]) >= 17;
    }
}
