package com.hfad.parkingfinder.ui.settings.dagger

import com.hfad.parkingfinder.ui.settings.SettingsActivity
import dagger.Subcomponent

@SettingsScope
@Subcomponent(modules = [SettingsModule::class])
interface SettingsComponent {
    fun inject(settingsActivity: SettingsActivity)
}
