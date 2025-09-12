pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        maven("https://maven.fabricmc.net/")
        maven("https://maven.neoforged.net/releases/")
        maven("https://maven.minecraftforge.net/")
        maven("https://maven.kikugie.dev/releases")
    }
}

plugins {
    id("dev.kikugie.stonecutter") version "0.7.10"
}

stonecutter {
    kotlinController = true
    centralScript = "build.gradle.kts"
    create(rootProject) {
        fun addVersionsForLoader(loader: String, versions: Iterable<String>) {
            versions.forEach { version("$it-$loader", it) }
        }
        fun fabric(versions: Iterable<String>) {
            addVersionsForLoader("fabric", versions)
        }
        fun forge(versions: Iterable<String>) {
            addVersionsForLoader("forge", versions)
        }
        fun neoforge(versions: Iterable<String>) {
            addVersionsForLoader("neoforge", versions)
        }
        
        fabric (listOf("latest", "1.21.1", "1.20.1"))
        forge (listOf("1.20.1"))
        neoforge (listOf("latest", "1.21.1"))

        // This is the default target.
        // https://stonecutter.kikugie.dev/stonecutter/guide/setup#settings-settings-gradle-kts
        vcsVersion = "latest-fabric"
    }
}

includeBuild("core")
includeBuild("service-neoforge")
includeBuild("service-forge")

rootProject.name = "ixeris"
