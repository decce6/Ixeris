plugins {
    id("dev.kikugie.stonecutter")
}

stonecutter active "1.21.10-fabric"

stonecutter parameters {
    constants.match(node.metadata.project.substringAfterLast('-'), "fabric", "neoforge", "forge")
    replacements.string("auto_logger") {
        direction = eval(current.version, "<=1.16.5")
        replace("org.slf4j.Logger", "org.apache.logging.log4j.Logger")
    }
}

tasks.register("publishAll") {
    group = "publishing"
    dependsOn(stonecutter.tasks.named("publishMods"))
}

tasks.register("publishAllModrinth") {
    group = "publishing"
    dependsOn(stonecutter.tasks.named("publishModrinth"))
}

tasks.register("publishAllCurseForge") {
    group = "publishing"
    dependsOn(stonecutter.tasks.named("publishCurseForge"))
}

stonecutter.tasks {
    order("publishMods")
    order("publishModrinth")
    order("publishCurseForge")
}

