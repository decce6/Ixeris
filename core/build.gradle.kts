plugins {
    id("java")
}

group = "me.decce.ixeris"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

var mainSourceSet = sourceSets.named("main").get()

var java8SourceSet = sourceSets.create("java8") {
    compileClasspath += mainSourceSet.compileClasspath
    runtimeClasspath += mainSourceSet.runtimeClasspath
}

sourceSets.named("main") {
    compileClasspath += java8SourceSet.output
    runtimeClasspath += java8SourceSet.output
}

repositories {
    mavenCentral()
    maven {
        url = uri("https://maven.neoforged.net/releases")
    }
    maven {
        url = uri("https://maven.minecraftforge.net/")
    }
}

dependencies {
    compileOnly ("net.lenni0451.classtransform:core:1.14.1")
    compileOnly ("net.lenni0451.classtransform:mixinstranslator:1.14.1")
    compileOnly ("net.lenni0451.classtransform:mixinsdummy:1.14.1")

    // We don't depend on Minecraft in this module but we still need these libraries.
    compileOnly ("it.unimi.dsi:fastutil:8.5.9")
    compileOnly ("com.google.code.gson:gson:2.10")
    compileOnly ("com.google.guava:guava:31.1-jre")
    compileOnly ("org.apache.logging.log4j:log4j-core:2.19.0")
    compileOnly ("org.lwjgl:lwjgl:3.3.6")
    compileOnly ("org.lwjgl:lwjgl-glfw:3.3.6")
    compileOnly ("cpw.mods:modlauncher:10.0.9")
    compileOnly ("cpw.mods:bootstraplauncher:1.1.2")
    // compileOnly ("cpw.mods:securejarhandler:2.1.10")
    compileOnly ("net.minecraftforge:securemodules:2.2.21")

    compileOnly("com.electronwill.night-config:core:3.8.2")
    compileOnly("com.electronwill.night-config:toml:3.8.2")
}

tasks {
    named<JavaCompile>("compileJava8Java") {
        sourceCompatibility = JavaVersion.VERSION_1_8.toString()
        targetCompatibility = JavaVersion.VERSION_1_8.toString()
    }

    named<Jar>("jar") {
        from(java8SourceSet.output)
    }
}