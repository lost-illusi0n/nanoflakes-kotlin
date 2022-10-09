package com.github.nanoflakes

/**
 * A [Nanoflake] which contains epoch information.
 *
 * @param nanoflake the underlying nanoflake information.
 * @param epochInMillis the nanoflake's epoch.
 */
class NanoflakeWithEpoch(
    private val nanoflake: PrimitiveNanoflake,
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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as NanoflakeWithEpoch

        if (nanoflake != other.nanoflake) return false
        if (epochInMillis != other.epochInMillis) return false

        return true
    }

    override fun hashCode(): Int {
        var result = nanoflake.hashCode()
        result = 31 * result + epochInMillis.hashCode()
        return result
    }

    override fun toString(): String {
        return "NanoflakeWithEpoch(nanoflake=$nanoflake, epochInMillis=$epochInMillis)"
    }
}

public fun PrimitiveNanoflake.withEpoch(epoch: Long): NanoflakeWithEpoch {
    return NanoflakeWithEpoch(this, epoch)
}