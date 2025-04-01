plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("kapt") version "1.9.22" apply false
    kotlin("plugin.serialization") version "1.9.25" apply false
    kotlin("plugin.spring") version "1.9.25"
    id("io.spring.dependency-management") version "1.1.7"
    id("org.graalvm.buildtools.native") version "0.10.6" apply false
    id("org.springframework.boot") version "3.4.3" apply false
}

group = "xyz.xszq"
version = "0.0.1-SNAPSHOT"

repositories {
    maven("https://maven.aliyun.com/repository/public/")
    mavenCentral()
}

allprojects {
    extra["springBootVersion"] = "3.4.3"
    extra["springCloudVersion"] = "2024.0.0"
}

val springbootExcluded = listOf("common")
val graalVMExcluded = listOf("common", "eureka", "gateway")
subprojects {
    group = "xyz.xszq.bang_video"
    version = "0.0.1-SNAPSHOT"
    if (this.name !in springbootExcluded) {
        apply(plugin = "org.springframework.boot")
    }
    if (this.name !in graalVMExcluded) {
        apply(plugin = "org.graalvm.buildtools.native")
    }
}

tasks.forEach {
    it.enabled = false
}