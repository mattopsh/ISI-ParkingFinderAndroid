package com.hfad.parkingfinder.ui.reportparkingstate.dagger

import com.hfad.parkingfinder.ui.reportparkingstate.ReportParkingStateActivity
import dagger.Subcomponent

@ReportParkingStateScope
@Subcomponent(modules = [ReportParkingStateModule::class])
interface ReportParkingStateComponent {
    fun inject(reportParkingStateActivity: ReportParkingStateActivity)
}
