import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import net.fabricmc.loom.task.RemapJarTask

plugins {
    id("fabric-loom") version "1.11-SNAPSHOT"
    id("com.gradleup.shadow")
    id("me.modmuss50.mod-publish-plugin")
}

fun prop(name: String) = if (hasProperty(name)) findProperty(name) as String else throw IllegalArgumentException("$name not found")
val shade = configurations.create("shade")

fun javaVersion() : Int = if (stonecutter.eval(stonecutter.current.version, ">=1.20.5")) 21 else 17
java.toolchain.languageVersion = JavaLanguageVersion.of(javaVersion())

base {
    archivesName = prop("mod_name")
}

val ixerisSourceSet = sourceSets.create("ixeris")
sourceSets {
    named ("ixeris") {
        compileClasspath += sourceSets["main"].compileClasspath
        runtimeClasspath += sourceSets["main"].runtimeClasspath
        annotationProcessorPath += sourceSets["main"].annotationProcessorPath
    }
}

loom {
    mixin {
        add(ixerisSourceSet, "ixeris.mixins.refmap.json")
    }
    createRemapConfigurations(ixerisSourceSet)
}

dependencies {
    minecraft("com.mojang:minecraft:${prop("deps.minecraft")}")
    mappings(loom.officialMojangMappings())
    modImplementation("net.fabricmc:fabric-loader:0.17.2")

    implementation("me.decce.ixeris:core")
    shade("me.decce.ixeris:core")
    shade(files(ixerisSourceSet.output))

    include("com.electronwill.night-config:core:3.8.2")
    include("com.electronwill.night-config:toml:3.8.2")
}

tasks {
    named<Jar>("jar") {
        archiveClassifier = "slim"
    }

    named<ShadowJar>("shadowJar") {
        archiveClassifier = "fat"
        configurations = listOf(shade)
        relocate("net.lenni0451.classtransform", "me.decce.ixeris.core.shadow.classtransform")
        relocate("net.lenni0451.reflect", "me.decce.ixeris.core.shadow.reflect")
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