package com.hfad.parkingfinder.ui.findnearest.dagger

import com.hfad.parkingfinder.dagger.DaggerAppComponent
import com.hfad.parkingfinder.dagger.IDaggerInjector
import com.hfad.parkingfinder.database.room.dagger.ParkingDbModule
import com.hfad.parkingfinder.ui.findnearest.FindNearestActivity

class FindNearestInjector : IDaggerInjector<FindNearestActivity> {
    override fun inject(target: FindNearestActivity) {
        val appComponent = DaggerAppComponent.builder()
                .build()

        appComponent.plus(FindNearestModule(target))
                .inject(target)
    }
}
