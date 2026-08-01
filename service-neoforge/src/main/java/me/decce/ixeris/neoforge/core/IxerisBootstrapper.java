package me.decce.ixeris.neoforge.core;

import cpw.mods.modlauncher.Launcher;
import cpw.mods.modlauncher.api.IModuleLayerManager;
import me.decce.ixeris.core.Constants;
import me.decce.ixeris.core.Ixeris;
import me.decce.ixeris.core.transform.util.TransformationConstants;
import net.neoforged.neoforgespi.earlywindow.GraphicsBootstrapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

        var classLoaderHandler = new NeoForgeClassLoaderHandler(Thread.currentThread().getContextClassLoader(), this.getClass().getClassLoader());
        classLoaderHandler.loadCoreClasses(this.getClass());
        classLoaderHandler.removeModClassesFromServiceLayer();

        if (!Ixeris.getConfig().isEnabled()) {
            LOGGER.info("Skipped Ixeris bootstrapping because: disabled by config");
            return;
        }

        LOGGER.debug("Attempting to transform org.lwjgl.glfw.GLFW");

        var helper = new NeoForgeTransformationHelper(classLoaderHandler.modClassLoader);

        helper.expandGlfwModuleReads();

        for (var clazz : Constants.getClassesForTransformation()) {
            var originalBytes = classLoaderHandler.readClassBytes(clazz.replace('.', '/') + ".class");
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
    }

    public static boolean isOnClient() {
        // Assume we're on dedicated server if the GLFW module does not exist.
        // This is not safe and might cause errors to be silenced.
        var layer = Launcher.INSTANCE.findLayerManager().orElseThrow().getLayer(IModuleLayerManager.Layer.BOOT).orElseThrow();
        return TransformationConstants.GLFW_MODULE_ALIASES.stream().anyMatch(alias -> layer.findModule(alias).isPresent());
    }
}
