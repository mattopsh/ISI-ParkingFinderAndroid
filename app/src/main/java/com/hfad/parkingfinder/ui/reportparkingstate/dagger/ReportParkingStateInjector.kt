package com.hfad.parkingfinder.ui.reportparkingstate.dagger

import com.hfad.parkingfinder.dagger.DaggerAppComponent
import com.hfad.parkingfinder.dagger.IDaggerInjector
import com.hfad.parkingfinder.ui.reportparkingstate.ReportParkingStateActivity

class ReportParkingStateInjector : IDaggerInjector<ReportParkingStateActivity> {
    override fun inject(target: ReportParkingStateActivity) {
        val appComponent = DaggerAppComponent.builder()
                .build()

        appComponent.plus(ReportParkingStateModule(target))
                .inject(target)
    }
}
