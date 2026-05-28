package me.decce.ixeris.core.util;

import me.decce.ixeris.core.Ixeris;
import org.lwjgl.system.Configuration;

import java.lang.invoke.MethodHandle;

public class UpcallExceptionHelper {
    private static MethodHandle lwjglUpcallExceptionHandler;
    private static boolean unsupportedLwjgl;

    public static void handleUpcallException(Throwable throwable) {
        if (!Configuration.FFM_UPCALL_EXCEPTION_CATCH.get(true)) {
            Ixeris.LOGGER.fatal("An upcall has thrown an exception. Ixeris will now terminate the VM in honor of the org.lwjgl.system.fmm.upcall.exception.catch system property.", throwable);
            System.exit(1);
        }
        else {
            invokeLwjglUpcallExceptionHandler(throwable);
        }
    }

    private static MethodHandle getLwjglUpcallExceptionHandler() {
        if (lwjglUpcallExceptionHandler == null && !unsupportedLwjgl) {
            try {
                lwjglUpcallExceptionHandler = ReflectionHelper.unreflect(() -> Class.forName("org.lwjgl.system.Upcalls").getDeclaredMethod("wrapException", Throwable.class));
            }
            catch (Throwable throwable) {
                // Possibly on unsafe backend, or a future LWJGL update changed the method name/signature
                Ixeris.LOGGER.error("Failed to find LWJGL upcall exception handler", throwable);
                unsupportedLwjgl = true;
            }
        }
        return lwjglUpcallExceptionHandler;
    }

    private static void invokeLwjglUpcallExceptionHandler(Throwable throwable) {
        if (lwjglUpcallExceptionHandler == null) {
            lwjglUpcallExceptionHandler = getLwjglUpcallExceptionHandler();
        }
        if (lwjglUpcallExceptionHandler != null) {
            try {
                lwjglUpcallExceptionHandler.invoke(throwable);
            } catch (Throwable error) {
                Ixeris.LOGGER.error("Error invoking LWJGL upcall exception handler", error);
            }
        }
        else {
            Ixeris.LOGGER.error("Unhandled exception in callback", throwable);
        }
    }
}
