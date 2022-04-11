package com.qifan.kotlin.gradle.zeplin.internal

import org.gradle.api.logging.Logger

private const val PURPLE = "\u001B[35m"
private const val YELLOW = "\u001B[33m"
private const val RESET = "\u001B[0m"
private const val RED = "\u001B[31m"
private const val GREEN = "\u001B[32m"

fun info(text: String) = println("$PURPLE  $text $RESET")
fun warn(text: String) = println("$YELLOW  $text $RESET")
fun success(text: String) = println("$GREEN $text $RESET")
fun error(text: String) = println("$RED $text $RESET")


private const val TAG = "[Zeplin Asset Downloader]"

internal fun Logger.i(message: String) = this.info("$TAG $message")

internal fun Logger.w(message: String, throwable: Throwable? = null) =
    if (throwable != null) {
        this.warn("$TAG $message", throwable)
    } else {
        this.warn("$TAG $message")
    }

internal fun Logger.e(message: String, throwable: Throwable? = null) =
    if (throwable != null) {
        this.error("$TAG $message", throwable)
    } else {
        this.error("$TAG $message")
    }
