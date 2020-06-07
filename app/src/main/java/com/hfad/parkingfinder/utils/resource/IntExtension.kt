package com.hfad.parkingfinder.utils.resource

fun Int.toDistance(): String {
    return when {
        this < 1000 -> this.toString() + "m"
        else -> "%.1f".format(this.toFloat() / 1000f) + "km"
    }
}

