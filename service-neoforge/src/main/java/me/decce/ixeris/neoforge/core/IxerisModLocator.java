package me.decce.ixeris.neoforge.core;

import cpw.mods.jarhandling.JarContents;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforgespi.ILaunchContext;
import net.neoforged.neoforgespi.locating.IDiscoveryPipeline;
import net.neoforged.neoforgespi.locating.IModFileCandidateLocator;
import net.neoforged.neoforgespi.locating.IncompatibleFileReporting;
import net.neoforged.neoforgespi.locating.ModFileDiscoveryAttributes;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class IxerisModLocator implements IModFileCandidateLocator {
    @Override
    public void findCandidates(ILaunchContext context, IDiscoveryPipeline pipeline) {
        if (!FMLEnvironment.dist.isClient()) {
            return;
        }
        try {
            final var resource = this.getClass().getResource("/META-INF/jarjar/");
            try (var stream = Files.walk(Path.of(Objects.requireNonNull(resource).toURI()), 1)) {
                stream.filter(path -> path.getFileName().toString().endsWith("-mod.jar"))
                        .forEach(path -> pipeline.addJarContent(JarContents.of(path), ModFileDiscoveryAttributes.DEFAULT, IncompatibleFileReporting.ERROR));
            }
        } catch (Throwable t) {
            throw new RuntimeException("Loading Ixeris JiJ mod", t);
        }
    }
}
