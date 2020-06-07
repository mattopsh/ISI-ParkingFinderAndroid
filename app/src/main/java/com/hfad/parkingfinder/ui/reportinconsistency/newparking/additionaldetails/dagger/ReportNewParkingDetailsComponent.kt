package com.hfad.parkingfinder.ui.reportinconsistency.newparking.additionaldetails.dagger

import com.hfad.parkingfinder.ui.reportinconsistency.newparking.additionaldetails.ReportNewParkingDetailsActivity
import dagger.Subcomponent

@ReportNewParkingDetailsScope
@Subcomponent(modules = [ReportNewParkingDetailsModule::class])
interface ReportNewParkingDetailsComponent {
    fun inject(reportNewParkingDetailsActivity: ReportNewParkingDetailsActivity)
}
