val alibabaCloudVersion: String by rootProject.extra
val springCloudVersion: String by rootProject.extra
plugins {
    kotlin("jvm")
    kotlin("plugin.spring")
    id("org.springframework.boot") version "3.4.3"
    id("io.spring.dependency-management")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.alibaba.cloud:spring-cloud-starter-alibaba-nacos-discovery")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.springframework.cloud:spring-cloud-starter")
    implementation("org.springframework.cloud:spring-cloud-starter-gateway")
    implementation("org.springframework.cloud:spring-cloud-starter-loadbalancer")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:$springCloudVersion")
        mavenBom("com.alibaba.cloud:spring-cloud-alibaba-dependencies:$alibabaCloudVersion")
    }
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
