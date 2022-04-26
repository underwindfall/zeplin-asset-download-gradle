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

/**
 * Zeplin project used to convert response call from zeplin to our proper model.
 * The model is created based on [this documentation](https://docs.zeplin.dev/reference#project)
 */
@Serializable
data class ZeplinProject(
    val id: String,
    val name: String,
    @SerialName(value = "number_of_screens") val numberOfScreens: Int
)

/**
 * Zeplin screen used to convert response call from zeplin to our proper model.
 * The model is created based on [this documentation](https://docs.zeplin.dev/reference#asset)
 */
@Serializable
data class ZeplinAsset(
    @SerialName(value = "layer_source_id")
    val id: String,
    @SerialName(value = "display_name")
    val name: String,
    val contents: List<ZeplinAssetContent>
) {
    /**
     * supported formats url for user to download currently we only use SVG
     * @see [ZeplinAssetContent.SupportedFormats]
     */
    val svgAssetUrl = contents
        .firstOrNull { ZeplinAssetContent.SupportedFormats.isSupportFormat(it.format) }
        ?.url
}

/**
 * Zeplin screen used to convert response call from zeplin to our proper model.
 * The model is created based on [this documentation](https://docs.zeplin.dev/reference#asset_content)
 */
@Serializable
data class ZeplinAssetContent(
    val url: String,
    val format: String,
    val density: Float
) {
    enum class SupportedFormats(private val value: String) {
        SVG("svg");

        companion object {
            /**
             * find out zeplin asset we had from call zeplin server is supported our case or not currently we only
             * support SVG
             */
            @JvmStatic
            fun isSupportFormat(format: String): Boolean = values().any { it.value.equals(format, ignoreCase = true) }
        }
    }
}

/**
 * Zeplin screen used to convert response call from zeplin to our proper model.
 * The model is created based on [this documentation](https://docs.zeplin.dev/reference#screen)
 */
@Serializable
data class ZeplinScreen(
    val id: String,
    val name: String,
    val tags: List<String>
) {
    override fun toString(): String = """
        id: $id,
        name: $name,
        tags: ${tags.joinToString(prefix = "[", postfix = "]", separator = ",") { it }}
    """.trimIndent()
}

/**
 * Zeplin screen used to convert response call from zeplin to our proper model.
 * The model is created based on [this documentation](https://docs.zeplin.dev/reference#screen_version)
 */
@Serializable
data class ZeplinScreenVersion(
    val id: String,
    val assets: List<ZeplinAsset>
)
