package com.hfad.parkingfinder.utils.edittext

import android.graphics.PorterDuff
import android.support.design.widget.TextInputEditText
import android.view.inputmethod.EditorInfo
import android.widget.EditText

fun EditText.setUnderlineColor(color: Int) {
    val drawable = this.background
    drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
    this.background = drawable
}

fun TextInputEditText.onSubmit(func: () -> Unit) {
    setOnEditorActionListener { _, actionId, _ ->
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            func()
        }
        true
    }
}

fun EditText.onSubmit(func: () -> Unit) {
    setOnEditorActionListener { _, actionId, _ ->
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            func()
        }
        true
    }
}