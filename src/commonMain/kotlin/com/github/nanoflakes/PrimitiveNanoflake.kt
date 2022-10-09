package com.github.nanoflakes

/**
 * A [Nanoflake](https://github.com/nanoflakes/nanoflakes).
 */
class PrimitiveNanoflake : Nanoflake {
    /**
     * The value of the nanoflake.
     */
    override val value: Long

    /**
     * Creates a new nanoflake.
     *
     * @param value the nanoflake's value.
     */
    constructor(value: Long) {
        this.value = value
    }

    /**
     * Creates a new nanoflake, parsing the value from a string.
     *
     * @param value the nanoflake's value.
     */
    constructor(value: String) {
        this.value = value.toLong()
    }

    /**
     * Creates a new nanoflake, parsing the value from a string with a radix.
     *
     * @param value the nanoflake's value.
     * @param radix the radix of the nanoflake.
     */
    constructor(value: String, radix: Int) {
        this.value = value.toLong(radix)
    }

    /**
     * {@inheritDoc}
     */
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || other !is Nanoflake) return false
        return value == other.value
    }

    /**
     * {@inheritDoc}
     */
    override fun hashCode(): Int {
        return arrayOf<Any?>(value).contentHashCode()
    }

    /**
     * {@inheritDoc}
     */
    override fun toString(): String {
        return "Nanoflake{value=$value}"
    }
}