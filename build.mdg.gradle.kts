import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("net.neoforged.moddev") version "2.0.115"
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

val modJar = tasks.register<Jar>("modJar") {
    from(ixerisSourceSet.output)
    archiveClassifier = "mod"
    manifest.attributes (
        "Automatic-Module-Name" to "me.decce.ixeris"
    )
}

neoForge {
    version = prop("deps.neoforge")
}

dependencies {
    implementation("me.decce.ixeris:core")
    shade("me.decce.ixeris:core")
    jarJar(files(modJar))
    shade("me.decce.ixeris:service-neoforge")


    listOf("net.lenni0451.classtransform:core:1.14.2-SNAPSHOT", "net.lenni0451.classtransform:mixinstranslator:1.14.2-SNAPSHOT", "net.lenni0451:Reflect:1.5.0").forEach {
        implementation(it)
        shade(it) {
            isTransitive = false
        }
    }
}

val jijShadowJar = tasks.register<Jar>("jijShadowJar") {
    from(zipTree(tasks.shadowJar.map { it.archiveFile }))
    dependsOn(tasks.shadowJar)

    from(tasks.jarJar)
    dependsOn(tasks.jarJar)

    manifest.attributes (
        "Automatic-Module-Name" to "me.decce.ixeris.${prop("deps.platform")}"
    )
}

tasks {
    named("createMinecraftArtifacts") {
        dependsOn("stonecutterGenerate")
    }

    named<Jar>("jar") {
        archiveClassifier = "slim"
        enabled = false
    }

    named<ShadowJar>("shadowJar") {
        archiveClassifier = "shade"
        configurations = listOf(shade)
        relocate("net.lenni0451.classtransform", "me.decce.ixeris.core.shadow.classtransform")
        relocate("net.lenni0451.reflect", "me.decce.ixeris.core.shadow.reflect")
    }

    assemble.get().dependsOn(jijShadowJar)

    register<Copy>("buildAndCollect") {
        group = "build"
        dependsOn(jijShadowJar)
        from(jijShadowJar.flatMap { it.archiveFile })
        into(rootProject.layout.buildDirectory.dir("libs"))
    }
}

publishMods {
    file = jijShadowJar.get().archiveFile
}