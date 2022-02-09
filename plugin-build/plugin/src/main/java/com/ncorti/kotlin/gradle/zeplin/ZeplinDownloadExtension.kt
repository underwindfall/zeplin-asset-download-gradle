package com.ncorti.kotlin.gradle.zeplin

import org.gradle.api.Project
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Property
import javax.inject.Inject


/** The entry point for all Zeplin download related configuration. */
class ZeplinDownloadExtension @Inject constructor(project: Project) {
    private val objects = project.objects

    /**
     * --------------- MANDATORY Information for this plugin. ----------------
     * The Zeplin developer token. Necessaary to download Zeplin projects.
     */
    val zeplinToken: Property<String> = objects.property(String::class.java)

    /**
     * --------------- MANDATORY to give some custom information for this plugin ----------------
     * The configuration file for how downloading the Zeplin assets.
     */
    val cofigFile: RegularFileProperty = objects.fileProperty()
}