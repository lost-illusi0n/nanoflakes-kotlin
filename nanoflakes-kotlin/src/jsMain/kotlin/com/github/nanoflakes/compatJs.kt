package com.github.nanoflakes

import kotlin.js.Date

actual fun currentTimeMillis(): Long = Date().getTime().toLong()

actual inline fun <R> maybeSynchronized(lock: Any, block: () -> R): R {
    return block()
}

actual typealias DateTime = Date

actual fun Long.toDateTime(): Date = Date(this)