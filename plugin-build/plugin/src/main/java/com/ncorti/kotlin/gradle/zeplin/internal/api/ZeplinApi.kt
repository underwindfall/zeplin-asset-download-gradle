package com.ncorti.kotlin.gradle.zeplin.internal.api

import com.ncorti.kotlin.gradle.zeplin.internal.model.ZeplinProject
import com.ncorti.kotlin.gradle.zeplin.internal.model.ZeplinScreen
import com.ncorti.kotlin.gradle.zeplin.internal.model.ZeplinScreenVersion
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
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

        fun create(okHttpClient: OkHttpClient): ZeplinApi {
            return Retrofit.Builder()
                .baseUrl(ENDPOINT)
                .validateEagerly(true)
                .client(okHttpClient)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
                .create(ZeplinApi::class.java)
        }
    }
}