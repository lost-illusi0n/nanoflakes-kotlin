package com.github.nanoflakes

/**
 * This function provides the current time in milliseconds to the local generator.
 */
expect fun currentTimeMillis(): Long

/**
 * This function provides optional synchronization for platforms with multithreading.
 */
expect inline fun <R> maybeSynchronized(lock: Any, block: () -> R): R

/**
 * This class should either be a typealias for a system-dependent Date and Time class or implement one if needed.
 */
expect class DateTime

/**
 * This function converts a Long for the system's dependent Date and Time class.
 */
expect fun Long.toDateTime() : DateTime
