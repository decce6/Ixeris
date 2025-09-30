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
    minecraftVersion.set(findProperty("deps.minecraft") as String)
    javaVersion.set(javaLanguageVersion)

    metadata {
        modId.set(findProperty("modid") as String)
        modName.set(findProperty("mod_name") as String)
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

        msShadow.dependency ("me.decce.ixeris:service-${project.property("required_service")}", mapOf("_do_not_relocate" to ""))
    }

    modstitchImplementation ("me.decce.ixeris", "core")
    msShadow.dependency ("me.decce.ixeris:core", mapOf("_do_not_relocate" to ""))
}