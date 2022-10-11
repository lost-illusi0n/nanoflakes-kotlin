package com.github.nanoflakes

actual fun currentTimeMillis(): Long = kotlin.system.getTimeMillis()

actual inline fun <R> maybeSynchronized(lock: Any, block: () -> R): R {
    return block()
}

actual typealias DateTime = Long

actual fun Long.toDateTime(): Long {
    return this
}