package me.decce.ixeris.neoforge.core;

import me.decce.ixeris.core.Ixeris;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforgespi.earlywindow.GraphicsBootstrapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class IxerisBootstrapper implements GraphicsBootstrapper {
    private final Logger LOGGER = LogManager.getLogger();

    @Override
    public String name() {
        return "ixeris";
    }

    // Must run before org.lwjgl.glfw.GLFW is loaded
    @Override
    public void bootstrap(String[] arguments) {
        if (!isOnClient()) {
            LOGGER.info("Skipped Ixeris bootstrapping because: on dedicated server");
            return;
        }

        var classLoaderHandler = new NeoForgeClassLoaderHandler(this.getClass().getClassLoader().getParent(), this.getClass().getClassLoader());
        classLoaderHandler.loadCoreClasses(this.getClass());
        classLoaderHandler.removeModClassesFromServiceLayer();

        if (!Ixeris.getConfig().isEnabled()) {
            LOGGER.info("Skipped Ixeris bootstrapping because: disabled by config");
            return;
        }

        var helper = new NeoForgeTransformationHelper(classLoaderHandler.modClassLoader);

        LOGGER.info("Attempting to transform org.lwjgl.glfw.GLFW");

        helper.expandGlfwModuleReads();

        var transformedBytes = helper.doTransformation("org.lwjgl.glfw.GLFW", classLoaderHandler.readClassBytes("org/lwjgl/glfw/GLFW.class"), true);

        try {
            classLoaderHandler.defineClass(classLoaderHandler.bootstrapClassLoader, "org.lwjgl.glfw.GLFW", transformedBytes);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }

        Ixeris.inEarlyDisplay = true;

        try {
            classLoaderHandler.close();
        } catch (IOException ignored) {}
    }

    public static boolean isOnClient() {
        return FMLEnvironment.getDist().isClient();
    }
}
