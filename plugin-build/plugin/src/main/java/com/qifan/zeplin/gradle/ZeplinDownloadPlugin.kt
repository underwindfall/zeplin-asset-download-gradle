/**
 * Copyright (C) 2022 by Qifan YANG (@underwindfall)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.qifan.zeplin.gradle

import com.qifan.zeplin.gradle.internal.api.ZeplinApi
import com.qifan.zeplin.gradle.internal.error
import com.qifan.zeplin.gradle.internal.info
import com.qifan.zeplin.gradle.internal.model.DownloadConfig
import com.qifan.zeplin.gradle.internal.okhttp.newOkHttpClient
import com.qifan.zeplin.gradle.internal.success
import com.qifan.zeplin.gradle.tasks.ZeplinDowloadTask
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Top level class for Zeplin gradle plugin. Takes care of creating all the necessary classes
 * based on which other plugins are applied in the build.
 */
const val EXTENSION_NAME = "zeplinConfig"
const val TASK_NAME = "updateZeplin"

abstract class ZeplinDownloadPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val extension = project.extensions.create(
            EXTENSION_NAME,
            ZeplinDownloadExtension::class.java,
            "Zeplin"
        )
        project.afterEvaluate {
            it.checkAndroidPlugin()
            val json = Json { ignoreUnknownKeys = true }
            val okHttpClient = newOkHttpClient(extension.zeplinToken.get())

            val config = project.parseZeplinConfig(extension, json)
            val zeplinApi = ZeplinApi.create(okHttpClient = okHttpClient, json = json)
            val updateZeplin = project.tasks.create(TASK_NAME).apply {
                group = "Zeplin"
                description = "Generate Android Assets from zeplin design files"
            }
            val updateAsset = project.tasks.create(
                ZeplinDowloadTask::taskAction.name,
                ZeplinDowloadTask::class.java,
                zeplinApi,
                config
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
    }

    private fun Project.parseZeplinConfig(extension: ZeplinDownloadExtension, json: Json): DownloadConfig {
        val config = extension.configFile.asFile.orNull
        if (config == null || config.extension != "json" || config.exists().not()) {
            error("Zeplin config file path  is not valid")
            throw IllegalStateException("Zeplin config file path is not valid")
        }
        val jsonString = config.bufferedReader().use { it.readText() }
        return json.decodeFromString(jsonString)
    }

    private fun Project.checkAndroidPlugin() {
        if (!(plugins.hasPlugin("com.android.library") || plugins.hasPlugin("com.android.application"))) {
            throw IllegalStateException("This plugin must apply with android library or android application")
        }
    }
}
