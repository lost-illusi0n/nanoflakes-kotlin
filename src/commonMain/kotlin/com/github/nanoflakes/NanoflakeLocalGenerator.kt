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
class NanoflakeLocalGenerator(private val epoch: Long, private val generatorId: Long) : NanoflakeGenerator {
    private var lastTimestamp = -1L
    private var sequence = 0L

    override fun next(): NanoflakeWithEpoch {
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
            return Nanoflake(value).withEpoch(epoch)
        }
    }

    override fun epochMillis(): Long {
        return epoch
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || other !is NanoflakeLocalGenerator) return false
        return epoch == other.epoch && generatorId == other.generatorId
    }

    override fun hashCode(): Int {
        return arrayOf(epoch, generatorId).contentHashCode()
    }

    override fun toString(): String {
        return "NanoflakeLocalGenerator{epoch=$epoch, generatorId=$generatorId, lastTimestamp=$lastTimestamp, sequence=$sequence}"
    }

    private fun tilNextMillis(lastTimestamp: Long): Long {
        var timestamp: Long = currentTimeMillis()
        while (timestamp <= lastTimestamp) timestamp = currentTimeMillis()
        return timestamp
    }

    init {
        require(epoch <= currentTimeMillis()) { "Specified epoch is on the future." }
        require(generatorId in 0..MAX_GENERATOR_ID) { "Invalid generator id, outside of [0, $MAX_GENERATOR_ID] range" }
    }
}