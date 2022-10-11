package com.github.nanoflakes

/**
 * A generator of [nanoflakes][Nanoflake].
 */
interface NanoflakeGenerator {
    /**
     * Gets the next [nanoflake][Nanoflake].
     *
     * @return a new, generated nanoflake.
     */
    operator fun next(): Nanoflake

    /**
     * Gets this nanoflake generator's epoch.
     *
     * @return the epoch in milliseconds.
     */
    fun epochMillis(): Long

    /**
     * Gets this nanoflake generator's epoch as an [OffsetDateTime].
     *
     * @return a [OffsetDateTime] set to the epoch time.
     */
    fun NanoflakeGenerator.epoch(): DateTime {
        return epochMillis().toDateTime()
    }
}