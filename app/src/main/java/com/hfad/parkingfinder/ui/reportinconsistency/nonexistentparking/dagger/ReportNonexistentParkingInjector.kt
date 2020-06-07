package com.hfad.parkingfinder.ui.reportinconsistency.nonexistentparking.dagger

import com.hfad.parkingfinder.dagger.DaggerAppComponent
import com.hfad.parkingfinder.dagger.IDaggerInjector
import com.hfad.parkingfinder.ui.reportinconsistency.nonexistentparking.ReportNonexistentParkingActivity

class ReportNonexistentParkingInjector : IDaggerInjector<ReportNonexistentParkingActivity> {
    override fun inject(target: ReportNonexistentParkingActivity) {
        val appComponent = DaggerAppComponent.builder()
                .build()

        appComponent.plus(ReportNonexistentParkingModule(target))
                .inject(target)
    }
}
