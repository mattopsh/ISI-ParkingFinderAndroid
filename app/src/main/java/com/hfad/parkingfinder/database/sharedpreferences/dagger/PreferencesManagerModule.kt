package com.hfad.parkingfinder.database.sharedpreferences.dagger

import com.hfad.parkingfinder.database.sharedpreferences.PreferencesManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PreferencesManagerModule {

    @Singleton
    @Provides
    fun providePreferencesManager() = PreferencesManager

}