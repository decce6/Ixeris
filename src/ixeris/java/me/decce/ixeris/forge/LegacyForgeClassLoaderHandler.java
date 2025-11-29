//? if forge && <=1.16.5 {
/*package me.decce.ixeris.forge;

import cpw.mods.modlauncher.TransformingClassLoader;
import me.decce.ixeris.core.transform.ClassLoaderHandler;
import net.minecraftforge.fml.loading.FMLLoader;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class LegacyForgeClassLoaderHandler extends ClassLoaderHandler {
    // ClassLoader#getName was added in Java 9, so in 1.16 if you call this method it returns null
    public static final String LEGACY_FORGE_MOD_CLASSLOADER = "cpw.mods.modlauncher.TransformingClassLoader";

    private FileSystem fileSystem;

    public LegacyForgeClassLoaderHandler(ClassLoader bootstrapClassLoader, ClassLoader modClassLoader) {
        super(bootstrapClassLoader, modClassLoader);
    }

    @Override
    public void verifyClassLoaders() {
        if (!LEGACY_FORGE_MOD_CLASSLOADER.equals(modClassLoader.getClass().getName())) {
            super.verifyClassLoaders();
        }
    }

    @Override
    public void removeModClassesFromServiceLayer() {
        ((TransformingClassLoader)modClassLoader).addTargetPackageFilter(s -> !s.startsWith("me.decce.ixeris.core"));
    }

    @Override
    protected Stream<Path> getClassesStream(Class<?> modClass) {
        var path = FMLLoader.getLoadingModList().getModFileById("ixeris").getFile().getFilePath();
        try {
            fileSystem = FileSystems.newFileSystem(path);
            return Files.walk(fileSystem.getPath("/me/decce/ixeris/core"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void close() throws IOException {
        if (fileSystem != null) {
            fileSystem.close();
        }
    }
}
*///?}