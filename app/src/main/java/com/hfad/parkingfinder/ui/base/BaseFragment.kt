package com.hfad.parkingfinder.ui.base

import android.content.Context
import android.content.Intent
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import com.hfad.parkingfinder.R
import com.hfad.parkingfinder.ui.welcome.WelcomeActivity
import com.hfad.parkingfinder.MainActivity
import com.hfad.parkingfinder.utils.mvp.ViewException
import com.hfad.parkingfinder.R

abstract class BaseFragment : ViewException, Fragment() {

    private fun showKeyboard(editText: EditText) {
        (context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
                .toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
        editText.requestFocus()
    }

    private fun hideKeyboard() {
        try {
            activity!!.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
            (context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
                    .hideSoftInputFromWindow(activity?.currentFocus?.windowToken, 0)
        } catch (ignored: Exception) {
        }
    }

    fun showSnackbar(message: CharSequence, length: Int, actionText: CharSequence? = null, onClickListener: (() -> Unit)? = null) {
        val snackbar = Snackbar.make(view!!, message, length)
        if (actionText != null && onClickListener != null) {
            snackbar.setAction(actionText) { onClickListener.invoke() }
            snackbar.setActionTextColor(ContextCompat.getColor(context!!, R.color.green))
        }
        snackbar.show()
    }

    override fun handleHttpException(code: Int): Boolean {
        if (code == 401) {
            startActivity(Intent(activity, MainActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            })
            activity!!.finish()
            return true
        }
        return false
    }

    override fun handleException(exception: Throwable) {
        Toast.makeText(context!!, exception.message, Toast.LENGTH_SHORT).show()
    }

    protected fun initBackArrowButtonListener(backArrow: View) {
        backArrow.setOnClickListener {
            hideKeyboard()
            activity!!.onBackPressed()
        }
    }
}