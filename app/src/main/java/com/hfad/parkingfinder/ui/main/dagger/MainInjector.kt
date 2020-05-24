package com.hfad.parkingfinder.ui.main.dagger

import com.hfad.parkingfinder.dagger.DaggerAppComponent
import com.hfad.parkingfinder.database.room.dagger.ParkingDbModule
import com.hfad.parkingfinder.ui.main.MainActivity

class MainInjector {
    fun inject(target: MainActivity) {
        val appComponent = DaggerAppComponent.builder()
                .build()

        appComponent.plus(MainModule(target))
                .inject(target)
    }
}
