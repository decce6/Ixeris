plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    gradlePluginPortal()
    maven("https://maven.kikugie.dev/releases")
}

dependencies {
    implementation("com.gradleup.shadow:shadow-gradle-plugin:9.2.2")
    implementation("me.modmuss50:mod-publish-plugin:1.1.0")
    implementation("dev.kikugie:stonecutter:0.7.10")
}
