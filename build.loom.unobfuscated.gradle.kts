import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("me.decce.ixeris.gradle.ixeris-common-conventions")
    id("net.fabricmc.fabric-loom") version "1.14-SNAPSHOT"
    id("com.gradleup.shadow")
    id("me.modmuss50.mod-publish-plugin")
}

fun prop(name: String) = if (hasProperty(name)) findProperty(name) as String else throw IllegalArgumentException("$name not found")

val ixerisSourceSet = sourceSets["ixeris"]
val java8SourceSet = sourceSets["java8"]

loom {
    mixin {
        add(ixerisSourceSet, "ixeris.mixins.refmap.json")
    }
}

dependencies {
    minecraft("com.mojang:minecraft:${prop("deps.minecraft")}")
    implementation("net.fabricmc:fabric-loader:0.17.2")

    shade(files(ixerisSourceSet.output))

    shade("com.electronwill.night-config:core:3.8.2")
    shade("com.electronwill.night-config:toml:3.8.2")
}

tasks {
    named<Jar>("jar") {
        archiveClassifier = "slim"
    }

    named<ShadowJar>("shadowJar") {
        archiveClassifier = ""
        from (java8SourceSet.output)
        relocate("com.electronwill.nightconfig", "me.decce.ixeris.core.shadow.nightconfig")
    }

    named<ProcessResources>("processResources") {
        from (layout.settingsDirectory.file("thirdparty/licenses/LICENSE-NightConfig"))
    }

    register<Copy>("buildAndCollect") {
        group = "build"
        dependsOn(shadowJar)
        from(shadowJar.flatMap { it.archiveFile })
        into(rootProject.layout.buildDirectory.dir("libs"))
    }
}

publishMods {
    file = tasks.shadowJar.get().archiveFile
}