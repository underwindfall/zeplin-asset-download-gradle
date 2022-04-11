package com.qifan.kotlin.gradle.zeplin.internal.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Zeplin project used to convert response call from zeplin to our proper model.
 * The model is created based on [this documentation](https://docs.zeplin.dev/reference#project)
 */
@JsonClass(generateAdapter = true)
data class ZeplinProject(
    val id: String,
    val name: String,
    @Json(name = "number_of_screens") val numberOfScreens: Int
)


/**
 * Zeplin screen used to convert response call from zeplin to our proper model.
 * The model is created based on [this documentation](https://docs.zeplin.dev/reference#asset)
 */
@JsonClass(generateAdapter = true)
data class ZeplinAsset(
    @Json(name = "layer_source_id")
    val id: String,
    @Json(name = "display_name")
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
@JsonClass(generateAdapter = true)
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
@JsonClass(generateAdapter = true)
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
@JsonClass(generateAdapter = true)
data class ZeplinScreenVersion(
    val id: String,
    val assets: List<ZeplinAsset>
)
