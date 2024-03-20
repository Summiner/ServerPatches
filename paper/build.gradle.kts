repositories {
    mavenLocal()
    maven {
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }

    maven {
        url = uri("https://repo.codemc.io/repository/maven-releases/")
    }
}

configurations.named("shadow") {
    extendsFrom(configurations.getByName("implementation"))
}

dependencies {
    shadow("org.bstats:bstats-bukkit:3.0.2")
    shadow("dev.dejvokep:boosted-yaml:1.3")
    shadow("com.github.retrooper.packetevents:spigot:2.2.0") {
        exclude(group = "net.kyori")
    }
    compileOnly("io.papermc.paper:paper-api:1.17.1-R0.1-SNAPSHOT")
}

tasks {
    processResources {
        inputs.property("version", project.version)
        filesMatching("plugin.yml") {
            expand(getProperties())
            expand(mutableMapOf("version" to project.version))
        }
    }

    shadowJar {
        archiveFileName.set("ServerPatches-Paper.jar")
        relocate("dev.dejvokep.boostedyaml", "rs.jamie.serverpatches.libs.boostedyaml")
        relocate("org.bstats", "rs.jamie.serverpatches.libs.bstats")
        relocate("com.github.retrooper", "rs.jamie.serverpatches.libs.retrooper.com")
        relocate("io.github.retrooper", "rs.jamie.serverpatches.libs.retrooper.io")
        dependencies {
            exclude("net.kyori")
        }
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_16
    targetCompatibility = JavaVersion.VERSION_16
}
