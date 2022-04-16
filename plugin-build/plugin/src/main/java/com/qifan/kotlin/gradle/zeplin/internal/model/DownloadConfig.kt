package com.qifan.kotlin.gradle.zeplin.internal.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

//configuration files to take into account when downloading tasks
//{
//    "projectId": "",
//    "tagName": [],
//    "outputDir": "",
//    "prefix": "",
//    "deniedList": { "screen_ids": [] },
//    "allowList": { "screen_ids": [] },
//}
@Serializable
data class DownloadConfig(
    val projectId: String,
    val tagName: List<String>,
    val output: String,
    val resourcePrefix: String,
    val deniedList: DeniedList,
    val allowList: AllowList,
)

/**
 * denied list to exclude some screens from downloading task without importing assets
 */
@Serializable
data class DeniedList(
    @SerialName(value =  "screen_ids")
    val screens: List<String>,
)

/**
 * allow list to include some screens from downloading task without importing assets
 */
@Serializable
data class AllowList(
    @SerialName(value =  "screen_ids")
    val screens: List<String>,
)