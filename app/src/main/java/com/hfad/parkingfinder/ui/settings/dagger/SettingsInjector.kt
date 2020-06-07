package com.hfad.parkingfinder.ui.settings.dagger

import com.hfad.parkingfinder.dagger.DaggerAppComponent
import com.hfad.parkingfinder.dagger.IDaggerInjector
import com.hfad.parkingfinder.ui.settings.SettingsActivity

class SettingsInjector : IDaggerInjector<SettingsActivity> {
    override fun inject(target: SettingsActivity) {
        val appComponent = DaggerAppComponent.builder()
                .build()

        appComponent.plus(SettingsModule(target))
                .inject(target)
    }
}
