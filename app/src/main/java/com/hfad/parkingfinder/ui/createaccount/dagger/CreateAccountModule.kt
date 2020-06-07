package com.hfad.parkingfinder.ui.createaccount.dagger

import com.hfad.parkingfinder.apicalls.dagger.RetrofitBasicClient
import com.hfad.parkingfinder.database.sharedpreferences.PreferencesManager
import com.hfad.parkingfinder.ui.createaccount.CreateAccountActivity
import com.hfad.parkingfinder.ui.createaccount.mvp.CreateAccountCalls
import com.hfad.parkingfinder.ui.createaccount.mvp.CreateAccountMVP
import com.hfad.parkingfinder.ui.createaccount.mvp.CreateAccountPresenter
import com.hfad.parkingfinder.utils.validator.Validator
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class CreateAccountModule(private val activity: CreateAccountActivity) {
    @CreateAccountScope
    @Provides
    fun providePresenter(createAccountCalls: CreateAccountMVP.CreateAccountCallsModel,
                         preferencesManager: PreferencesManager,
                         validator: Validator): CreateAccountMVP.Presenter {

        return CreateAccountPresenter(activity, createAccountCalls, preferencesManager, validator)
    }

    @CreateAccountScope
    @Provides
    fun provideCreateAccountCalls(@RetrofitBasicClient
                                  retrofit: Retrofit): CreateAccountMVP.CreateAccountCallsModel {

        return CreateAccountCalls(retrofit)
    }
}