rootProject.name = "parent"

pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://maven.aliyun.com/repository/public/")
        mavenCentral()
    }
}
include(
    "eureka",
    "gateway",
    "user",
//    "video",
//    "file",
    "community",
//    "encoding",
    "common"
)