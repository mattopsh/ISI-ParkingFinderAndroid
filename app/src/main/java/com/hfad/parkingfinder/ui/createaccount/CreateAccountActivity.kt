package com.hfad.parkingfinder.ui.createaccount

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.View
import com.hfad.parkingfinder.R
import com.hfad.parkingfinder.ui.base.BaseActivity
import com.hfad.parkingfinder.ui.main.MainActivity
import com.hfad.parkingfinder.ui.createaccount.dagger.CreateAccountInjector
import com.hfad.parkingfinder.ui.createaccount.mvp.CreateAccountMVP
import com.hfad.parkingfinder.utils.edittext.onSubmit
import kotlinx.android.synthetic.main.activity_create_account.*
import org.jetbrains.anko.intentFor
import java.net.ConnectException
import javax.inject.Inject


class CreateAccountActivity : BaseActivity(), CreateAccountMVP.View {

    @Inject
    lateinit var presenter: CreateAccountMVP.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)
        CreateAccountInjector().inject(this)
        initBackArrowButtonListener(backArrowIV)
        initLogInButtonListener()
        initOnSubmitListener()
        showKeyboard(emailTIET)
    }

    override fun onDestroy() {
        presenter.disposeRX()
        super.onDestroy()
    }

    override fun setLoading(isLoading: Boolean) {
        if (isLoading) {
            registerBt.visibility = View.INVISIBLE
            loadingAnimationPB.visibility = View.VISIBLE
        } else {
            loadingAnimationPB.visibility = View.INVISIBLE
            registerBt.visibility = View.VISIBLE
        }
    }

    override fun handleException(exception: Throwable) {
        if (exception is ConnectException) {
            showSnackbar("No internet connection", Snackbar.LENGTH_LONG, "Try again") {
                createAccount()
            }
        } else {
            super.handleException(exception)
        }
    }

    override fun startMainActivity() {
        hideKeyboard()
        startActivity(intentFor<MainActivity>().addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK))
    }

    override fun emailAlreadyExists() {
        emailTIL.error = "Email already exists"
    }

    override fun invalidEmail() {
        emailTIL.error = "Invalid email format"
    }

    override fun invalidPassword() {
        passwordTIL.error = "At least 8 characters required"
    }


    private fun initLogInButtonListener() {
        registerBt.setOnClickListener {
            createAccount()
        }
    }

    private fun initOnSubmitListener() {
        passwordTIET.onSubmit {
            createAccount()
        }
    }

    private fun createAccount() {
        emailTIL.error = null
        passwordTIL.error = null
        val email = emailTIET.text.toString()
        val password = passwordTIET.text.toString()
        presenter.createAccount(email, password)
    }
}
