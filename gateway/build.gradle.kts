val springCloudVersion: String = "2024.0.0"
plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    id("io.spring.dependency-management") version "1.1.7"
    id("org.springframework.boot") version "3.4.3"
}

group = "xyz.xszq.bang_video"
version = "0.0.1-SNAPSHOT"

repositories {
    maven("https://maven.aliyun.com/repository/public/")
    mavenCentral()
}

dependencies {
    implementation("com.google.code.gson:gson:2.11.0")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.springframework.cloud:spring-cloud-starter")
    implementation("org.springframework.cloud:spring-cloud-starter-gateway")
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:$springCloudVersion")
    }
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

//graalvmNative {
//    metadataRepository {
//        version = "0.3.14"
//    }
//    binaries {
//        named("main") {
//            buildArgs.add("-H:ReflectionConfigurationFiles=../../../../reflect-config.json")
//            buildArgs.add("-H:ResourceConfigurationFiles=../../../../resource-config.json")
//        }
//    }
//}