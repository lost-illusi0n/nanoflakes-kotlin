package com.github.nanoflakes

/**
 * A [Nanoflake](https://github.com/nanoflakes/nanoflakes).
 */
interface Nanoflake {
    /**
     * The value of the nanoflake.
     */
    public val value: Long

    public val timestamp get() = Nanoflakes.timestampValue(value)

    public val generator get() = Nanoflakes.generatorValue(value)

    public val sequence get() = Nanoflakes.sequenceValue(value)


    /**
     * Gets the nanoflake value as a Long.
     *
     * @return the value as a long.
     */
    public fun asLong(): Long {
        return value
    }

    /**
     * Gets the nanoflake value as a string.
     *
     * @return the value as a String.
     */
    public fun asString(): String {
        return value.toString()
    }

    /**
     * Gets the nanoflake value as a string, using a specified radix.
     *
     * @param radix the radix.
     * @return the value as a String.
     */
    public fun withRadix(radix: Int): String {
        return value.toString(radix)
    }
}

public fun nanoflake(value: Long): PrimitiveNanoflake {
    return PrimitiveNanoflake(value)
}

public fun nanoflake(value: String): PrimitiveNanoflake {
    return PrimitiveNanoflake(value.toLong())
}

public fun nanoflake(value: String, radix: Int): PrimitiveNanoflake {
    return PrimitiveNanoflake(value.toLong(radix))
}