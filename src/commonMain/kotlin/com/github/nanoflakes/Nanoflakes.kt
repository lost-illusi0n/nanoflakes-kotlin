package com.github.nanoflakes

/**
 * Utility methods and constants related to Nanoflakes.
 */
object Nanoflakes {
    /**
     * How much of the nanoflake is occupied by the timestamp of the ID, in bits.
     */
    const val TIMESTAMP_BITS: Int = 14

    /**
     * How much of an nanoflake is occupied by the generator ID, in bits.
     */
    const val GENERATOR_ID_BITS: Int = 10

    /**
     * How much of an nanoflake is occupied by sequence number, in bits.
     */
    const val SEQUENCE_BITS: Int = 12

    /**
     * The maximum ID possible for a generator.
     */
    const val MAX_GENERATOR_ID: Long = 1023

    /**
     * The max sequence value of a snowflake.
     */
    const val MAX_SEQUENCE: Long = 4095

    /**
     * The value used for generator id-based shifts.
     */
    const val GENERATOR_ID_SHIFT = SEQUENCE_BITS

    /**
     * The value used for timestamp-based shifts.
     */
    const val TIMESTAMP_SHIFT = SEQUENCE_BITS + GENERATOR_ID_SHIFT

    /**
     * Creates a local generator.
     * @param epoch base epoch
     * @param generatorId the generator id.
     * @return a new [NanoflakeLocalGenerator].
     */
    fun localGenerator(epoch: Long, generatorId: Long): NanoflakeGenerator {
        return NanoflakeLocalGenerator(epoch, generatorId)
    }

    /**
     * Creates a local generator that keeps epoch information in the resulting nanoflake.
     * @param epoch base epoch
     * @param generatorId the generator id.
     * @return a new local nanoflake generator.
     */
    fun withEpochLocalGenerator(epoch: Long, generatorId: Long): NanoflakeWithEpochGenerator {
        return localGenerator(epoch, generatorId).withEpoch()
    }

    /**
     * Gets the timestamp of a nanoflake.
     * @param id the nanoflake.
     * @return the raw timestamp.
     */
    fun timestampValue(id: Long): Long {
        return id shr TIMESTAMP_SHIFT.toInt()
    }

    /**
     * Gets the generator ID of a nanoflake.
     * @param id the nanoflake.
     * @return the generator ID.
     */
    fun generatorValue(id: Long): Long {
        return id shr GENERATOR_ID_SHIFT and MAX_GENERATOR_ID
    }

    /**
     * Gets the sequence ID of a nanoflake.
     * @param id the nanoflake.
     * @return the sequence ID.
     */
    fun sequenceValue(id: Long): Long {
        return id and MAX_SEQUENCE
    }
}