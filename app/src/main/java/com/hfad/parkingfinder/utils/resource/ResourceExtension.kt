package com.hfad.parkingfinder.utils.resource

import android.content.res.Resources
import android.graphics.drawable.ColorDrawable
import android.os.Build

fun Resources.getColorDrawable(colorId: Int): ColorDrawable {
    return ColorDrawable(getColorId(colorId))
}

fun Resources.getColorId(colorId: Int): Int {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        getColor(colorId, null)
    } else {
        getColor(colorId)
    }
}