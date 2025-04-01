//val alibabaCloudVersion: String = "2023.0.3.2"
val springCloudVersion: String = "2024.0.0"
plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    id("io.spring.dependency-management") version "1.1.7"
    id("org.springframework.boot") version "3.4.3"
}

repositories {
    maven("https://maven.aliyun.com/repository/public/")
    mavenCentral()
}

dependencies {
//    implementation("com.alibaba.cloud:spring-cloud-starter-alibaba-nacos-discovery")
    implementation("com.google.code.gson:gson:2.11.0")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.springframework.cloud:spring-cloud-starter")
    implementation("org.springframework.cloud:spring-cloud-starter-gateway")
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
//    implementation("org.springframework.cloud:spring-cloud-starter-loadbalancer")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:$springCloudVersion")
//        mavenBom("com.alibaba.cloud:spring-cloud-alibaba-dependencies:$alibabaCloudVersion")
    }
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

graalvmNative {
    metadataRepository {
        version = "0.3.14"
    }
    binaries {
        named("main") {
            buildArgs.add("-H:ReflectionConfigurationFiles=../../../../reflect-config.json")
            buildArgs.add("-H:ResourceConfigurationFiles=../../../../resource-config.json")
        }
    }
}