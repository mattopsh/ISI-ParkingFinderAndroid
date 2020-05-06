package com.hfad.parkingfinderapp.ui.base

import android.content.Context
import android.content.Intent
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import com.hfad.parkingfinderapp.MainActivity
import com.hfad.parkingfinderapp.utils.mvp.ViewException
import kotlinx.android.synthetic.main.layout_normal_action_bar.view.*
import kotlinx.android.synthetic.main.layout_search_action_bar.view.*
import org.jetbrains.anko.contentView
import org.jetbrains.anko.intentFor


abstract class BaseActivity : ViewException, AppCompatActivity() {

    private var normalActionBar: View? = null
    private var searchActionBar: View? = null

    fun showKeyboard(editText: EditText) {
        (this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
                .toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
        editText.requestFocus()
    }

    fun hideKeyboard() {
        try {
            this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
            (this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
                    .hideSoftInputFromWindow(this.currentFocus?.windowToken, 0)
        } catch (ignored: Exception) {
        }
    }

    fun showSearchActionBar() {
        if (normalActionBar !== null && searchActionBar?.searchET !== null) {
            normalActionBar!!.visibility = View.INVISIBLE
            searchActionBar!!.visibility = View.VISIBLE
            showKeyboard(searchActionBar!!.searchET)
        } else {
            throw IllegalStateException("You need to call initActionBarBasicBehavior before using actionBar")
        }
    }

    fun showNormalActionBar() {
        if (normalActionBar !== null && searchActionBar !== null) {
            normalActionBar!!.visibility = View.VISIBLE
            searchActionBar!!.visibility = View.INVISIBLE
            hideKeyboard()
        } else {
            throw IllegalStateException("You need to call initActionBarBasicBehavior before using actionBar")
        }
    }

    fun showSnackbar(message: CharSequence, length: Int, actionText: CharSequence? = null, onClickListener: (() -> Unit)? = null) {
        val snackbar = Snackbar.make(contentView!!, message, length)
        if (actionText != null && onClickListener != null) {
            snackbar.setAction(actionText) { onClickListener.invoke() }
            snackbar.setActionTextColor(ContextCompat.getColor(applicationContext, R.color.green))
        }
        snackbar.show()
    }

    fun showToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    override fun handleHttpException(code: Int): Boolean {
        if (code == 401) {
            startActivity(intentFor<MainActivity>().addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK))
            finish()
            return true
        }
        return false
    }

    override fun handleException(exception: Throwable) {
        Toast.makeText(applicationContext, exception.message, Toast.LENGTH_SHORT).show()
    }

    protected open fun initBackArrowButtonListener(backArrow: View) {
        backArrow.setOnClickListener {
            hideKeyboard()
            super.onBackPressed()
        }
    }

    protected fun initActionBarBasicBehavior(normalActionBar: View, searchActionBar: View, colorId: Int? = null) {
        this.normalActionBar = normalActionBar
        this.searchActionBar = searchActionBar
        initBackArrowButtonListener(normalActionBar.backArrow1IV)
        searchActionBar.backArrow2IV.setOnClickListener {
            showNormalActionBar()
        }
        normalActionBar.searchIV.setOnClickListener {
            showSearchActionBar()
        }
        searchActionBar.clearIV.setOnClickListener {
            searchActionBar.searchET.text = null
        }
        if (colorId !== null) {
            normalActionBar.actionBar1Bg.background = resources.getColorDrawable(colorId)
            searchActionBar.actionBar2Bg.background = resources.getColorDrawable(colorId)
        }
    }
}