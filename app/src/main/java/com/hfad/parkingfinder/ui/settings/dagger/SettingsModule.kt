package com.hfad.parkingfinder.ui.settings.dagger

import com.hfad.parkingfinder.ui.settings.SettingsActivity
import com.hfad.parkingfinder.ui.settings.mvp.SettingsPresenter
import dagger.Module
import dagger.Provides

@Module
class SettingsModule(private val activity: SettingsActivity) {
    @SettingsScope
    @Provides
    fun providePresenter(): SettingsPresenter {
        return SettingsPresenter(activity)
    }

}
