import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import net.fabricmc.loom.task.RemapJarTask

plugins {
    id("me.decce.ixeris.gradle.ixeris-common-conventions")
    id("fabric-loom") version "1.13-SNAPSHOT"
    id("com.gradleup.shadow")
    id("me.modmuss50.mod-publish-plugin")
}

fun prop(name: String) = if (hasProperty(name)) findProperty(name) as String else throw IllegalArgumentException("$name not found")

val ixerisSourceSet = sourceSets["ixeris"]
val java8SourceSet = sourceSets["java8"]

loom {
    createRemapConfigurations(ixerisSourceSet)
}

dependencies {
    minecraft("com.mojang:minecraft:${prop("deps.minecraft")}")
    mappings(loom.officialMojangMappings())
    modImplementation("net.fabricmc:fabric-loader:0.17.2")

    shade(files(ixerisSourceSet.output))

    shade("com.electronwill.night-config:core:3.8.2")
    shade("com.electronwill.night-config:toml:3.8.2")
}

tasks {
    named<Jar>("jar") {
        archiveClassifier = "slim"
    }

    named<ShadowJar>("shadowJar") {
        from (java8SourceSet.output)
        relocate("com.electronwill.nightconfig", "me.decce.ixeris.core.shadow.nightconfig")
    }

    named<ProcessResources>("processResources") {
        from (layout.settingsDirectory.file("thirdparty/licenses/LICENSE-NightConfig"))
    }

    named<RemapJarTask>("remapJar") {
        dependsOn(shadowJar)
        inputFile = shadowJar.flatMap { it.archiveFile }
        archiveClassifier = ""
    }

    register<Copy>("buildAndCollect") {
        group = "build"
        dependsOn(remapJar)
        from(remapJar.flatMap { it.archiveFile })
        into(rootProject.layout.buildDirectory.dir("libs"))
    }
}

publishMods {
    file = tasks.remapJar.get().archiveFile
}