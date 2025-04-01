val springCloudVersion: String by rootProject.extra
plugins {
    kotlin("jvm")
    kotlin("kapt")
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
    kapt("org.mapstruct:mapstruct-processor:1.5.5.Final")
    implementation(project(":common"))
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.mapstruct:mapstruct:1.5.5.Final")
    implementation("org.springframework.boot:spring-boot-starter-amqp")
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("org.springframework.boot:spring-boot-starter-data-elasticsearch")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.cloud:spring-cloud-starter")
    implementation("org.springframework.cloud:spring-cloud-starter-loadbalancer")
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
    implementation("org.springframework.integration:spring-integration-redis")
    implementation("org.springframework.session:spring-session-data-redis")
    kaptTest("org.mapstruct:mapstruct-processor:1.5.5.Final")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation("org.springframework.amqp:spring-rabbit-test")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
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
