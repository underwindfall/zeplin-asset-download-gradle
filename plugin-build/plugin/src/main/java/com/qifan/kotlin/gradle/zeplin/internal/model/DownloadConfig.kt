package com.qifan.kotlin.gradle.zeplin.internal.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

//configuration files to take into account when downloading tasks
//{
//    "projectId": "",
//    "tagName": [],
//    "outputDir": "",
//    "prefix": "",
//    "deniedList": { "screen_ids": [] },
//    "allowList": { "screen_ids": [] },
//}
@JsonClass(generateAdapter = true)
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
@JsonClass(generateAdapter = true)
data class DeniedList(
    @Json(name = "screen_ids")
    val screens: List<String>,
)

/**
 * allow list to include some screens from downloading task without importing assets
 */
@JsonClass(generateAdapter = true)
data class AllowList(
    @Json(name = "screen_ids")
    val screens: List<String>,
)