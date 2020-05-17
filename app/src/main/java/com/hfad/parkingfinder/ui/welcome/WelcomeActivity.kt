package com.hfad.parkingfinder.ui.welcome

import android.content.Intent
import android.os.Bundle
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.hfad.parkingfinder.R
import com.hfad.parkingfinder.ui.base.BaseActivity
import com.hfad.parkingfinder.ui.dialog.NoInternetConnectionDialog
import com.hfad.parkingfinder.ui.dialog.ProgressDialog
import com.hfad.parkingfinder.ui.login.LoginActivity
import com.hfad.parkingfinder.ui.main.MainActivity
import com.hfad.parkingfinder.ui.createaccount.CreateAccountActivity
import com.hfad.parkingfinder.ui.welcome.dagger.WelcomeInjector
import com.hfad.parkingfinder.ui.welcome.mvp.WelcomeMVP
import kotlinx.android.synthetic.main.activity_welcome.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivity
import javax.inject.Inject

class WelcomeActivity : BaseActivity(), WelcomeMVP.View {

    @Inject
    lateinit var presenter: WelcomeMVP.Presenter

    private val callbackManager = CallbackManager.Factory.create()
    private var progressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        WelcomeInjector().inject(this)
        initButtonsListeners()
    }

    override fun tryAgainDialog(fbToken: String) {
        val noInternetConnectionDialog = NoInternetConnectionDialog()
        noInternetConnectionDialog.onTryAgainListener = {
            presenter.sendFbAuthorizationData(fbToken)
        }
        noInternetConnectionDialog.show(fragmentManager, null)
    }

    override fun setLoading(isLoading: Boolean) {
        if (isLoading) {
            progressDialog?.dismiss()
            progressDialog = ProgressDialog()
            progressDialog!!.show(fragmentManager, null)
        } else {
            progressDialog?.dismiss()
        }
    }

    override fun startMainActivity() {
        startActivity(intentFor<MainActivity>().addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun initButtonsListeners() {
        initFbLogin()
        loginWithEmailBt.setOnClickListener {
            startActivity<LoginActivity>()
        }
        createAccountBt.setOnClickListener {
            startActivity<CreateAccountActivity>()
        }
    }

    private fun initFbLogin() {
        LoginManager.getInstance().registerCallback(callbackManager,
                object : FacebookCallback<LoginResult> {
                    override fun onSuccess(loginResult: LoginResult) {
                        presenter.sendFbAuthorizationData(loginResult.accessToken.token)
                    }

                    override fun onCancel() {}
                    override fun onError(exception: FacebookException) {}
                })
        loginWithFbTV.setOnClickListener {
            LoginManager.getInstance().logInWithReadPermissions(this, listOf("email", "public_profile"))
        }
    }

}
