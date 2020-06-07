package com.hfad.parkingfinder.ui.findbylocation.dagger

import com.hfad.parkingfinder.dagger.DaggerAppComponent
import com.hfad.parkingfinder.dagger.IDaggerInjector
import com.hfad.parkingfinder.ui.findbylocation.FindByLocationActivity

class FindByLocationInjector : IDaggerInjector<FindByLocationActivity> {
    override fun inject(target: FindByLocationActivity) {
        val appComponent = DaggerAppComponent.builder()
                .build()

        appComponent.plus(FindByLocationModule(target))
                .inject(target)
    }
}
