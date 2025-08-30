import java.io.FileInputStream
import java.util.Properties

plugins {
    id("dev.isxander.modstitch.base") version "0.5.12"
    id("dev.isxander.modstitch.shadow") version "0.5.12"
}

fun prop(name: String, consumer: (prop: String) -> Unit) {
    (findProperty(name) as? String?)
        ?.let(consumer)
}

val minecraft = property("deps.minecraft") as String;

group = "me.decce.ixeris"

modstitch {
    minecraftVersion = minecraft

    // Alternatively use stonecutter.eval if you have a lot of versions to target.
    // https://stonecutter.kikugie.dev/stonecutter/guide/setup#checking-versions
    javaTarget.set(Integer.valueOf(property("java_version") as String))

    // If parchment doesnt exist for a version yet you can safely
    // omit the "deps.parchment" property from your versioned gradle.properties
    parchment {
        prop("deps.parchment") { mappingsVersion = it }
    }

    // This metadata is used to fill out the information inside
    // the metadata files found in the templates folder.
    metadata {
        modId = "ixeris"
        modName = "Ixeris"

        fun <K, V> MapProperty<K, V>.populate(block: MapProperty<K, V>.() -> Unit) {
            block()
        }
    }

    // Fabric Loom (Fabric)
    loom {
        // It's not recommended to store the Fabric Loader version in properties.
        // Make sure its up to date.
        fabricLoaderVersion = "0.16.14"

        // Configure loom like normal in this block.
        configureLoom {

        }
    }

    // ModDevGradle (NeoForge, Forge, Forgelike)
    moddevgradle {
        enable {
            prop("deps.forge") { forgeVersion = it }
            prop("deps.neoform") { neoFormVersion = it }
            prop("deps.neoforge") { neoForgeVersion = it }
            prop("deps.mcp") { mcpVersion = it }
        }

        // Configures client and server runs for MDG, it is not done by default
        defaultRuns()

        // This block configures the `neoforge` extension that MDG exposes by default,
        // you can configure MDG like normal from here
        configureNeoforge {
            runs.all {
                disableIdeRun()
            }
        }
    }

    mixin {
        // You do not need to specify mixins in any mods.json/toml file if this is set to
        // true, it will automatically be generated.
        addMixinsToModManifest = false

        // Most of the time you wont ever need loader specific mixins.
        // If you do, simply make the mixin file and add it like so for the respective loader:
        // if (isLoom) configs.register("examplemod-fabric")
        // if (isModDevGradleRegular) configs.register("examplemod-neoforge")
        // if (isModDevGradleLegacy) configs.register("examplemod-forge")
    }
}

// Stonecutter constants for mod loaders.
// See https://stonecutter.kikugie.dev/stonecutter/guide/comments#condition-constants
var constraint: String = name.split("-")[1]
stonecutter {
    consts(
        "fabric" to constraint.equals("fabric"),
        "neoforge" to constraint.equals("neoforge"),
        "forge" to constraint.equals("forge"),
        "vanilla" to constraint.equals("vanilla")
    )
}

// Source set acrobatics, for achieving mod-in-service structure on NeoForge
// On NeoForge the mod is JiJed inside the service jar; on fabric it is "shadowed" to have one flat jar
val ixerisSourceSet = java.sourceSets.create("ixeris");
java.sourceSets {
    named ("ixeris") {
        java.setSrcDirs(listOf("src/main/java"))
        resources.setSrcDirs(listOf("src/main/resources"))
        compileClasspath += sourceSets["main"].compileClasspath
        runtimeClasspath += sourceSets["main"].runtimeClasspath
    }
    named ("main") {
        java.setSrcDirs(listOf("src/dummy/java"))
        resources.setSrcDirs(listOf("src/dummy/resources"))
    }
}

val modJar = tasks.register<Jar>("modJar") {
    from(ixerisSourceSet.output)
    archiveClassifier = "mod"
    manifest {
        attributes (
            "Automatic-Module-Name" to "me.decce.ixeris"
        )
    }
}

fun getModVersion(version : String): String {
    val sb = StringBuilder(version)
    sb.append("+").append(property("deps.minecraft"))
    sb.append("-").append(when (property("modstitch.platform") as String) {
        "loom" -> "fabric"
        "moddevgradle" -> "neoforge"
        else -> "unknown"
    })
    return sb.toString()
}

tasks.withType<ProcessResources> {
    outputs.upToDateWhen { false }
    val fabric = "**/fabric.mod.json"
    val forge = "**/mods.toml"
    val neoforge = "**/neoforge.mods.toml"
    when (project.property("modstitch.platform") as String) {
        "loom" -> exclude(forge, neoforge)
        "moddevgradle" -> exclude(fabric, forge)
    }
    filesMatching(listOf(fabric, forge, neoforge)) {
        // TODO: fix configuration-cache
        val propfile = resources.text.fromFile(layout.settingsDirectory.file("mod.properties"))
        val prop = Properties().apply {
            FileInputStream(propfile.asFile()).use { load(it) }
        }
        val propMap = mutableMapOf<String, Any>().apply {
            prop.forEach { k, v -> put(k.toString(), v) }
            project.properties.forEach { k, v -> put(k.toString(), v.toString())}
            project.version = getModVersion(prop["mod_version"] as String)
            put ("mod_version_full", project.version)
        }
        expand(propMap)
    }
}

(tasks.getByName("processResources") as ProcessResources).apply {
    from (layout.settingsDirectory.dir("thirdparty")) {
        into ("thirdparty")
    }
    from (layout.settingsDirectory.file("LICENSE"))
}

msShadow {
    relocatePackage = "me.decce.ixeris.core.shadow"
}

// All dependencies should be specified through modstitch's proxy configuration.
// Wondering where the "repositories" block is? Go to "stonecutter.gradle.kts"
// If you want to create proxy configurations for more source sets, such as client source sets,
// use the modstitch.createProxyConfigurations(sourceSets["client"]) function.
dependencies {
    modstitch.loom {
        msShadow.dependency(files(modJar), mapOf("_do_not_relocate" to ""))
    }

    modstitch.moddevgradle {
        modstitchJiJ (files(modJar))

        msShadow.dependency ("net.lenni0451.classtransform:core:1.14.1", mapOf("net.lenni0451.classtransform" to "classtransform"))

        modstitchImplementation ("me.decce.ixeris", "service")
        msShadow.dependency ("me.decce.ixeris:service", mapOf("_do_not_relocate" to ""))
    }

    modstitchImplementation ("me.decce.ixeris", "core")
    msShadow.dependency ("me.decce.ixeris:core", mapOf("_do_not_relocate" to ""))

    // Anything else in the dependencies block will be used for all platforms.
}