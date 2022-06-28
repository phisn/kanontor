plugins {
    application
    kotlin("jvm") version "1.7.0"
    kotlin("kapt") version "1.7.0"
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "com.schmisn"
version = "0.0.1"

application {
    mainClass.set("com.schmisn.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

dependencies {
    implementation(project(":core"))
}
