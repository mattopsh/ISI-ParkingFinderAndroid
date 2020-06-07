package com.hfad.parkingfinder.ui.findprovider.dagger

import com.hfad.parkingfinder.apicalls.provider.ProviderCalls
import com.hfad.parkingfinder.ui.findprovider.FindProviderActivity
import com.hfad.parkingfinder.ui.findprovider.FindProviderPresenter

import dagger.Module
import dagger.Provides

@Module
class FindProviderModule(private val activity: FindProviderActivity) {

    @FindProviderScope
    @Provides
    fun providePresenter(providerCalls: ProviderCalls): FindProviderPresenter {
        return FindProviderPresenter(activity, providerCalls)
    }
}
