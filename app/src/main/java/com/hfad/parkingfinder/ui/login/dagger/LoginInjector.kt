package com.hfad.parkingfinder.ui.login.dagger

import com.hfad.parkingfinder.restclient.dagger.RetrofitClientModule
import com.hfad.parkingfinder.dagger.DaggerAppComponent
import com.hfad.parkingfinder.dagger.IDaggerInjector
import com.hfad.parkingfinder.database.sharedpreferences.dagger.PreferencesManagerModule
import com.hfad.parkingfinder.ui.login.LoginActivity

class LoginInjector : IDaggerInjector<LoginActivity> {
    override fun inject(target: LoginActivity) {
        val appComponent = DaggerAppComponent.builder()
                .preferencesManagerModule(PreferencesManagerModule())
                .retrofitClientModule(RetrofitClientModule())
                .build()

        appComponent.plus(LoginModule(target))
                .inject(target)
    }
}