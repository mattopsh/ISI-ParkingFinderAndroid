package com.hfad.parkingfinder.ui.login

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.View
import com.hfad.parkingfinder.R
import com.hfad.parkingfinder.ui.base.BaseActivity
import com.hfad.parkingfinder.ui.login.dagger.LoginInjector
import com.hfad.parkingfinder.ui.login.mvp.LoginMVP
import com.hfad.parkingfinder.ui.main.MainActivity
import com.hfad.parkingfinder.utils.edittext.onSubmit
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.intentFor
import java.net.ConnectException
import javax.inject.Inject


class LoginActivity : BaseActivity(), LoginMVP.View {

    @Inject
    lateinit var presenter: LoginMVP.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        LoginInjector().inject(this)
        initBackArrowButtonListener(backArrowIV)
        initLoginButtonListener()
        initOnSubmitListener()
        showKeyboard(emailTIET)
    }

    override fun onDestroy() {
        presenter.disposeRX()
        super.onDestroy()
    }

    override fun handleException(exception: Throwable) {
        if (exception is ConnectException) {
            showSnackbar("No internet connection", Snackbar.LENGTH_LONG, "Try again") {
                login()
            }
        } else {
            super.handleException(exception)
        }
    }

    override fun startMainActivity() {
        hideKeyboard()
        startActivity(intentFor<MainActivity>().addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK))
    }

    override fun invalidEmailOrPassword() {
        emailTIL.error = " "
        passwordTIL.error = "Invalid e-mail or password"
    }

    override fun setLoading(isLoading: Boolean) {
        if (isLoading) {
            loginBt.visibility = View.INVISIBLE
            loadingAnimationPB.visibility = View.VISIBLE
        } else {
            loadingAnimationPB.visibility = View.INVISIBLE
            loginBt.visibility = View.VISIBLE
        }
    }

    private fun initLoginButtonListener() {
        loginBt.setOnClickListener {
            login()
        }
    }


    private fun initOnSubmitListener() {
        passwordTIET.onSubmit {
            login()
        }
    }

    private fun login() {
        emailTIL.error = null
        passwordTIL.error = null
        val email = emailTIET.text.toString()
        val password = passwordTIET.text.toString()
        presenter.loginEmail(email, password)
    }
}
