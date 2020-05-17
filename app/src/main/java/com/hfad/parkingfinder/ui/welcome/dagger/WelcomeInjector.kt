package com.hfad.parkingfinder.ui.welcome.dagger

import com.hfad.parkingfinder.apicalls.dagger.RetrofitClientModule
import com.hfad.parkingfinder.dagger.DaggerAppComponent
import com.hfad.parkingfinder.dagger.IDaggerInjector
import com.hfad.parkingfinder.database.sharedpreferences.dagger.PreferencesManagerModule
import com.hfad.parkingfinder.ui.welcome.WelcomeActivity

class WelcomeInjector : IDaggerInjector<WelcomeActivity> {
    override fun inject(target: WelcomeActivity) {
        val appComponent = DaggerAppComponent.builder()
                .preferencesManagerModule(PreferencesManagerModule())
                .retrofitClientModule(RetrofitClientModule())
                .build()

        appComponent.plus(WelcomeModule(target))
                .inject(target)
    }

}