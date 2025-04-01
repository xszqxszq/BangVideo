val springCloudVersion: String by rootProject.extra
plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
    kotlin("plugin.spring")
    id("io.spring.dependency-management")
    id("org.graalvm.buildtools.native")
    id("org.springframework.boot")
}

group = "xyz.xszq.bang_video"
version = "0.0.1-SNAPSHOT"

repositories {
    maven("https://maven.aliyun.com/repository/public/")
    mavenCentral()
}

dependencies {
    implementation(project(":common"))
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")
    implementation("org.springframework.boot:spring-boot-starter-amqp")
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation("org.springframework.amqp:spring-rabbit-test")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
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

tasks.withType<Test> {
    useJUnitPlatform()
}
