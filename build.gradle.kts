import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version ("8.1.1")
}

group = "com.github.itsanikethere"
version = "1.0.0"

repositories {
    mavenCentral()
    maven {
        setUrl("https://jitpack.io")
    }
}

dependencies {
    implementation("com.github.devoxin:lavaplayer:1.8.0")
    implementation("org.javacord:javacord:3.8.0")
    implementation("org.slf4j:slf4j-api:2.0.9")
    runtimeOnly("org.apache.logging.log4j:log4j-slf4j2-impl:2.22.0")
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks {
    withType<ShadowJar> {
        manifest {
            attributes["Main-Class"] = "com.github.itsanikethere.botler.Main"
        }
    }
}

tasks.test {
    useJUnitPlatform()
}