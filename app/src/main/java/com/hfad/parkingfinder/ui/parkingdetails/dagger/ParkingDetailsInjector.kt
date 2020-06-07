package com.hfad.parkingfinder.ui.parkingdetails.dagger

import com.hfad.parkingfinder.dagger.DaggerAppComponent
import com.hfad.parkingfinder.dagger.IDaggerInjector
import com.hfad.parkingfinder.database.room.dagger.ParkingDbModule
import com.hfad.parkingfinder.ui.parkingdetails.ParkingDetailsActivity

class ParkingDetailsInjector : IDaggerInjector<ParkingDetailsActivity> {
    override fun inject(target: ParkingDetailsActivity) {
        val appComponent = DaggerAppComponent.builder()
                .parkingDbModule(ParkingDbModule(target))
                .build()

        appComponent.plus(ParkingDetailsModule(target))
                .inject(target)
    }
}
