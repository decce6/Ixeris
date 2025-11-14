package me.decce.ixeris.neoforge.core;

import cpw.mods.modlauncher.Launcher;
import cpw.mods.modlauncher.api.IModuleLayerManager;
import me.decce.ixeris.core.Ixeris;
import me.decce.ixeris.core.transform.TransformationHelper;
import net.neoforged.neoforgespi.earlywindow.GraphicsBootstrapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

        var classLoaderHandler = new NeoForgeClassLoaderHandler(Thread.currentThread().getContextClassLoader(), this.getClass().getClassLoader());
        classLoaderHandler.loadCoreClasses(this.getClass());
        classLoaderHandler.removeModClassesFromServiceLayer();

        if (!Ixeris.getConfig().isEnabled()) {
            LOGGER.info("Skipped Ixeris bootstrapping because: disabled by config");
            return;
        }

        LOGGER.info("Attempting to transform org.lwjgl.glfw.GLFW");

        var helper = new NeoForgeTransformationHelper(classLoaderHandler.modClassLoader);

        helper.expandGlfwModuleReads();

        var transformedBytes = helper.doTransformation("org.lwjgl.glfw.GLFW", classLoaderHandler.readClassBytes("org/lwjgl/glfw/GLFW.class"), true);

        try {
            classLoaderHandler.defineClass(classLoaderHandler.bootstrapClassLoader, "org.lwjgl.glfw.GLFW", transformedBytes);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }

        this.temporarilySuppressEventPollingWarning();
    }

    // Must be called *after* calling loadCoreClasses to make sure it uses the Ixeris class loaded on the bootstrap classloader
    private void temporarilySuppressEventPollingWarning() {
        // Suppress the warnings produced by early display window calling glfwPollEvents, which are safely canceled
        Ixeris.suppressEventPollingWarning = true;
    }

    public static boolean isOnClient() {
        // Assume we're on dedicated server if the GLFW module does not exist.
        // This is not safe and might cause errors to be silenced.
        var layer = Launcher.INSTANCE.findLayerManager().orElseThrow().getLayer(IModuleLayerManager.Layer.BOOT).orElseThrow();
        return layer.findModule(TransformationHelper.MODULE_GLFW).isPresent();
    }
}
