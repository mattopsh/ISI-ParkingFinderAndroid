package com.hfad.parkingfinder.ui.findprovider.dagger

import com.hfad.parkingfinder.dagger.DaggerAppComponent
import com.hfad.parkingfinder.dagger.IDaggerInjector
import com.hfad.parkingfinder.ui.findprovider.FindProviderActivity

class FindProviderInjector : IDaggerInjector<FindProviderActivity> {
    override fun inject(target: FindProviderActivity) {
        val appComponent = DaggerAppComponent.builder()
                .build()

        appComponent.plus(FindProviderModule(target))
                .inject(target)
    }
}
