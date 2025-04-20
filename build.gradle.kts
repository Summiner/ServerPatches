import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("com.github.johnrengelman.shadow") version "8.1.1" apply false
    `java-library`
}

subprojects {
    apply {
        plugin("java-library")
        plugin("com.github.johnrengelman.shadow")
    }

    version = "1.0.6"
    java.sourceCompatibility = JavaVersion.VERSION_16

    repositories {
        mavenLocal()
        maven {
            url = uri("https://repo.maven.apache.org/maven2/")
        }
    }

    tasks {
        val shadowJar = named<ShadowJar>("shadowJar") {
            configurations = listOf(project.configurations.getByName("shadow"))
            archiveFileName.set("ServerPatches.jar")
        }

        build {
            dependsOn(shadowJar)
        }
    }

    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
    }
}