import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "2.4.0"
    id("net.fabricmc.fabric-loom") version "1.17-SNAPSHOT"
}

version = project.property("mod_version") as String
group = project.property("maven_group") as String
val midnightLib = "eu.midnightdust:midnightlib:${project.property("midnight_lib_version")}"

base {
    archivesName.set(project.property("archives_base_name") as String)
}

loom {
    runConfigs {
        named("client") {
            vmArg("-Ddevauth.enabled=true")
        }
    }
    accessWidenerPath = file("src/main/resources/eternity.accesswidener")
}

val targetJavaVersion = 25
java {
    toolchain.languageVersion = JavaLanguageVersion.of(targetJavaVersion)
    withSourcesJar()
}

repositories {
    maven("https://maven.terraformersmc.com/releases" ) {
        name = "Modrinth"
    }
    maven("https://maven.midnightdust.eu/releases")
    maven("https://pkgs.dev.azure.com/djtheredstoner/DevAuth/_packaging/public/maven/v1")
}

dependencies {
    minecraft("com.mojang:minecraft:26.2")
    implementation("net.fabricmc:fabric-loader:0.19.3")
    implementation("net.fabricmc:fabric-language-kotlin:1.13.12+kotlin.2.4.0")
    implementation("net.fabricmc.fabric-api:fabric-api:0.152.2+26.2")
    implementation("com.terraformersmc:modmenu:20.0.0-beta.4")

    runtimeOnly("me.djtheredstoner:DevAuth-fabric:1.2.2")
}
tasks.processResources {
    inputs.property("version", project.version)
    inputs.property("minecraft_version", project.property("minecraft_version") as String)
    inputs.property("loader_version", project.property("loader_version") as String)
    filteringCharset = "UTF-8"

    filesMatching("fabric.mod.json") {
        expand(
            "version" to project.version,
            "minecraft_version" to (project.property("minecraft_version") as String),
            "loader_version" to (project.property("loader_version") as String)
        )
    }
}

tasks.jar {
    from("LICENSE") {
        rename { "${it}_${project.base.archivesName.get()}" }
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
    options.release.set(targetJavaVersion)
}

tasks.withType<KotlinCompile>().configureEach {
    compilerOptions.jvmTarget.set(JvmTarget.fromTarget(targetJavaVersion.toString()))
}

tasks.assemble.get().dependsOn(tasks.jar)