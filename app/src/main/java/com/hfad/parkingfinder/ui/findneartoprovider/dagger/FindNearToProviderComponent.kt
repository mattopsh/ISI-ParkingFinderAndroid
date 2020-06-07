package com.hfad.parkingfinder.ui.findneartoprovider.dagger

import com.hfad.parkingfinder.ui.findneartoprovider.FindNearToProviderActivity
import dagger.Subcomponent

@FindNearToProviderScope
@Subcomponent(modules = [FindNearToProviderModule::class])
interface FindNearToProviderComponent {
    fun inject(findNearToProviderActivity: FindNearToProviderActivity)
}
