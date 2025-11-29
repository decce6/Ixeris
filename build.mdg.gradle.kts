plugins {
    id("me.decce.ixeris.gradle.ixeris-common-conventions")
    id("net.neoforged.moddev") version "2.0.115"
    id("com.gradleup.shadow")
    id("me.modmuss50.mod-publish-plugin")
}

fun prop(name: String) = if (hasProperty(name)) findProperty(name) as String else throw IllegalArgumentException("$name not found")

val ixerisSourceSet = sourceSets["ixeris"]

val skipArtifactCreation = if (extra.has("neoforge_skip_artifact_creation")) extra["neoforge_skip_artifact_creation"] == "true" else false

neoForge {
    version = prop("deps.neoforge")
}

val modJar = tasks.register<Jar>("modJar") {
    from(ixerisSourceSet.output)
    archiveClassifier = "mod"
    manifest.attributes (
        "Automatic-Module-Name" to "me.decce.ixeris"
    )
}

dependencies {
    jarJar(files(modJar))
    shade("me.decce.ixeris:service-${prop("required_service")}")

    listOf("net.lenni0451.classtransform:core:${prop("deps.classtransform")}", "net.lenni0451.classtransform:mixinstranslator:${prop("deps.classtransform")}").forEach {
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
        if (skipArtifactCreation) {
            enabled = false
        }
        dependsOn("stonecutterGenerate")
    }

    named<Jar>("jar") {
        archiveClassifier = "slim"
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