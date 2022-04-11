package com.qifan.kotlin.gradle.zeplin

import com.qifan.kotlin.gradle.zeplin.internal.api.ZeplinApi
import com.qifan.kotlin.gradle.zeplin.internal.model.DownloadConfig
import com.squareup.moshi.Moshi
import org.gradle.api.Plugin
import org.gradle.api.Project
import com.qifan.kotlin.gradle.zeplin.internal.error
import com.qifan.kotlin.gradle.zeplin.internal.info
import com.qifan.kotlin.gradle.zeplin.internal.okhttp.newOkHttpClient
import com.qifan.kotlin.gradle.zeplin.internal.success
import com.qifan.kotlin.gradle.zeplin.tasks.ZeplinDowloadTask
import java.io.File

/**
 * Top level class for Zeplin gradle plugin. Takes care of creating all the necessary classes
 * based on which other plugins are applied in the build.
 */
const val EXTENSION_NAME = "Zeplin config"
const val TASK_NAME = "updateZeplin"

abstract class ZeplinDownloadPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val extension = project.extensions.create(EXTENSION_NAME, ZeplinDownloadExtension::class.java, project)
        val moshi = Moshi.Builder().build()
        val okHttpClient = newOkHttpClient(extension.zeplinToken.get())
        val config =
            requireNotNull(project.parseZeplinConfig(extension, moshi)) { "Zeplin config file path is not valid" }
        val zeplinApi = ZeplinApi.create(okHttpClient = okHttpClient)
        val updateZeplin = project.tasks.create(TASK_NAME).apply {
            group = "Zeplin"
            description = "Generate Android Assets from zeplin design files"
        }
        val updateAsset = project.tasks.create(
            ZeplinDowloadTask::taskAction.name, ZeplinDowloadTask::class.java, extension,
            zeplinApi, config
        ).apply {
            doFirst {
                info("Updating assets...")
                info("The script start to download resources")
                info("This will takes 1-2 minutes to update the sources. Chill and grap a coffee ☕️\n")
            }
        }
        updateZeplin.dependsOn(updateAsset)
        updateZeplin.apply {
            doLast {
                success("Congratulations, your assets are up to date 🎉")
            }
        }
    }


    private fun Project.parseZeplinConfig(extension: ZeplinDownloadExtension, moshi: Moshi): DownloadConfig? {
        val configPath = extension.cofigFilePath.orNull
        if (configPath.isNullOrBlank() || !configPath.endsWith(".json") || File(configPath).exists().not()) {
            error("Zeplin config file path $configPath  is not valid")
        }
        val jsonString = File(configPath).bufferedReader().use { it.readText() }
        return moshi.adapter(DownloadConfig::class.java).fromJson(jsonString)
    }
}
