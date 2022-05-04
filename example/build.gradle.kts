plugins {
    id("com.android.library")
    id("io.github.underwindfall.zeplin.gradle")
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

val token = System.getenv("ZEPLIN_TOKEN") ?: ""
zeplinConfig {
    zeplinToken.set(token)
    configFile.set(file("test.json"))
}
