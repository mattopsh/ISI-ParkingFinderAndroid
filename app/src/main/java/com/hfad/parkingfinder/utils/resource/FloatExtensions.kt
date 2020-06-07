package com.hfad.parkingfinder.utils.resource

import java.lang.IllegalArgumentException
import java.util.*

fun Float.setPrecision(precision: Int): Float {
    if (precision < 0) {
        throw IllegalArgumentException()
    }
    return "%.${precision}f".format(Locale.US, this).toFloat()
}