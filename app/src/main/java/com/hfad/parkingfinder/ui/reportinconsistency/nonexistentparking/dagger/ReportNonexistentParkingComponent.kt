package com.hfad.parkingfinder.ui.reportinconsistency.nonexistentparking.dagger

import com.hfad.parkingfinder.ui.reportinconsistency.nonexistentparking.ReportNonexistentParkingActivity
import dagger.Subcomponent

@ReportNonexistentParkingScope
@Subcomponent(modules = [ReportNonexistentParkingModule::class])
interface ReportNonexistentParkingComponent {
    fun inject(reportNonexistentParkingActivity: ReportNonexistentParkingActivity)
}
