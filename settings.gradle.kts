pluginManagement {
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

        gradlePluginPortal()
        mavenCentral()

        maven("https://maven.fabricmc.net/")
        maven("https://maven.neoforged.net/releases/")
        maven("https://maven.minecraftforge.net/")
        maven("https://maven.kikugie.dev/releases")
        maven("https://maven.architectury.dev/")
        exclusiveMaven("Sponge", "org.spongepowered", "https://repo.spongepowered.org/repository/maven-public")
    }
}

plugins {
    id("dev.kikugie.stonecutter") version "0.7.10"
}

val targetVersions = if (extra.has("target_versions")) extra["target_versions"].toString().split(",") else null
val targetLoaders = if (extra.has("target_loaders")) extra["target_loaders"].toString().split(",") else null
val defaultVersion = "1.21.10-fabric"

fun shouldBuildForVersion(version: String) : Boolean {
    if (targetVersions == null) {
        return true
    }
    return targetVersions.any { stonecutter.eval(version, it) }
}

fun shouldBuildForLoader(loader: String) : Boolean {
    if (targetLoaders == null) {
        return true
    }
    return targetLoaders.any { loader == it }
}

fun shouldBuild(version: String, loader: String) : Boolean{
    if (defaultVersion.substringBefore('-') == version && defaultVersion.substringAfter('-') == loader) {
        return true; // FIXME
    }
    return shouldBuildForVersion(version) && shouldBuildForLoader(loader)
}

stonecutter {
    kotlinController = true
    centralScript = "build.gradle.kts"
    create(rootProject) {
        fun optionallyInclude(loader: String, script: String, versions: Iterable<String>) {
            versions.forEach {
                if (shouldBuild(it, loader)) {
                    version("$it-$loader", it).buildscript("build.$script.gradle.kts")
                }
                else {
                    println("Skipped $it-$loader")
                }
            }
        }
        fun fabric(versions: Iterable<String>) {
            optionallyInclude("fabric", "loom", versions)
        }
        fun neoforge(versions: Iterable<String>) {
            optionallyInclude("neoforge", "mdg", versions)
        }
        fun forge(versions: Iterable<String>) {
            optionallyInclude("forge", "fg", versions)
        }
        
        fabric (listOf("1.21.10", "1.21.8", "1.21.1", "1.20.4", "1.20.1"))
        neoforge (listOf("1.21.10", "1.21.8", "1.21.1"))
        forge (listOf("1.21.10", "1.21.8", "1.21.1", "1.20.1"))

        // This is the default target.
        // https://stonecutter.kikugie.dev/stonecutter/guide/setup#settings-settings-gradle-kts
        vcsVersion = defaultVersion
    }
}

includeBuild("core")
if (shouldBuildForLoader("neoforge")) {
    if (shouldBuildForVersion("<1.21.9")) {
        includeBuild("service-neoforge")
    }
    if (shouldBuildForVersion(">=1.21.9")) {
        includeBuild("service-neoforge-v2")
    }
}

rootProject.name = "ixeris"
