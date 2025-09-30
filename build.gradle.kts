plugins {
    id("dev.isxander.modstitch.base") version "0.7.0-unstable"
    id("dev.isxander.modstitch.shadow") version "0.7.0-unstable"
}

val loader = when {
    modstitch.isLoom -> "fabric"
    modstitch.isModDevGradleRegular -> "neoforge"
    else -> "unknown"
}

fun prop(name: String) = findProperty(name) as String

fun fullModVersion() = "${prop("mod_version")}+${prop("deps.minecraft")}-$loader"

val javaLanguageVersion = if (stonecutter.eval(stonecutter.current.version, ">=1.20.5")) 21 else 17
java.toolchain.languageVersion = JavaLanguageVersion.of(javaLanguageVersion)

version = fullModVersion()
group = prop("maven_group")

tasks.withType<ProcessResources> {
    if (!modstitch.isLoom) exclude("**/fabric.mod.json")
    if (!modstitch.isModDevGradleRegular) exclude ("**/neoforge.mods.toml")
    val propMap = mutableMapOf<String, Any>().apply {
        project.properties.forEach { k, v -> put(k.toString(), v.toString())}
        put ("mod_version_full", fullModVersion())
        put ("java_version", javaLanguageVersion)
    }
    inputs.property("propMap", propMap)
    filesMatching(listOf("**/fabric.mod.json", "**/neoforge.mods.toml")) {
        expand(propMap)
    }
}

(tasks.getByName("processResources") as ProcessResources).apply {
    from (layout.settingsDirectory.file("LICENSE"))
}

// Source set acrobatics to achieve mod-in-service structure on NeoForge
val ixerisSourceSet = sourceSets.create("ixeris");
sourceSets {
    named ("ixeris") {
        // NeoForge: include mod manifest in both the service JAR and the mod JAR (inside the service JAR), so metadata
        // can be read both by NeoForge and by launchers, etc.
        resources.srcDir(layout.settingsDirectory.dir("src/main/resources"))

        compileClasspath += sourceSets["main"].compileClasspath
        runtimeClasspath += sourceSets["main"].runtimeClasspath
        annotationProcessorPath += sourceSets["main"].annotationProcessorPath
    }
}

modstitch {
    minecraftVersion.set(prop("deps.minecraft"))
    javaVersion.set(javaLanguageVersion)

    metadata {
        modId.set(prop("modid"))
        modName.set(prop("mod_name"))
        modVersion.set(project.version as String)
    }

    // Fabric Loom (Fabric)
    loom {
        fabricLoaderVersion = "0.17.2"
    }

    // ModDevGradle (NeoForge, Forge, Forgelike)
    moddevgradle {
        neoForgeVersion = findProperty("deps.neoforge") as String?

        // Configures client and server runs for MDG, it is not done by default
        defaultRuns()
    }

    mixin {
        configs.register("ixeris")
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
    manifest.attributes (
        "Automatic-Module-Name" to "me.decce.ixeris"
    )
}

if (modstitch.isModDevGradle) {
    modstitch.finalJarTask {
        manifest.attributes (
            "Automatic-Module-Name" to "me.decce.ixeris.neoforge"
        )
    }
}

msShadow {
    relocatePackage = "me.decce.ixeris.core.shadow"
}

repositories {
    maven {
        name = "lenni0451"
        url = uri("https://maven.lenni0451.net/snapshots")
    }
}

dependencies {
    modstitch.loom {
        msShadow.dependency(files(modJar), mapOf("_do_not_relocate" to ""))
    }

    modstitch.moddevgradle {
        modstitchJiJ (files(modJar))

        msShadow.dependency ("net.lenni0451.classtransform:core:1.14.2-SNAPSHOT", mapOf("net.lenni0451.classtransform" to "classtransform"))
        msShadow.dependency ("net.lenni0451.classtransform:mixinstranslator:1.14.2-SNAPSHOT", mapOf("net.lenni0451.classtransform" to "classtransform"))

        msShadow.dependency ("me.decce.ixeris:service-${prop("required_service")}", mapOf("_do_not_relocate" to ""))
    }

    modstitchImplementation ("me.decce.ixeris", "core")
    msShadow.dependency ("me.decce.ixeris:core", mapOf("_do_not_relocate" to ""))
}