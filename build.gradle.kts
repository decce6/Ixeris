plugins {
    id("dev.isxander.modstitch.base") version "0.7.0-unstable"
    id("dev.isxander.modstitch.shadow") version "0.7.0-unstable"
}

fun fullModVersion(): String {
    val sb = StringBuilder(property("mod_version") as String)
    sb.append("+").append(property("deps.minecraft"))
    sb.append("-").append(when {
        modstitch.isLoom -> "fabric"
        modstitch.isModDevGradleRegular -> "neoforge"
        modstitch.isModDevGradleLegacy -> "forge"
        else -> "unknown"
    })
    return sb.toString()
}

val javaLanguageVersion = if (stonecutter.eval(stonecutter.current.version, ">=1.20.5")) 21 else 17
java.toolchain.languageVersion = JavaLanguageVersion.of(javaLanguageVersion)

version = fullModVersion()
group = property("maven_group") as String

tasks.withType<ProcessResources> {
    if (!modstitch.isLoom) exclude("**/fabric.mod.json")
    if (!modstitch.isModDevGradleRegular) exclude ("**/neoforge.mods.toml")
    if (!modstitch.isModDevGradleLegacy) exclude ("**/mods.toml")
    val propMap = mutableMapOf<String, Any>().apply {
        project.properties.forEach { k, v -> put(k.toString(), v.toString())}
        put ("mod_version_full", fullModVersion())
        put ("java_version", javaLanguageVersion)
    }
    inputs.property("propMap", propMap)
    filesMatching(listOf("**/fabric.mod.json", "**/mods.toml", "**/neoforge.mods.toml")) {
        expand(propMap)
    }
}

(tasks.getByName("processResources") as ProcessResources).apply {
    from (layout.settingsDirectory.dir("thirdparty")) {
        into ("thirdparty")
    }
    from (layout.settingsDirectory.file("LICENSE"))
}

// Source set acrobatics to achieve mod-in-service structure on (Neo)Forge
val ixerisSourceSet = sourceSets.create("ixeris");
sourceSets {
    named ("ixeris") {
        compileClasspath += sourceSets["main"].compileClasspath
        runtimeClasspath += sourceSets["main"].runtimeClasspath
        annotationProcessorPath += sourceSets["main"].annotationProcessorPath
    }
}

modstitch {
    minecraftVersion.set(property("deps.minecraft") as String)
    javaVersion.set(javaLanguageVersion)

    metadata {
        modId.set(project.property("modid") as String)
        modName.set(project.property("mod_name") as String)
        modVersion.set(project.version as String)
    }

    // Fabric Loom (Fabric)
    loom {
        fabricLoaderVersion = "0.17.2"
    }

    // ModDevGradle (NeoForge, Forge, Forgelike)
    moddevgradle {
        forgeVersion = findProperty("deps.forge") as String?
        mcpVersion = findProperty("deps.mcp") as String?
        neoFormVersion = findProperty("deps.neoform") as String?
        neoForgeVersion = findProperty("deps.neoforge") as String?

        // Configures client and server runs for MDG, it is not done by default
        defaultRuns()
    }

    mixin {
        configs.register("ixeris")
        if (modstitch.isModDevGradleLegacy) {
            // workaround for https://github.com/isXander/modstitch/issues/33
            registerSourceSet(ixerisSourceSet, "unnamed_mod.refmap.json")
        }
        addMixinsToModManifest = false
    }
}

tasks.register<Copy>("buildAndCollect") {
    group = "build"
    dependsOn(modstitch.finalJarTask)
    from(modstitch.finalJarTask.flatMap { it.archiveFile })
    into(rootProject.layout.buildDirectory.dir("libs"))
}

val modJar = tasks.register<Jar>("modJar") {
    from(ixerisSourceSet.output)
    archiveClassifier = "mod"
    manifest {
        attributes (
            "Automatic-Module-Name" to "me.decce.ixeris"
        )
        if (modstitch.isModDevGradleLegacy) {
            attributes.put("MixinConfigs", "ixeris.mixins.json")
        }
    }
}

msShadow {
    relocatePackage = "me.decce.ixeris.core.shadow"
}

dependencies {
    modstitch.loom {
        msShadow.dependency(files(modJar), mapOf("_do_not_relocate" to ""))
    }

    if (modstitch.isModDevGradleLegacy) {
        implementation("io.github.llamalad7:mixinextras-common:0.5.0")
        implementation("io.github.llamalad7:mixinextras-forge:0.5.0")
        modstitchJiJ("io.github.llamalad7:mixinextras-forge:0.5.0")
    }

    modstitch.moddevgradle {
        modstitchJiJ (files(modJar))

        msShadow.dependency ("net.lenni0451.classtransform:core:1.14.1", mapOf("net.lenni0451.classtransform" to "classtransform"))

        msShadow.dependency ("me.decce.ixeris:service-${project.property("required_service")}", mapOf("_do_not_relocate" to ""))
    }

    modstitchImplementation ("me.decce.ixeris", "core")
    msShadow.dependency ("me.decce.ixeris:core", mapOf("_do_not_relocate" to ""))

    // Anything else in the dependencies block will be used for all platforms.
}