package me.decce.ixeris.neoforge;

//? if neoforge {
import cpw.mods.cl.ModuleClassLoader;
import cpw.mods.modlauncher.Launcher;
import cpw.mods.modlauncher.api.IModuleLayerManager;
import me.decce.ixeris.Ixeris;
import net.lenni0451.classtransform.TransformerManager;
import net.lenni0451.classtransform.utils.tree.BasicClassProvider;
import net.neoforged.neoforgespi.earlywindow.GraphicsBootstrapper;
import sun.misc.Unsafe;

import java.io.IOException;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;

public class IxerisBootstraper implements GraphicsBootstrapper {
    public static final Unsafe THE_UNSAFE;
    public static final MethodHandles.Lookup LOOKUP;
    public static final MethodHandle DEFINE_CLASS;
    public static final MethodHandle RESOLVE_CLASS;
    static {
        try {
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            THE_UNSAFE = (Unsafe) theUnsafe.get(null);
            Field implLookup = MethodHandles.Lookup.class.getDeclaredField("IMPL_LOOKUP");
            LOOKUP = (MethodHandles.Lookup) THE_UNSAFE.getObject(THE_UNSAFE.staticFieldBase(implLookup), THE_UNSAFE.staticFieldOffset(implLookup));
            var clazz = ClassLoader.class;
            DEFINE_CLASS = LOOKUP.unreflect(clazz.getDeclaredMethod("defineClass", String.class, byte[].class, int.class, int.class));
            RESOLVE_CLASS = LOOKUP.unreflect(clazz.getDeclaredMethod("resolveClass", Class.class));
        } catch (Throwable throwable) {
            throw new RuntimeException(throwable);
        }
    }

    @Override
    public String name() {
        return Ixeris.MOD_ID;
    }

    // Must run before org.lwjgl.glfw.GLFW is loaded
    @Override
    public void bootstrap(String[] arguments) {
        var cl = (ModuleClassLoader) Thread.currentThread().getContextClassLoader();
        Ixeris.LOGGER.info("Bootstrapping on {}", cl.getClass().getName());
        var layer = Launcher.INSTANCE.findLayerManager().orElseThrow().getLayer(IModuleLayerManager.Layer.BOOT).orElseThrow();
        var module = layer.configuration().findModule("org.lwjgl.glfw").orElseThrow();
        var ref = module.reference();
        try (var reader = ref.open()) {
            try (var stream = reader.open("org/lwjgl/glfw/GLFW.class").orElseThrow()) {
                var bytes = stream.readAllBytes();
                var manager = getTransformerManager();
                manager.addTransformer(GLFWTransformer.class.getName());
                var transformedBytes = manager.transform("org.lwjgl.glfw.GLFW", bytes);
                var transformedClazz = (Class<?>) DEFINE_CLASS.invoke(cl, "org.lwjgl.glfw.GLFW", transformedBytes, 0, transformedBytes.length);
                RESOLVE_CLASS.invoke(cl, transformedClazz);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static TransformerManager getTransformerManager() {
        var provider = new BasicClassProvider();
        var manager = new TransformerManager(provider);
        // manager.addTransformerPreprocessor(new MixinsTranslator());
        return manager;
    }
}
//? }
