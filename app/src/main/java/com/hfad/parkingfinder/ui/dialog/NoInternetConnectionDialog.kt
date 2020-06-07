package com.hfad.parkingfinder.ui.dialog

import android.app.Dialog
import android.app.DialogFragment
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.View
import com.hfad.parkingfinder.R
import kotlinx.android.synthetic.main.dialog_alert.view.*

class NoInternetConnectionDialog : DialogFragment() {

    var onTryAgainListener: (() -> Unit)? = null
    var onCancelListener: (() -> Unit)? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val view = activity.layoutInflater.inflate(R.layout.dialog_alert, null)
        builder.setView(view)
        initButtonListeners(view)
        val dialog = builder.create()
        dialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        isCancelable = false
        return dialog
    }

    private fun initButtonListeners(view: View) {
        view.tryAgainTV.setOnClickListener {
            onTryAgainListener?.invoke()
            dismiss()
        }
        view.cancelTV.setOnClickListener {
            onCancelListener?.invoke()
            dismiss()
        }
    }
}