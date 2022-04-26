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
package com.qifan.kotlin.gradle.zeplin.internal.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// configuration files to take into account when downloading tasks
// {
//    "projectId": "",
//    "tagName": [],
//    "outputDir": "",
//    "resourcePrefix": "",
//    "deniedList": { "screen_ids": [] },
//    "allowList": { "screen_ids": [] },
// }
@Serializable
data class DownloadConfig(
    val projectId: String,
    val tagName: List<String>,
    val outputDir: String,
    val resourcePrefix: String,
    val deniedList: DeniedList,
    val allowList: AllowList,
)

/**
 * denied list to exclude some screens from downloading task without importing assets
 */
@Serializable
data class DeniedList(
    @SerialName(value = "screen_ids")
    val screens: List<String>,
)

/**
 * allow list to include some screens from downloading task without importing assets
 */
@Serializable
data class AllowList(
    @SerialName(value = "screen_ids")
    val screens: List<String>,
)
