object Versions {
    const val JUNIT = "4.13.2"
    const val COROUTINES = "1.6.0"
    const val RETROFIT = "2.9.0"
    const val OKHTTP = "4.9.3"
    const val SERIALIZATION = "1.3.2"
}

object BuildPluginsVersion {
    const val DETEKT = "1.19.0"
    const val KOTLIN = "1.6.10"
    const val KTLINT = "10.0.0"
    const val PLUGIN_PUBLISH = "0.18.0"
    const val VERSIONS_PLUGIN = "0.38.0"
    const val SPOTLESS = "6.5.0"
}

object Libs {
    const val COROUTINES_CORE = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.COROUTINES}"
    const val RETROFIT_CORE = "com.squareup.retrofit2:retrofit:${Versions.RETROFIT}"
    const val RETROFIT_CONVERTER = "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.8.0"
    const val OKHTTP_CORE = "com.squareup.okhttp3:okhttp:${Versions.OKHTTP}"
    const val JSON = "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.SERIALIZATION}"
}

object AndroidTools {
    const val SDK_COMMON = "com.android.tools:sdk-common:30.2.0-alpha07"
    const val COMMON = "com.android.tools:common:30.2.0-alpha07"
}


object TestingLib {
    const val JUNIT = "junit:junit:${Versions.JUNIT}"
}