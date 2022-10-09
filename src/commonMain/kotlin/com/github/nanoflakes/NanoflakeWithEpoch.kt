package com.github.nanoflakes

/**
 * A [Nanoflake] which contains epoch information.
 *
 * @param nanoflake the underlying nanoflake information.
 * @param epochInMillis the nanoflake's epoch.
 */
data class NanoflakeWithEpoch(
    private val nanoflake: Nanoflake,
    val epochInMillis: Long,
): Nanoflake by nanoflake {
    /**
     * Gets this nanoflake's epoch as an [OffsetDateTime].
     *
     * @return a [OffsetDateTime] set to the epoch time.
     */
    public val epoch get() = epochInMillis.toDateTime()

    /**
     * The creation time.
     */
    val creationTimeInMillis get() = epochInMillis + timestamp

    /**
     * Gets the creation time as an [OffsetDateTime].
     *
     * @return a [OffsetDateTime] set to the creation time.
     */
    val creationTime get() = creationTimeInMillis.toDateTime()
}

public fun Nanoflake.withEpoch(epoch: Long): NanoflakeWithEpoch {
    return NanoflakeWithEpoch(this, epoch)
}