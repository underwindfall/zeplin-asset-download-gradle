package com.ncorti.kotlin.gradle.zeplin.tasks

import com.android.ide.common.vectordrawable.Svg2Vector
import com.ncorti.kotlin.gradle.zeplin.internal.api.ZeplinApi
import com.ncorti.kotlin.gradle.zeplin.internal.e
import com.ncorti.kotlin.gradle.zeplin.internal.i
import com.ncorti.kotlin.gradle.zeplin.internal.model.*
import com.ncorti.kotlin.gradle.zeplin.internal.okhttp.downloadAssetAndSaveTo
import com.ncorti.kotlin.gradle.zeplin.internal.w
import kotlinx.coroutines.*
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import java.io.File
import javax.inject.Inject

/**
 * Update assets from zeplin.
 */
abstract class ZeplinDowloadTask @Inject constructor(
    private val api: ZeplinApi,
    private val config: DownloadConfig
) : DefaultTask() {
    init {
        description = "Download assets from zeplin"
    }

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    @get:Input
    abstract val token: String

    @TaskAction
    internal fun taskAction() {
        runBlocking(scope.coroutineContext) {
            val assets = download(tags = config.tagName, allowList = config.allowList, deniedList = config.deniedList)
            writeAssets(assets)
        }
        scope.cancel()
    }


    private suspend fun download(tags: List<String>, allowList: AllowList, deniedList: DeniedList): List<ZeplinAsset> {
        val projectInfo = withContext(Dispatchers.IO) { fetchZeplinProject(api, config.projectId) }
        val screens = withContext(Dispatchers.IO) { fetchAllScreens(api, projectInfo) }
        val assets = screens
            .filter { screen -> screen.tags.any { tags.contains(it) } || allowList.screens.contains(screen.id) }
            .filter { screen -> !deniedList.screens.contains(screen.id) }
            .map { it.id }
            .run { fetchScreenAssets(api = api, projectId = projectInfo.id, screenIds = this) }
            .flatMap { it.assets }
        logger.i("Get Zeplin assets response $assets")
        return assets
    }

    private suspend fun fetchZeplinProject(api: ZeplinApi, projectId: String): ZeplinProject {
        val projectInfo = api.getProjectInfo(projectId)
        logger.w("Get Zeplin Project response $projectInfo")
        return projectInfo
    }

    private suspend fun fetchAllScreens(api: ZeplinApi, projectInfo: ZeplinProject): List<ZeplinScreen> {
        val pageSize = 100
        if (projectInfo.numberOfScreens >= pageSize) {
            val screens = mutableListOf<ZeplinScreen>()
            var page = 1
            while (true) {
                val screenList = api.getAllScreens(projectInfo.id, pageSize, page * pageSize)
                screens.addAll(screenList)
                if (screenList.size < pageSize) {
                    break
                }
                page++
            }
            logger.w("Get Zeplin screens response $screens")
            return screens
        } else {
            val screens = api.getAllScreens(projectInfo.id, projectInfo.numberOfScreens)
            logger.w("Get Zeplin screens response $screens")
            return screens
        }
    }

    private suspend fun fetchScreenAssets(
        api: ZeplinApi,
        projectId: String,
        screenIds: List<String>
    ): List<ZeplinScreenVersion> = coroutineScope {
        screenIds
            .map { id -> async { api.getLatestScreenVersion(projectId = projectId, screenId = id) } }
            .awaitAll()
    }

    private suspend fun writeAssets(assets: List<ZeplinAsset>): List<File> {
        val svgFiles = assets
            .filter { it.svgAssetUrl != null }
            .map { asset ->
                val assetName = asset.name
                downloadAssetAndSaveTo(
                    project = project,
                    downloadUrl = asset.svgAssetUrl!!,
                    outputName = "${config.resourcePrefix}_$assetName"
                )
            }
        logger.i("Assets download finished")
        logger.i("Transforming svg to vector drawables.....")
        return svgFiles
    }

    private fun transformAssets(files: List<File>) {
        runBlocking {
            files
                .onEach { svg ->
                    val name = svg.nameWithoutExtension.substringAfterLast("/")
                    val output = File("${config.output}/drawable/$name.xml")
                    if (output.exists().not()) {
                        output.parentFile.mkdirs()
                        output.createNewFile()
                    }
                    convertSvgToVectorDrawable(svg, output)
                }
                .forEach { svg ->
                    svg.delete()
                }
        }
        logger.i("Transforming complete.....")
    }

    private fun convertSvgToVectorDrawable(svgFile: File, output: File) {
        val outputStream = output.outputStream()
        try {
            val errorMessage = Svg2Vector.parseSvgToXml(svgFile, outputStream)
            if (errorMessage != null && errorMessage.isNotEmpty()) {
                outputStream.close()
                logger.e("An error occurred while trying to convert ${svgFile.name} to vector drawable [$errorMessage]")
                return
            }
        } catch (e: Exception) {
            outputStream.close()
            logger.e("An error occurred while trying to convert ${svgFile.name} to a vector drawable [$e]")
            return
        } finally {
            outputStream.close()
        }
    }
}