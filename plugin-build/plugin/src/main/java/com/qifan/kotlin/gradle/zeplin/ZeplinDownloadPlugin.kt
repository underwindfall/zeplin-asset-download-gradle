package com.qifan.kotlin.gradle.zeplin

import com.qifan.kotlin.gradle.zeplin.internal.api.ZeplinApi
import com.qifan.kotlin.gradle.zeplin.internal.error
import com.qifan.kotlin.gradle.zeplin.internal.info
import com.qifan.kotlin.gradle.zeplin.internal.model.DownloadConfig
import com.qifan.kotlin.gradle.zeplin.internal.okhttp.newOkHttpClient
import com.qifan.kotlin.gradle.zeplin.internal.success
import com.qifan.kotlin.gradle.zeplin.tasks.ZeplinDowloadTask
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.gradle.api.Plugin
import org.gradle.api.Project
import java.io.File

/**
 * Top level class for Zeplin gradle plugin. Takes care of creating all the necessary classes
 * based on which other plugins are applied in the build.
 */
const val EXTENSION_NAME = "zeplinConfig"
const val TASK_NAME = "updateZeplin"

abstract class ZeplinDownloadPlugin : Plugin<Project> {
    override fun apply(project: Project) {
//        project.afterEvaluate {
        val extension = project.extensions.create(EXTENSION_NAME, ZeplinDownloadExtension::class.java, project)
        val json = Json { ignoreUnknownKeys = true}
        val okHttpClient = newOkHttpClient(extension.zeplinToken.getOrElse(""))
//        val config =  project.parseZeplinConfig(extension, json)
//        val zeplinApi = ZeplinApi.create(okHttpClient = okHttpClient, json = json)
        val updateZeplin = project.tasks.create(TASK_NAME).apply {
            group = "Zeplin"
            description = "Generate Android Assets from zeplin design files"
        }
//        val updateAsset = project.tasks.create(
//            ZeplinDowloadTask::taskAction.name, ZeplinDowloadTask::class.java, extension,
//            zeplinApi, config
//        ).apply {
//            doFirst {
//                info("Updating assets...")
//                info("The script start to download resources")
//                info("This will takes 1-2 minutes to update the sources. Chill and grap a coffee ☕️\n")
//            }
//        }
//        updateZeplin.dependsOn(updateAsset)
        updateZeplin.apply {
            doLast {
                success("Congratulations, your assets are up to date 🎉")
            }
        }
//        }

    }


//    private fun Project.parseZeplinConfig(extension: ZeplinDownloadExtension, json: Json): DownloadConfig {
//        val configPath = extension.cofigFilePath.orNull
//        if (configPath.isNullOrBlank() || !configPath.endsWith(".json") || File(configPath).exists().not()) {
//            error("Zeplin config file path $configPath  is not valid")
//            throw IllegalStateException("Zeplin config file path $configPath  is not valid")
//        }
//        val jsonString = File(configPath).bufferedReader().use { it.readText() }
//        return json.decodeFromString(jsonString)
//    }
}
