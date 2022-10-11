package com.github.nanoflakes

/**
 * A generator of [nanoflakes][Nanoflake] that retains it's epoch information..
 * @param generator the underlying generator.
 */
class NanoflakeWithEpochGenerator(
    private val generator: NanoflakeGenerator
) : NanoflakeGenerator by generator {
    override fun next(): NanoflakeWithEpoch {
        return when (val nanoflake = generator.next()) {
            is NanoflakeWithEpoch -> nanoflake
            is PrimitiveNanoflake -> nanoflake.withEpoch(generator.epochMillis())
        }
    }
}

fun NanoflakeGenerator.withEpoch(): NanoflakeWithEpochGenerator {
    return when (this) {
        is NanoflakeWithEpochGenerator -> this
        else -> NanoflakeWithEpochGenerator(this)
    }
}