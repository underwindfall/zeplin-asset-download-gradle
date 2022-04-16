plugins {
    kotlin("jvm")
    id("com.qifan.kotlin.gradle.zeplin")
}


zeplinConfig {
    zeplinToken.set("input your token here")
    configFile.set(file("test.json"))
}
