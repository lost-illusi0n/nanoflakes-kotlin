package com.github.nanoflakes

import kotlinx.coroutines.delay
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

/**
 * A local suspending generator of [nanoflakes][Nanoflake].
 * @constructor Creates a new local generator of [nanoflakes][Nanoflake].
 * @param epoch       the generator's epoch.
 * @param generatorId the generator's ID.
 */
public data class SuspendingNanoflakeLocalGenerator(override val epoch: Long, private val generatorId: Long) : SuspendingNanoflakeGenerator {
    private var lastTimestamp = -1L
    private var sequence = 0L

    private val mutex = Mutex(locked = false)

    override suspend fun next(): PrimitiveNanoflake = mutex.withLock {
        var timestamp: Long = currentTimeMillis()

        while (timestamp < lastTimestamp) {
            val diff = lastTimestamp - timestamp
            delay(diff)

            timestamp = currentTimeMillis()
        }

        if (lastTimestamp != timestamp) {
            sequence = 0L
        } else {
            sequence = sequence + 1 and Nanoflakes.MAX_SEQUENCE
            if (sequence == 0L) timestamp = tilNextMillis(lastTimestamp)
        }

        lastTimestamp = timestamp

        val value = timestamp - epoch shl Nanoflakes.TIMESTAMP_SHIFT or (generatorId shl Nanoflakes.GENERATOR_ID_SHIFT) or sequence

        return PrimitiveNanoflake(value)
    }

    private suspend fun tilNextMillis(lastTimestamp: Long): Long {
        var timestamp: Long = currentTimeMillis()
        while (timestamp <= lastTimestamp) {
            delay(1)
            timestamp = currentTimeMillis()
        }
        return timestamp
    }

    override fun toString(): String {
        return "NanoflakeLocalGenerator(epoch=$epoch, generatorId=$generatorId, lastTimestamp=$lastTimestamp, sequence=$sequence)"
    }

    init {
        require(epoch <= currentTimeMillis()) { "Specified epoch is in the future." }
        require(generatorId in 0..Nanoflakes.MAX_GENERATOR_ID) { "Invalid generator id, outside of [0, ${Nanoflakes.MAX_GENERATOR_ID}] range" }
    }
}

public fun Nanoflakes.suspendingLocalGenerator(epoch: Long, generatorId: Long): SuspendingNanoflakeLocalGenerator {
    return SuspendingNanoflakeLocalGenerator(epoch, generatorId)
}