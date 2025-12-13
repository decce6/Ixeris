import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import net.fabricmc.loom.task.RemapJarTask

plugins {
    id("me.decce.ixeris.gradle.ixeris-common-conventions")
    id("dev.architectury.loom") version "1.13-SNAPSHOT"
    id("com.gradleup.shadow")
    id("me.modmuss50.mod-publish-plugin")
}

fun prop(name: String) = if (hasProperty(name)) findProperty(name) as String else throw IllegalArgumentException("$name not found")

val ixerisSourceSet = sourceSets["ixeris"]
val java8SourceSet = sourceSets["java8"]

// Need to shadow MixinExtras in <1.18.2
val jijMixinExtras = stonecutter.eval(stonecutter.current.version, ">=1.18.2")

dependencies {
    minecraft("com.mojang:minecraft:${prop("deps.minecraft")}")
    mappings(loom.officialMojangMappings())
    forge("net.minecraftforge:forge:${prop("deps.minecraft")}-${prop("deps.forge")}")

    shade(files(ixerisSourceSet.output))

    annotationProcessor("io.github.llamalad7:mixinextras-common:0.5.0")
    implementation("io.github.llamalad7:mixinextras-common:0.5.0")
    if (jijMixinExtras) {
        include("io.github.llamalad7:mixinextras-forge:0.5.0")
        implementation("io.github.llamalad7:mixinextras-forge:0.5.0")
    }
    else {
        shade("io.github.llamalad7:mixinextras-common:0.5.0")
    }

    // TODO: shadow these in the service projects so we can minimize them
    listOf("net.lenni0451.classtransform:core:${prop("deps.classtransform")}", "net.lenni0451:Reflect:${prop("deps.reflect")}").forEach {
        implementation(it)
        shade(it) {
            isTransitive = false
        }
    }
}

loom {
    mixin {
        add(ixerisSourceSet, "ixeris.mixins.refmap.json")
        useLegacyMixinAp = true
    }
    createRemapConfigurations(ixerisSourceSet)
}

tasks {
    named<Jar>("jar") {
        archiveClassifier = "slim"
    }

    named<ShadowJar>("shadowJar") {
        if (!jijMixinExtras) {
            relocate("com.llamalad7.mixinextras", "me.decce.ixeris.shadow.mixinextras")
            mergeServiceFiles()
        }
        exclude ("/META-INF/versions/21/**")
        exclude ("/META-INF/versions/24/**")
        from (java8SourceSet.output)
    }

    named<RemapJarTask>("remapJar") {
        dependsOn(shadowJar)
        inputFile = shadowJar.flatMap { it.archiveFile }
        archiveClassifier = ""
        manifest.attributes("MixinConfigs" to "ixeris.mixins.json")
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