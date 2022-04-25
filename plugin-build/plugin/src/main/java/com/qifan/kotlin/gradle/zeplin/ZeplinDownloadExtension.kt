package com.qifan.kotlin.gradle.zeplin

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
