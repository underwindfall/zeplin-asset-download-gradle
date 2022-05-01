object PluginCoordinates {
    const val ID = "com.qifan.zeplin.gradle"
    const val GROUP = "com.qifan"
    const val VERSION = "0.0.1"
    const val   IMPLEMENTATION_CLASS = "com.qifan.zeplin.gradle.ZeplinDownloadPlugin"
}

object PluginBundle {
    const val VCS = "https://github.com/underwindfall/gradle-zepling-asset-downloader.git"
    const val WEBSITE = "https://github.com/underwindfall/gradle-zepling-asset-downloader"
    const val DESCRIPTION = "A plugin used to download assets from zeplin and convert it to useful assets (vector-drawables) for android"
    const val DISPLAY_NAME = "ZeplinAssetDownloader"
    val TAGS = listOf(
        "kotlin",
        "plugin",
        "download",
        "zeplin",
        "vector drawable",
        "svg converter"
    )
}

