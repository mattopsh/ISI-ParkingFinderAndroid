package com.hfad.parkingfinder.ui.createaccount.mvp


import com.hfad.parkingfinder.apicalls.auth.dto.UserCredentialsDto
import com.hfad.parkingfinder.utils.validator.Validator
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CreateAccountPresenter(private val view: CreateAccountMVP.View,
                             private val createAccountCalls: CreateAccountMVP.CreateAccountCallsModel,
                             private val preferencesManager: PreferencesManager,
                             private val validator: Validator) : CreateAccountMVP.Presenter {

    override val compositeDisposable = CompositeDisposable()

    override fun createAccount(email: String, password: String) {
        if (validator.validateEmail(email).not()) {
            view.invalidEmail()
            return
        }
        if (validator.validatePassword(password).not()) {
            view.invalidPassword()
            return
        }
        view.setLoading(true)
        compositeDisposable.add(createAccountCalls.createAccount(UserCredentialsDto(email, password))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            if (it.isSuccessful) {
                                preferencesManager.saveAccessToken(it.body()!!.accessToken)
                                preferencesManager.saveRefreshToken(it.body()!!.refreshToken)
                                view.startMainActivity()
                            } else {
                                if (it.code() == 409) {
                                    view.emailAlreadyExists()
                                }
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