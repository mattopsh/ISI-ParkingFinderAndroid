package com.hfad.parkingfinder.utils.resource

import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan

fun boldString(bold: String, normal: String): Spannable {
    val spannableString = SpannableString(bold + normal)
    spannableString.setSpan(StyleSpan(Typeface.BOLD), 0, bold.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    return spannableString
}