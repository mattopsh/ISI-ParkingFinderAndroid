package com.hfad.parkingfinder.ui.createaccount.dagger

import com.hfad.parkingfinder.apicalls.dagger.RetrofitClientModule
import com.hfad.parkingfinder.dagger.DaggerAppComponent
import com.hfad.parkingfinder.dagger.IDaggerInjector
import com.hfad.parkingfinder.database.sharedpreferences.dagger.PreferencesManagerModule
import com.hfad.parkingfinder.ui.createaccount.CreateAccountActivity

class CreateAccountInjector : IDaggerInjector<CreateAccountActivity> {
    override fun inject(target: CreateAccountActivity) {
        val appComponent = DaggerAppComponent.builder()
                .preferencesManagerModule(PreferencesManagerModule())
                .retrofitClientModule(RetrofitClientModule())
                .build()

        appComponent.plus(CreateAccountModule(target))
                .inject(target)
    }

}