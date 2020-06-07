package com.hfad.parkingfinder.ui.reportinconsistency.nonexistentparking.dagger

import com.hfad.parkingfinder.apicalls.parking.ParkingCalls
import com.hfad.parkingfinder.apicalls.report.ReportCalls
import com.hfad.parkingfinder.ui.reportinconsistency.nonexistentparking.ReportNonexistentParkingActivity
import com.hfad.parkingfinder.ui.reportinconsistency.nonexistentparking.ReportNonexistentParkingPresenter
import dagger.Module
import dagger.Provides

@Module
class ReportNonexistentParkingModule(private val activity: ReportNonexistentParkingActivity) {
    @ReportNonexistentParkingScope
    @Provides
    fun providePresenter(reportCalls: ReportCalls, parkingCalls: ParkingCalls): ReportNonexistentParkingPresenter {
        return ReportNonexistentParkingPresenter(activity, reportCalls, parkingCalls)
    }
}
