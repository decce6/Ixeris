plugins {
    id("dev.isxander.modstitch.base") version "0.7.0-unstable"
    id("dev.isxander.modstitch.shadow") version "0.7.0-unstable"
}

fun fullModVersion(): String {
    val sb = StringBuilder(property("mod_version") as String)
    sb.append("+").append(property("deps.minecraft"))
    sb.append("-").append(when (property("modstitch.platform") as String) {
        "loom" -> "fabric"
        "moddevgradle" -> "neoforge"
        "moddevgradle-legacy" -> "forge"
        else -> "unknown"
    })
    return sb.toString()
}

val javaLanguageVersion = if (stonecutter.eval(stonecutter.current.version, ">=1.20.5")) 21 else 17
java.toolchain.languageVersion = JavaLanguageVersion.of(javaLanguageVersion)

version = fullModVersion()
group = property("maven_group") as String

tasks.withType<ProcessResources> {
    val fabric = "**/fabric.mod.json"
    val forge = "**/mods.toml"
    val neoforge = "**/neoforge.mods.toml"
    when (project.property("modstitch.platform") as String) {
        "loom" -> exclude(forge, neoforge)
        "moddevgradle" -> exclude(fabric, forge)
        "moddevgradle-legacy" -> exclude(fabric, neoforge)
    }
    val propMap = mutableMapOf<String, Any>().apply {
        project.properties.forEach { k, v -> put(k.toString(), v.toString())}
        put ("mod_version_full", fullModVersion())
        put ("java_version", javaLanguageVersion)
    }
    inputs.property("propMap", propMap)
    filesMatching(listOf(fabric, forge, neoforge)) {
        expand(propMap)
    }
}

(tasks.getByName("processResources") as ProcessResources).apply {
    from (layout.settingsDirectory.dir("thirdparty")) {
        into ("thirdparty")
    }
    from (layout.settingsDirectory.file("LICENSE"))
}

// Source set acrobatics to achieve mod-in-service structure on NeoForge
val ixerisSourceSet = java.sourceSets.create("ixeris");
java.sourceSets {
    named ("ixeris") {
        java.setSrcDirs(listOf(layout.settingsDirectory.dir("src/main/java")))
        resources.setSrcDirs(listOf(layout.settingsDirectory.dir("src/main/resources")))
        compileClasspath += sourceSets["main"].compileClasspath
        runtimeClasspath += sourceSets["main"].runtimeClasspath
        annotationProcessorPath += sourceSets["main"].annotationProcessorPath
    }
    named ("main") {
        java.setSrcDirs(listOf(layout.settingsDirectory.dir("src/meta/java")))
        resources.setSrcDirs(listOf(layout.settingsDirectory.dir("src/meta/resources")))
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
        registerSourceSet(ixerisSourceSet, "ixeris.refmap.json")
        addMixinsToModManifest = false
    }
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

    modstitch.moddevgradle {
        modstitchJiJ (files(modJar))

        msShadow.dependency ("net.lenni0451.classtransform:core:1.14.1", mapOf("net.lenni0451.classtransform" to "classtransform"))

        msShadow.dependency ("me.decce.ixeris:service-${project.property("required_service")}", mapOf("_do_not_relocate" to ""))
    }

    modstitchImplementation ("me.decce.ixeris", "core")
    msShadow.dependency ("me.decce.ixeris:core", mapOf("_do_not_relocate" to ""))

    // Anything else in the dependencies block will be used for all platforms.
}