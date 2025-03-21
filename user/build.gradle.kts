val alibabaCloudVersion: String by rootProject.extra
val springCloudVersion: String by rootProject.extra
plugins {
    kotlin("jvm")
    kotlin("kapt")
    kotlin("plugin.jpa")
    kotlin("plugin.spring")
    id("org.springframework.boot") version "3.4.3"
    id("io.spring.dependency-management")
}

group = "xyz.xszq.bang_video"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    kapt("org.mapstruct:mapstruct-processor:1.5.5.Final")
    implementation(project(":common"))
    runtimeOnly("org.mariadb.jdbc:mariadb-java-client")
    implementation("com.alibaba.cloud:spring-cloud-starter-alibaba-nacos-discovery")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.mapstruct:mapstruct:1.5.5.Final")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.cloud:spring-cloud-starter")
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign")
    implementation("org.springframework.cloud:spring-cloud-starter-loadbalancer")
    implementation("org.springframework.session:spring-session-data-redis")
    kaptTest("org.mapstruct:mapstruct-processor:1.5.5.Final")
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
kapt {
    arguments {
        arg("mapstruct.defaultComponentModel", "spring")
    }
    keepJavacAnnotationProcessors = true
}

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
