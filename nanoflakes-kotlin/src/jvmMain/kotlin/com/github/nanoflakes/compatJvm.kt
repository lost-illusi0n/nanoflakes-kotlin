package com.github.nanoflakes

import java.time.OffsetDateTime
import java.util.*

actual fun currentTimeMillis(): Long = System.currentTimeMillis()

actual inline fun <R> maybeSynchronized(lock: Any, block: () -> R): R {
    return synchronized(lock, block)
}

actual typealias DateTime = OffsetDateTime

actual fun Long.toDateTime(): OffsetDateTime {
    val gmt: Calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"))
    gmt.timeInMillis = this
    return OffsetDateTime.ofInstant(gmt.toInstant(), gmt.timeZone.toZoneId())
}