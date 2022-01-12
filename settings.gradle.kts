pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

rootProject.name = ("gradle-zeplin-asset-plugin")

include(":example")
includeBuild("plugin-build")
