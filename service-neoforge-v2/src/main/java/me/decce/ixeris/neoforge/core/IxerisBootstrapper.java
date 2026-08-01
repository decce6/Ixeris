package me.decce.ixeris.neoforge.core;

import me.decce.ixeris.core.Constants;
import me.decce.ixeris.core.Ixeris;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforgespi.earlywindow.GraphicsBootstrapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Arrays;

public class IxerisBootstrapper implements GraphicsBootstrapper {
    private final Logger LOGGER = LogManager.getLogger();

    @Override
    public String name() {
        return "ixeris";
    }

    // Must run before GLFW/SDL classes are loaded
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

        LOGGER.debug("Attempting to transform GLFW/SDL classes");

        var helper = new NeoForgeTransformationHelper(classLoaderHandler.modClassLoader);

        helper.expandGlfwModuleReads();

        for (var clazz : Constants.getClassesForTransformation()) {
            var originalBytes = classLoaderHandler.readClassBytes(clazz.replace('.', '/') + ".class");
            if (originalBytes == null) {
                continue;
            }
            var transformedBytes = helper.doTransformation(clazz, originalBytes, true);
            if (!Arrays.equals(originalBytes, transformedBytes)) {
                try {
                    classLoaderHandler.defineClass(classLoaderHandler.bootstrapClassLoader, clazz, transformedBytes);
                } catch (Throwable e) {
                    throw new RuntimeException(e);
                }
            }
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
