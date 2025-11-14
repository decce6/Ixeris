import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import net.minecraftforge.gradle.userdev.tasks.JarJar

plugins {
    id("net.minecraftforge.gradle") version "6.0.46"
    id("com.gradleup.shadow")
    id("org.spongepowered.mixin") version "0.7.+"
    id("me.modmuss50.mod-publish-plugin")
}

fun prop(name: String) = if (hasProperty(name)) findProperty(name) as String else throw IllegalArgumentException("$name not found")
val shade = configurations.create("shade")

fun javaVersion() : Int = if (stonecutter.eval(stonecutter.current.version, ">=1.20.5")) 21 else 17
java.toolchain.languageVersion = JavaLanguageVersion.of(javaVersion())

// Forge 1.20.6 and newer use official mappings at runtime, so we shouldn't reobf from official to SRG
fun needsReobf() = stonecutter.eval(stonecutter.current.version, "<=1.20.5")

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

minecraft {
    mappings("official", prop("deps.minecraft"))

    reobf = needsReobf()
}

mixin {
    add(ixerisSourceSet, "ixeris.mixins.refmap.json")
    config("ixeris.mixins.json")
}

dependencies {
    minecraft("net.minecraftforge:forge:${prop("deps.forge")}")

    annotationProcessor("org.spongepowered:mixin:0.8.5-SNAPSHOT:processor")
    annotationProcessor("io.github.llamalad7:mixinextras-common:0.5.0")
    compileOnly("io.github.llamalad7:mixinextras-common:0.5.0")
    implementation("io.github.llamalad7:mixinextras-forge:0.5.0")
    jarJar.ranged(jarJar("io.github.llamalad7:mixinextras-forge:0.5.0"), "[0.5.0,)")

    implementation("me.decce.ixeris:core")
    shade("me.decce.ixeris:core") {
        exclude ("ixeris.core.mixins.json")
    }
    shade(files(ixerisSourceSet.output))

    // TODO: shadow these in the service projects so we can minimize them
    listOf("net.lenni0451.classtransform:core:1.14.2-SNAPSHOT", "net.lenni0451:Reflect:1.5.0").forEach {
        implementation(it)
        shade(it) {
            isTransitive = false
        }
    }
}

tasks {
    named<Jar>("jar") {
        archiveClassifier = "slim"
    }

    named<ShadowJar>("shadowJar") {
        archiveClassifier = "shade"
        configurations = listOf(shade)
        relocate("net.lenni0451.classtransform", "me.decce.ixeris.core.shadow.classtransform")
        relocate("net.lenni0451.reflect", "me.decce.ixeris.core.shadow.reflect")
    }

    named<JarJar>("jarJar") {
        archiveClassifier = ""
        dependsOn(shadowJar)
        from(zipTree(shadowJar.flatMap { it.archiveFile }))
        if (needsReobf()) {
            finalizedBy("reobfJarJar")
        }
        manifest.attributes("MixinConfigs" to "ixeris.mixins.json")
    }

    register<Copy>("buildAndCollect") {
        group = "build"
        dependsOn(jarJar)
        from(jarJar.flatMap { it.archiveFile })
        into(rootProject.layout.buildDirectory.dir("libs"))
    }
}

publishMods {
    file = tasks.jarJar.get().archiveFile
}