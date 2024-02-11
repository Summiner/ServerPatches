import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("com.github.johnrengelman.shadow") version "8.1.1"
    `java-library`
    `maven-publish`
}

repositories {
    mavenLocal()
    maven {
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }

    maven {
        url = uri("https://repo.maven.apache.org/maven2/")
    }

    maven {
        url = uri("https://repo.codemc.io/repository/maven-releases/")
    }
}

dependencies {
    implementation("org.bstats:bstats-bukkit:3.0.2")
    implementation("com.github.retrooper.packetevents:spigot:2.2.0")
    compileOnly("io.papermc.paper:paper-api:1.17.1-R0.1-SNAPSHOT")
}

tasks {
    processResources {
        inputs.property("version", project.version)
        filesMatching("plugin.yml") {
            expand(getProperties())
            expand(mutableMapOf("version" to project.version))
        }
        filesMatching("Velocity.java") {
            expand(getProperties())
            expand(mutableMapOf("version" to project.version))
        }
    }
    
    named<ShadowJar>("shadowJar") {
        
        archiveBaseName.set("Server-Patches")
        mergeServiceFiles()
        relocate("org.bstats", "summiner.serverpatches.bstats")
        relocate("com.github.retrooper", "summiner.serverpatches.retrooper.com")
        relocate("io.github.retrooper", "summiner.serverpatches.retrooper.io")
        minimize{
            exclude(dependency("com.comphenix.protocol:.*"))
        }
    }
}

group = "Server-Patches"
version = "1.0"
description = "ServerPatches"
java.sourceCompatibility = JavaVersion.VERSION_17

publishing {
    publications.create<MavenPublication>("maven") {
        from(components["java"])
    }
}

tasks.withType<JavaCompile>() {
    options.encoding = "UTF-8"
}

tasks.withType<Javadoc>() {
    options.encoding = "UTF-8"
}