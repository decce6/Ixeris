import java.nio.file.Files

plugins {
    id("dev.isxander.modstitch.base") version "0.7.1-unstable"
    id("dev.isxander.modstitch.publishing") version "0.7.1-unstable"
    id("dev.isxander.modstitch.shadow") version "0.7.1-unstable"
}

val loader = when {
    modstitch.isLoom -> "fabric"
    modstitch.isModDevGradleRegular -> "neoforge"
    modstitch.isModDevGradleLegacy -> "forge"
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
    if (!modstitch.isModDevGradleLegacy) exclude ("**/mods.toml")
    val propMap = mutableMapOf<String, Any>().apply {
        project.properties.forEach { k, v -> put(k.toString(), v.toString())}
        put ("mod_version_full", fullModVersion())
        put ("minecraft_supported_fabric", supportedVersionFabric())
        put ("minecraft_supported_forge", supportedVersionForge())
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
        forgeVersion = findProperty("deps.forge") as String?

        // Configures client and server runs for MDG, it is not done by default
        defaultRuns()
    }

    mixin {
        configs.register("ixeris")
        registerSourceSet(ixerisSourceSet, "ixeris.refmap.json")
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
    if (modstitch.isModDevGradleLegacy) { // Forge does not load mixin from mods.toml
        manifest.attributes["MixinConfigs"] = "ixeris.mixins.json"
    }
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

    if (modstitch.isModDevGradleLegacy) {
        implementation("io.github.llamalad7:mixinextras-common:0.5.0")
        implementation("io.github.llamalad7:mixinextras-forge:0.5.0")
        modstitchJiJ("io.github.llamalad7:mixinextras-forge:0.5.0")
    }

    modstitch.moddevgradle {
        modstitchJiJ (files(modJar))

        msShadow.dependency ("net.lenni0451.classtransform:core:1.14.2-SNAPSHOT", mapOf("net.lenni0451.classtransform" to "classtransform"))
        msShadow.dependency ("net.lenni0451.classtransform:mixinstranslator:1.14.2-SNAPSHOT", mapOf("net.lenni0451.classtransform" to "classtransform"))

        if (modstitch.isModDevGradleLegacy) {
            msShadow.dependency ("net.lenni0451:Reflect:1.5.0", mapOf("net.lenni0451.reflect" to "reflect"))
        }

        msShadow.dependency ("me.decce.ixeris:service-${prop("required_service")}", mapOf("_do_not_relocate" to ""))
    }

    modstitchImplementation ("me.decce.ixeris", "core")
    msShadow.dependency ("me.decce.ixeris:core", mapOf("_do_not_relocate" to ""))
}

fun latestChangelog() : String {
    val str = Files.readString(layout.settingsDirectory.file("CHANGELOG.md").asFile.toPath())
    val i = str.indexOf('\n') + 2
    val r = str.indexOf("##", i + 1)
    return str.substring(i, r - 2)
}

msPublishing {
    mpp {
        type = STABLE
        dryRun = providers.environmentVariable("CURSEFORGE_TOKEN").getOrNull() == null || providers.environmentVariable("MODRINTH_TOKEN").getOrNull() == null
        changelog = latestChangelog()
        displayName = "${prop("mod_name")} ${project.version}"
        curseforge {
            accessToken = providers.environmentVariable("CURSEFORGE_TOKEN")
            clientRequired = true
            serverRequired = false
            projectId = "1285307"
            projectSlug = "ixeris"
            if (hasProperty("minecraft_supported_from")) {
                minecraftVersionRange {
                    start = prop("minecraft_supported_from")
                    end = if (hasProperty("minecraft_supported_to")) prop("minecraft_supported_to") else "latest"
                }
            }
            else {
                minecraftVersions.add(prop("deps.minecraft"))
            }
        }

        modrinth {
            accessToken = providers.environmentVariable("MODRINTH_TOKEN")
            projectId = "p8RJPJIC"
            if (hasProperty("minecraft_supported_from")) {
                minecraftVersionRange {
                    start = prop("minecraft_supported_from")
                    end = if (hasProperty("minecraft_supported_to")) prop("minecraft_supported_to") else "latest"
                }
            }
            else {
                minecraftVersions.add(prop("deps.minecraft"))
            }
        }

    }
}

fun supportedVersionFabric() : String {
    var str = ""
    if (hasProperty("minecraft_supported_from")) {
        str += ">=${prop("minecraft_supported_from")}"
    }
    if (hasProperty("minecraft_supported_to")) {
        str += " <=${prop("minecraft_supported_to")}"
    }
    if (str == "") {
        str = "=${prop("deps.minecraft")}"
    }
    return str;
}

fun supportedVersionForge() : String {
    var str = "["
    if (hasProperty("minecraft_supported_from")) {
        str += "${prop("minecraft_supported_from")},"
    }
    if (hasProperty("minecraft_supported_to")) {
        str += prop("minecraft_supported_to")
    }
    if (str == "[") {
        str += prop("deps.minecraft")
    }
    str += "]"
    return str;
}