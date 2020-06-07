package com.hfad.parkingfinder.ui.findneartoprovider.dagger

import com.hfad.parkingfinder.dagger.DaggerAppComponent
import com.hfad.parkingfinder.dagger.IDaggerInjector
import com.hfad.parkingfinder.ui.findneartoprovider.FindNearToProviderActivity

class FindNearToProviderInjector : IDaggerInjector<FindNearToProviderActivity> {
    override fun inject(target: FindNearToProviderActivity) {
        val appComponent = DaggerAppComponent.builder()
                .build()

        appComponent.plus(FindNearToProviderModule(target))
                .inject(target)
    }
}


