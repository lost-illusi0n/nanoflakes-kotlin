package com.github.nanoflakes

import kotlin.jvm.JvmInline

/**
 * A [Nanoflake](https://github.com/nanoflakes/nanoflakes).
 *
 * @param value the value of the nanoflake.
 */
@JvmInline
value class PrimitiveNanoflake(override val value: Long) : Nanoflake