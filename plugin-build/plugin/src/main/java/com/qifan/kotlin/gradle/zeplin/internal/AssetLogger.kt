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
