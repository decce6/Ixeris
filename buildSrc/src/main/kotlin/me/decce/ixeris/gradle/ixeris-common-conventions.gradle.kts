package me.decce.ixeris.gradle
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import dev.kikugie.stonecutter.build.StonecutterBuildExtension
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.named
import java.nio.file.Files

plugins {
    `java-library`
    id("com.gradleup.shadow")
    id("me.modmuss50.mod-publish-plugin")
}

fun prop(name: String) = if (hasProperty(name)) findProperty(name) as String else throw IllegalArgumentException("$name not found")
val shade = configurations.create("shade")

val platform = prop("deps.platform")
fun fullModVersion() = "${prop("mod_version")}+${prop("deps.minecraft")}-${platform}"

val stonecutter = project.extensions.getByType<StonecutterBuildExtension>()
val mcVersion = stonecutter.current.version
val javaVersion =
    if (stonecutter.eval(mcVersion, ">=1.20.5")) 21
    else if (stonecutter.eval(mcVersion, ">=1.18")) 17
    else 17 // TODO: maybe support Java 8 for 1.16?

java {
    sourceCompatibility = JavaVersion.toVersion(javaVersion)
    targetCompatibility = JavaVersion.toVersion(javaVersion)
}

version = fullModVersion()
group = prop("maven_group")
base {
    archivesName = prop("mod_name")
}

val ixerisSourceSet = sourceSets.create("ixeris") {
    compileClasspath += sourceSets["main"].compileClasspath
    runtimeClasspath += sourceSets["main"].runtimeClasspath
    annotationProcessorPath += sourceSets["main"].annotationProcessorPath
}

val java8SourceSet = sourceSets.create("java8") {
    compileClasspath += sourceSets["main"].compileClasspath + ixerisSourceSet.output
    runtimeClasspath += sourceSets["main"].runtimeClasspath + ixerisSourceSet.output
}

// Compile the mixin plugin and mod entrypoints with Java 8 so we can disable the mod when the Java in use is older than 17
tasks.named<JavaCompile>("compileJava8Java") {
    sourceCompatibility = JavaVersion.VERSION_1_8.toString()
    targetCompatibility = JavaVersion.VERSION_1_8.toString()
}

dependencies {
    if (stonecutter.eval(stonecutter.current.version, "<1.19")) {
        // These versions of Minecraft use an older version of LWJGL which misses some functions, thus causing one of our transformers (in the glfw_threading_330 package) to fail compilation
        // We add this here to make them compile; the relevant code is disabled at runtime
        compileOnly("org.lwjgl:lwjgl-glfw:3.3.1")
    }

    implementation("me.decce.ixeris:core")
    shade("me.decce.ixeris:core")
}

fun fetchLatestChangelog() : String {
    val str = Files.readString(layout.settingsDirectory.file("CHANGELOG.md").asFile.toPath())
    val i = str.indexOf('\n') + 2
    val r = str.indexOf("## ", i + 1)
    return str.substring(i, r - 2)
}

fun supportedVersionFabric() : String {
    var str = ""
    if (project.hasProperty("minecraft_supported_from")) {
        str += ">=${prop("minecraft_supported_from")}"
    }
    if (project.hasProperty("minecraft_supported_to")) {
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

repositories {
    fun exclusiveMaven(mavenName: String, group: String, mavenUrl: String) {
        exclusiveContent {
            forRepository {
                maven {
                    name = mavenName
                    url = uri(mavenUrl)
                }
            }
            filter {
                includeGroupAndSubgroups(group)
            }
        }
    }
    exclusiveMaven("Sponge", "org.spongepowered", "https://repo.spongepowered.org/repository/maven-public")
    maven {
        name = "lenni0451"
        url = uri("https://maven.lenni0451.net/snapshots")
    }
}

tasks {
    named<ShadowJar>("shadowJar") {
        archiveClassifier = "fat"
        configurations = listOf(shade)
        relocate("net.lenni0451.classtransform", "me.decce.ixeris.core.shadow.classtransform")
        relocate("net.lenni0451.reflect", "me.decce.ixeris.core.shadow.reflect")
        if (platform != "fabric") {
            exclude ("ixeris.core.mixins.json")
        }
    }

    withType<ProcessResources> {
        if (platform != "fabric") exclude("**/fabric.mod.json")
        if (platform != "neoforge") exclude("**/neoforge.mods.toml")
        if (platform != "forge") exclude("**/mods.toml", "**/pack.mcmeta")
        val propMap = mutableMapOf<String, Any>().apply {
            project.properties.forEach { k, v -> put(k.toString(), v.toString()) }
            put("mod_version_full", fullModVersion())
            put("minecraft_supported_fabric", supportedVersionFabric())
            put("minecraft_supported_forge", supportedVersionForge())
            put("java_version", javaVersion)
        }
        inputs.property("propMap", propMap)
        filesMatching(listOf("**/fabric.mod.json", "**/neoforge.mods.toml", "**/mods.toml", "**/pack.mcmeta")) {
            expand(propMap)
        }
    }

    named<ProcessResources>("processResources") {
        from (layout.settingsDirectory.file("LICENSE"))
    }
}

publishMods {
    type = STABLE
    version = fullModVersion()
    dryRun = providers.environmentVariable("CURSEFORGE_TOKEN").getOrNull() == null || providers.environmentVariable("MODRINTH_TOKEN").getOrNull() == null
    changelog = fetchLatestChangelog()
    displayName = "${prop("mod_name")} ${fullModVersion()}"
    modLoaders.add(prop("deps.platform"))
    if (prop("deps.platform") == "fabric") {
        modLoaders.add("quilt")
    }
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