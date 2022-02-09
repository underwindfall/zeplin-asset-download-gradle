package com.ncorti.kotlin.gradle.zeplin

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Top level class for Zeplin gradle plugin. Takes care of creating all the necessary classes
 * based on which other plugins are applied in the build.
 */
abstract class ZeplinDownloadPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        // Add the 'template' extension object
//        val extension = project.extensions.create(EXTENSION_NAME, TemplateExtension::class.java, project)
    }
}
