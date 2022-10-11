package com.github.nanoflakes

/**
 * A suspending generator of [nanoflakes][Nanoflake].
 * If it cannot generate a nanoflake instantly, it will wait.
 */
interface SuspendingNanoflakeGenerator {
    /**
     * This nanoflake generator's epoch in milliseconds.
     */
    val epoch: Long

    /**
     * Gets the next [nanoflake][Nanoflake].
     *
     * @return a new, generated nanoflake.
     */
    suspend operator fun next(): Nanoflake
}