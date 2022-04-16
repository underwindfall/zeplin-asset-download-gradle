plugins {
    kotlin("jvm")
    id("com.qifan.kotlin.gradle.zeplin")
}


zeplinConfig {
    zeplinToken.set("st")
//    cofigFilePath.set("test.json")
}


