plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("kapt") version "1.9.22"
    kotlin("plugin.jpa") version "1.9.25"
    kotlin("plugin.serialization") version "1.9.25" apply false
    kotlin("plugin.spring") version "1.9.25"
    id("org.springframework.boot") version "3.4.3" apply false
    id("io.spring.dependency-management") version "1.1.7"
}

group = "xyz.xszq"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

repositories {
    mavenCentral()
}

allprojects {
    extra["springBootVersion"] = "3.4.3"
    extra["springCloudVersion"] = "2024.0.0"
    extra["alibabaCloudVersion"] = "2022.0.0.0-RC2"
}

val excludedTasks = listOf("common")
subprojects {
    group = "xyz.xszq.bang_video"
    version = "0.0.1-SNAPSHOT"
    if (this.name !in excludedTasks) {
        apply(plugin = "org.springframework.boot")
    }
    tasks.withType<Test> {
        useJUnitPlatform()
    }
}
tasks.forEach {
    it.enabled = false
}