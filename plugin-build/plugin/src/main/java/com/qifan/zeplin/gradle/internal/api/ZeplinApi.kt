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
package com.qifan.zeplin.gradle.internal.api

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.qifan.zeplin.gradle.internal.model.ZeplinProject
import com.qifan.zeplin.gradle.internal.model.ZeplinScreen
import com.qifan.zeplin.gradle.internal.model.ZeplinScreenVersion
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * api client for zeplin call the detail information is from [https://docs.zeplin.dev/reference#introduction]
 */
interface ZeplinApi {

    /**
     * Used to get the project information details for retrieve some useful informations of project
     * such as number of screens etc.
     */
    @GET("projects/{projectId}")
    suspend fun getProjectInfo(@Path("projectId") projectId: String): ZeplinProject

    /**
     * Used to get all the screens in a project.
     * The details are [here](https://docs.zeplin.dev/reference#getprojectscreens)
     */
    @GET("projects/{projectId}/screens")
    suspend fun getAllScreens(
        @Path("projectId") projectId: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int = 0
    ): List<ZeplinScreen>

    /**
     * Used to get the latest version of a screen.
     * The details are [here](https://docs.zeplin.dev/reference#getlatestscreenversion)
     */
    @GET("projects/{projectId}/screens/{screenId}/versions/latest")
    suspend fun getLatestScreenVersion(
        @Path("projectId") projectId: String,
        @Path("screenId") screenId: String
    ): ZeplinScreenVersion

    companion object {
        private const val VERSION = "v1"
        private const val ENDPOINT = "https://api.zeplin.dev/$VERSION/"

        internal const val PAGE_SIZE = 100

        @OptIn(ExperimentalSerializationApi::class)
        fun create(okHttpClient: OkHttpClient, json: Json): ZeplinApi {
            val contentType = "application/json".toMediaType()
            return Retrofit.Builder()
                .baseUrl(ENDPOINT)
                .validateEagerly(true)
                .client(okHttpClient)
                .addConverterFactory(json.asConverterFactory(contentType))
                .build()
                .create(ZeplinApi::class.java)
        }
    }
}
