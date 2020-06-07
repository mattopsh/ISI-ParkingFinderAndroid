package com.hfad.parkingfinder.ui.reportinconsistency.newparking.additionaldetails.dagger

import com.hfad.parkingfinder.apicalls.report.ReportCalls
import com.hfad.parkingfinder.ui.reportinconsistency.newparking.additionaldetails.ReportNewParkingDetailsActivity
import com.hfad.parkingfinder.ui.reportinconsistency.newparking.additionaldetails.mvp.ReportNewParkingDetailsPresenter
import dagger.Module
import dagger.Provides

@Module
class ReportNewParkingDetailsModule(private val activity: ReportNewParkingDetailsActivity) {
    @ReportNewParkingDetailsScope
    @Provides
    fun providePresenter(reportCalls: ReportCalls): ReportNewParkingDetailsPresenter {
        return ReportNewParkingDetailsPresenter(activity, reportCalls)
    }
}
