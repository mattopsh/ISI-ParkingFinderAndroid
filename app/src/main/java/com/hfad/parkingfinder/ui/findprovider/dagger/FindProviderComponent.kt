package com.hfad.parkingfinder.ui.findprovider.dagger

import com.hfad.parkingfinder.ui.findprovider.FindProviderActivity
import dagger.Subcomponent

@FindProviderScope
@Subcomponent(modules = [FindProviderModule::class])
interface FindProviderComponent {
    fun inject(findProviderActivity: FindProviderActivity)
}
