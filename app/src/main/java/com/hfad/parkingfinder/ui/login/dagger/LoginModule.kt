package com.hfad.parkingfinder.ui.login.dagger

import com.hfad.parkingfinder.apicalls.dagger.RetrofitBasicClient
import com.hfad.parkingfinder.database.sharedpreferences.PreferencesManager
import com.hfad.parkingfinder.ui.login.LoginActivity
import com.hfad.parkingfinder.ui.login.mvp.LoginCalls
import com.hfad.parkingfinder.ui.login.mvp.LoginMVP
import com.hfad.parkingfinder.ui.login.mvp.LoginPresenter
import com.hfad.parkingfinder.utils.validator.Validator

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class LoginModule(private val activity: LoginActivity) {

    @LoginScope
    @Provides
    fun providePresenter(loginCallsModel: LoginMVP.LoginCallsModel,
                         preferencesManager: PreferencesManager,
                         validator: Validator
    ): LoginMVP.Presenter {
        return LoginPresenter(activity, loginCallsModel, preferencesManager, validator)
    }

    @LoginScope
    @Provides
    fun provideCreateAccountCalls(@RetrofitBasicClient
                                  retrofit: Retrofit): LoginMVP.LoginCallsModel {
        return LoginCalls(retrofit)
    }

}