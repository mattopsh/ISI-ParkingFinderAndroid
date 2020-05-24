package com.hfad.parkingfinder.utils

fun scaleProgress(progress: Int, minProgress: Int, maxProgress: Int): Int {
    return minProgress + ((progress * progress) / (maxProgress - minProgress))
}