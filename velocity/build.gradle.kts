plugins {
    id("xyz.jpenilla.run-velocity") version "2.3.1"
}

configurations.named("shadow") {
    extendsFrom(configurations.getByName("implementation"))
}

dependencies {
    compileOnly("com.velocitypowered:velocity-api:3.4.0-SNAPSHOT")
    annotationProcessor("com.velocitypowered:velocity-api:3.4.0-SNAPSHOT")
    shadow("org.bstats:bstats-velocity:3.0.2")
    shadow("dev.dejvokep:boosted-yaml:1.3")
    shadow("com.github.retrooper:packetevents-velocity:2.7.0")
    implementation("org.apache.commons:commons-lang3:3.17.0")
}

tasks {
    processResources {
        inputs.property("version", project.version)
        filesMatching("ServerPatchesPlugin.java") {
            expand(getProperties())
            expand(mutableMapOf("version" to project.version))
        }
    }

    shadowJar {
        archiveFileName.set("ServerPatches-Velocity.jar")
        relocate("dev.dejvokep.boostedyaml", "rs.jamie.serverpatches.libs.boostedyaml")
        relocate("org.bstats", "rs.jamie.serverpatches.libs.bstats")
        relocate("com.github.retrooper", "rs.jamie.serverpatches.libs.retrooper.com")
        relocate("io.github.retrooper", "rs.jamie.serverpatches.libs.retrooper.io")
        dependencies {
            exclude("net.kyori")
        }
    }

    runVelocity {
        velocityVersion("3.4.0-SNAPSHOT")
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
