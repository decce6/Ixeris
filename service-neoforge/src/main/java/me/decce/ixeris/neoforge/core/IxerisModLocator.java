package me.decce.ixeris.neoforge.core;

import cpw.mods.jarhandling.SecureJar;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.fml.loading.moddiscovery.locators.JarInJarDependencyLocator;
import net.neoforged.fml.loading.moddiscovery.readers.JarModsDotTomlModFileReader;
import net.neoforged.neoforgespi.locating.IDependencyLocator;
import net.neoforged.neoforgespi.locating.IDiscoveryPipeline;
import net.neoforged.neoforgespi.locating.IModFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

public class IxerisModLocator extends JarInJarDependencyLocator implements IDependencyLocator {
    @Override
    public void scanMods(List<IModFile> loadedMods, IDiscoveryPipeline pipeline) {
        if (!FMLLoader.getDist().isClient()) {
            return;
        }
        try {
            var secureJar = SecureJar.from(Path.of(IxerisModLocator.class.getProtectionDomain().getCodeSource().getLocation().toURI()));
            var resource = IxerisModLocator.class.getResource("/META-INF/jarjar/");
            try (var stream = Files.walk(Path.of(Objects.requireNonNull(resource).toURI()), 1)) {
                stream.filter(path -> path.getFileName().toString().endsWith("-mod.jar"))
                        .forEach(path -> {
                            var modFile = IModFile.create(secureJar, JarModsDotTomlModFileReader::manifestParser);
                            var mod = loadModFileFrom(modFile, Path.of("META-INF", "jarjar", path.getFileName().toString()), pipeline).orElseThrow();
                            pipeline.addModFile(mod);
                        });
            }
        } catch (Throwable t) {
            throw new RuntimeException("Loading Ixeris JiJ mod", t);
        }
    }
}
