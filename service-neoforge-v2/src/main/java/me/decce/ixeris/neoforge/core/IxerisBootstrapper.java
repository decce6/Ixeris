package me.decce.ixeris.neoforge.core;

import me.decce.ixeris.core.Ixeris;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforgespi.earlywindow.GraphicsBootstrapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class IxerisBootstrapper implements GraphicsBootstrapper {
    private final Logger LOGGER = LogManager.getLogger();

    private final NeoForgeTransformationHelper helper = new NeoForgeTransformationHelper(this.getClass().getClassLoader().getParent(), this.getClass().getClassLoader());

    @SuppressWarnings("ReferenceToMixin")
    private final Class<?>[] TRANSFORMERS = new Class[] {
            me.decce.ixeris.core.mixins.GLFWMixin.class,
            me.decce.ixeris.core.mixins.callback_dispatcher.GLFWMixin.class,
            me.decce.ixeris.core.mixins.glfw_state_caching.GLFWMixin.class,
            me.decce.ixeris.core.mixins.glfw_threading.GLFWMixin.class,
    };

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

        helper.verifyClassLoaders();

        LOGGER.info("Attempting to transform org.lwjgl.glfw.GLFW");

        helper.expandGlfwModuleReads();

        var transformedBytes = helper.doTransformation(TRANSFORMERS, true);

        try {
            helper.defineClass(helper.mcBootstrapClassLoader, "org.lwjgl.glfw.GLFW", transformedBytes);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }

        helper.loadCoreClasses(this.getClass());

        helper.removeModClassesFromServiceLayer();

        this.temporarilySuppressEventPollingWarning();

        try {
            helper.close();
        } catch (IOException ignored) {}
    }

    // Must be called *after* everything else is done to make sure it uses the Ixeris class loaded on MC-BOOTSTRAP
    private void temporarilySuppressEventPollingWarning() {
        // Suppress the warnings produced by early display window calling glfwPollEvents, which are safely canceled
        Ixeris.suppressEventPollingWarning = true;
    }

    public static boolean isOnClient() {
        return FMLEnvironment.getDist().isClient();
    }
}
