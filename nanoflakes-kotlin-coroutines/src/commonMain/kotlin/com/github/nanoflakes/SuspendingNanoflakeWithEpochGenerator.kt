package com.github.nanoflakes

/**
 * A generator of [nanoflakes][Nanoflake] that retains its epoch information.
 * @param generator the underlying generator.
 */
public class SuspendingNanoflakeWithEpochGenerator(
    private val generator: SuspendingNanoflakeGenerator
) : SuspendingNanoflakeGenerator by generator {
    override suspend fun next(): NanoflakeWithEpoch {
        return when (val nanoflake = generator.next()) {
            is NanoflakeWithEpoch -> nanoflake
            is PrimitiveNanoflake -> nanoflake.withEpoch(generator.epoch)
        }
    }
}

public fun SuspendingNanoflakeGenerator.withEpoch(): SuspendingNanoflakeWithEpochGenerator {
    return when (this) {
        is SuspendingNanoflakeWithEpochGenerator -> this
        else -> SuspendingNanoflakeWithEpochGenerator(this)
    }
}

public fun Nanoflakes.suspendingWithEpochLocalGenerator(epoch: Long, generatorId: Long): SuspendingNanoflakeWithEpochGenerator {
    return suspendingLocalGenerator(epoch, generatorId).withEpoch()
}