package me.decce.ixeris;

import java.util.regex.Pattern;

public class JavaHelper {
    public static final boolean JAVA_SUPPORTED;
    public static final int MINIMUM_MAJOR = 17;
    public static String message;

    static {
        String javaVersion = System.getProperty("java.version");
        if (javaVersion.startsWith("1.")) {
            JAVA_SUPPORTED = false;
        }
        else {
            int major;
            try {
                String majorString = javaVersion.split(Pattern.quote("."))[0];
                int i = majorString.indexOf('-'); // some builds may contain version strings like 26-ea, 26-beta
                major = Integer.parseInt(i == -1 ? majorString : majorString.substring(0, i));
            } catch (Exception e) {
                message = "Failed to parse java version, assuming supported java: " + javaVersion;
                major = MINIMUM_MAJOR;
            }
            JAVA_SUPPORTED = major >= MINIMUM_MAJOR;
        }
    }
}
