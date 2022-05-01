object PluginCoordinates {
    const val ID = "com.qifan.kotlin.gradle.zeplin"
    const val GROUP = "com.qifan.kotlin.gradle.zeplin"
    const val VERSION = "0.0.1"
    const val IMPLEMENTATION_CLASS = "com.qifan.kotlin.gradle.zeplin.ZeplinDownloadPlugin"
}

object PluginBundle {
    const val VCS = "https://github.com/underwindfall/gradle-zepling-asset-downloader"
    const val WEBSITE = "https://github.com/underwindfall/gradle-zepling-asset-downloader"
    const val DESCRIPTION = "A plugin used to download assets from zeplin and convert it to useful assets (vector-drawables) for android"
    const val DISPLAY_NAME = "ZeplinAssetDownloader"
    val TAGS = listOf(
        "kotlin",
        "plugin",
        "gradle",
        "download",
        "zeplin",
        "vector drawable",
        "svg converter"
    )
}

