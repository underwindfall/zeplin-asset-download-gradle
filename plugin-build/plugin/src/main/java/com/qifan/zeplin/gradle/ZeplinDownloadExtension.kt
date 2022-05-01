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

import org.gradle.api.Named
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.PathSensitive
import org.gradle.api.tasks.PathSensitivity
import javax.inject.Inject

/**
 * The entry point for all Zeplin download related configuration.
 */
abstract class ZeplinDownloadExtension @Inject constructor(
    private val name: String
) : Named {
    @Internal
    override fun getName(): String = name

    /**
     * --------------- MANDATORY Information for this plugin. ----------------
     * The Zeplin developer token. Necessaary to download Zeplin projects.
     */
    @get:Input
    abstract val zeplinToken: Property<String>

    /**
     * --------------- MANDATORY to give some custom information for this plugin ----------------
     * The configuration file path for how downloading the Zeplin assets.
     */
    @get:PathSensitive(PathSensitivity.RELATIVE)
    @get:InputFile
    abstract val configFile: RegularFileProperty
}
