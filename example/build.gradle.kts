plugins {
    id("com.android.library")
    id("com.qifan.kotlin.gradle.zeplin")
}

android {
    compileSdk = 30

    defaultConfig {
        minSdk = 21
        targetSdk = 30
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

zeplinConfig {
    zeplinToken.set("input your token here")
    configFile.set(file("test.json"))
}
