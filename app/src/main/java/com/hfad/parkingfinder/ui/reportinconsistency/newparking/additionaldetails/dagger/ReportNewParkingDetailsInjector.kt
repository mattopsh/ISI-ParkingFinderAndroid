package com.hfad.parkingfinder.ui.reportinconsistency.newparking.additionaldetails.dagger

import com.hfad.parkingfinder.dagger.DaggerAppComponent
import com.hfad.parkingfinder.dagger.IDaggerInjector
import com.hfad.parkingfinder.ui.reportinconsistency.newparking.additionaldetails.ReportNewParkingDetailsActivity

class ReportNewParkingDetailsInjector : IDaggerInjector<ReportNewParkingDetailsActivity> {
    override fun inject(target: ReportNewParkingDetailsActivity) {
        val appComponent = DaggerAppComponent.builder()
                .build()

        appComponent.plus(ReportNewParkingDetailsModule(target))
                .inject(target)
    }
}
