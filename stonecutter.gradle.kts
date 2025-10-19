import java.nio.file.Files

plugins {
    id("dev.kikugie.stonecutter")
    id("com.gradleup.shadow") version "9.2.2"
    id("me.modmuss50.mod-publish-plugin") version "0.8.+"
}

stonecutter active "1.21.9-fabric"

stonecutter parameters {
    constants.match(node.metadata.project.substringAfterLast('-'), "fabric", "neoforge", "forge")
}

var currentProject : Project? = null
fun prop(name: String) = if (currentProject?.hasProperty(name) ?: false) currentProject?.findProperty(name) as String else throw IllegalArgumentException("$name not found")
fun platform() = prop("deps.platform")
fun fullModVersion() = "${prop("mod_version")}+${prop("deps.minecraft")}-${platform()}"
fun javaVersion() : Int = if (stonecutter.eval(stonecutter.current?.version ?: throw IllegalStateException(), ">=1.20.5")) 21 else 17

fun latestChangelog() : String {
    val str = Files.readString(layout.settingsDirectory.file("CHANGELOG.md").asFile.toPath())
    val i = str.indexOf('\n') + 2
    val r = str.indexOf("## ", i + 1)
    return str.substring(i, r - 2)
}

fun supportedVersionFabric() : String {
    var str = ""
    if (hasProperty("minecraft_supported_from")) {
        str += ">=${prop("minecraft_supported_from")}"
    }
    if (hasProperty("minecraft_supported_to")) {
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

subprojects.forEach {
    currentProject = it
    it.version = fullModVersion()
    it.group = prop("maven_group")

}
subprojects {
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
    afterEvaluate {
        currentProject = project
        val platform = platform()
        tasks.withType<ProcessResources> {
            if (platform != "fabric") exclude("**/fabric.mod.json")
            if (platform != "neoforge") exclude ("**/neoforge.mods.toml")
            if (platform != "forge") exclude ("**/mods.toml", "**/pack.mcmeta")
            val propMap = mutableMapOf<String, Any>().apply {
                project.properties.forEach { k, v -> put(k.toString(), v.toString())}
                put ("mod_version_full", fullModVersion())
                put ("minecraft_supported_fabric", supportedVersionFabric())
                put ("minecraft_supported_forge", supportedVersionForge())
                put ("java_version", javaVersion())
            }
            inputs.property("propMap", propMap)
            filesMatching(listOf("**/fabric.mod.json", "**/neoforge.mods.toml", "**/mods.toml", "**/pack.mcmeta")) {
                expand(propMap)
            }
        }

        tasks.named<ProcessResources>("processResources") {
            from (layout.settingsDirectory.file("LICENSE"))
        }

        apply(plugin = "me.modmuss50.mod-publish-plugin")
        publishMods {
            type = STABLE
            version = fullModVersion()
            dryRun = providers.environmentVariable("CURSEFORGE_TOKEN").getOrNull() == null || providers.environmentVariable("MODRINTH_TOKEN").getOrNull() == null
            changelog = latestChangelog()
            displayName = "${prop("mod_name")} ${fullModVersion()}"
            modLoaders.add(prop("deps.platform"))
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
    }
}
