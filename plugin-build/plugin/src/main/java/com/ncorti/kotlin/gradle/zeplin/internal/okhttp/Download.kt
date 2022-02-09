package com.ncorti.kotlin.gradle.zeplin.internal.okhttp

import kotlinx.coroutines.*
import okhttp3.*
import org.gradle.api.Project
import java.io.File
import java.io.IOException
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException


/**
 * generate a new http client
 */
fun newOkHttpClient(token: String): OkHttpClient {
    val oAuthInterceptor = OAuthInterceptor(token)

    return OkHttpClient.Builder()
        .addInterceptor(oAuthInterceptor)
        .build()
}

/**
 * this class is used to intercept http client request to insert Authorization token into it
 * current Zeplin
 */
private class OAuthInterceptor(private val token: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val newBuilder = request.newBuilder()
        newBuilder.header("Accept", "application/json")
        //write current token to request
        newBuilder.header("Authorization", "Bearer $token")

        request = newBuilder.build() //overwrite old request
        return chain.proceed(request)
    }
}


/**
 * schedule thread
 */
@Suppress("MagicNumber")
private inline val downloadThreadPool: ScheduledExecutorService
    get() = Executors.newScheduledThreadPool(4) { task -> Thread(task).also { it.isDaemon = true } }

/**
 * coroutine okhttp downloader coroutine the downloader system
 */
@Suppress("TooGenericExceptionCaught")
suspend fun downloadAssetAndSaveTo(
    project: Project,
    downloadUrl: String,
    outputName: String,
    blockingDispatcher: CoroutineDispatcher = downloadThreadPool.asCoroutineDispatcher(),
): File = OkHttpClient().newCall(Request.Builder().get().url(downloadUrl).build()).run {
    withContext(blockingDispatcher) {
        suspendCancellableCoroutine { cancellableContinuation ->
            cancellableContinuation.invokeOnCancellation { cancel() }
            enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    cancellableContinuation.resumeWithException(e)
                }

                override fun onResponse(call: Call, response: Response) {
                    if (!response.isSuccessful) {
                        cancellableContinuation.resumeWithException(IOException("HTTP error code: ${response.code}"))
                        return
                    }

                    try {
                        val body = response.body
                        if (body == null) {
                            cancellableContinuation.resumeWithException(IllegalStateException("Body is null"))
                            return
                        }
                        val temporaryFile = File("${project.rootProject.buildDir}/config/$outputName.svg")
                        if (temporaryFile.exists().not()) {
                            temporaryFile.parentFile.mkdirs()
                            temporaryFile.createNewFile()
                        }
                        temporaryFile.writeBytes(body.bytes())
                        cancellableContinuation.resume(temporaryFile)
                    } catch (e: Exception) {
                        cancellableContinuation.resumeWithException(e)
                    }
                }
            })
        }
    }
}
