package com.hfad.parkingfinder.ui.login.mvp

import com.hfad.parkingfinder.restclient.auth.dto.UserCredentialsDto
import com.hfad.parkingfinder.utils.PreferencesManager
import com.hfad.parkingfinder.utils.validator.Validator
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class LoginPresenter(private val view: LoginMVP.View,
                     private val createAccountCalls: LoginMVP.LoginCallsModel,
                     private val preferencesManager: PreferencesManager,
                     private val validator: Validator) : LoginMVP.Presenter {

    override val compositeDisposable = CompositeDisposable()

    override fun loginEmail(email: String, password: String) {

        if (validator.validateEmail(email).not() or validator.validatePassword(password).not()) {
            view.invalidEmailOrPassword()
            return
        }
        view.setLoading(true)
        compositeDisposable.add(createAccountCalls.loginEmail(UserCredentialsDto(email, password))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            if (it.isSuccessful) {
                                preferencesManager.saveAccessToken(it.body()!!.accessToken)
                                preferencesManager.saveRefreshToken(it.body()!!.refreshToken)
                                view.startMainActivity()
                            } else {
                                view.invalidEmailOrPassword()
                            }
                            view.setLoading(false)
                        },
                        {
                            view.handleException(it)
                            view.setLoading(false)
                        }
                )
        )
    }
}