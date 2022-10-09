package com.github.nanoflakes

import com.github.nanoflakes.Nanoflakes.GENERATOR_ID_SHIFT
import com.github.nanoflakes.Nanoflakes.MAX_GENERATOR_ID
import com.github.nanoflakes.Nanoflakes.MAX_SEQUENCE
import com.github.nanoflakes.Nanoflakes.TIMESTAMP_SHIFT

/**
 * A local generator of [nanoflakes][Nanoflake].
 * @constructor Creates a new local generator of [nanoflakes][Nanoflake].
 * @param epoch       the generator's epoch.
 * @param generatorId the generator's ID.
 */
data class NanoflakeLocalGenerator(private val epoch: Long, private val generatorId: Long) : NanoflakeGenerator {
    private var lastTimestamp = -1L
    private var sequence = 0L

    override fun next(): PrimitiveNanoflake {
        var timestamp: Long = currentTimeMillis()
        if (timestamp < lastTimestamp) {
            throw RuntimeException("Clock moved backwards. Refusing to generate for " + (lastTimestamp - timestamp) + "milliseconds.")
        }
        maybeSynchronized(this) {
            if (lastTimestamp != timestamp) {
                sequence = 0L
            } else {
                sequence = sequence + 1 and MAX_SEQUENCE
                if (sequence == 0L) timestamp = tilNextMillis(lastTimestamp)
            }
            lastTimestamp = timestamp
            val value = timestamp - epoch shl TIMESTAMP_SHIFT or (generatorId shl GENERATOR_ID_SHIFT) or sequence
            return PrimitiveNanoflake(value)
        }
    }

    override fun epochMillis(): Long {
        return epoch
    }

    private fun tilNextMillis(lastTimestamp: Long): Long {
        var timestamp: Long = currentTimeMillis()
        while (timestamp <= lastTimestamp) timestamp = currentTimeMillis()
        return timestamp
    }

    override fun toString(): String {
        return "NanoflakeLocalGenerator(epoch=$epoch, generatorId=$generatorId, lastTimestamp=$lastTimestamp, sequence=$sequence)"
    }


    init {
        require(epoch <= currentTimeMillis()) { "Specified epoch is in the future." }
        require(generatorId in 0..MAX_GENERATOR_ID) { "Invalid generator id, outside of [0, $MAX_GENERATOR_ID] range" }
    }
}