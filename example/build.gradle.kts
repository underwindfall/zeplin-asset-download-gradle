plugins {
    id("com.android.library")
    id("com.qifan.zeplin.gradle")
}

android {
    compileSdk = 30

    defaultConfig {
        minSdk = 21
        targetSdk = 30
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

zeplinConfig {
    zeplinToken.set(System.getenv("ZEPLIN_TOKEN"))
    configFile.set(file("test.json"))
}
