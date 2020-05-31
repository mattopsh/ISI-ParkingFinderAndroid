package com.hfad.parkingfinder.ui.reportparkingstate.dagger

import com.hfad.parkingfinder.restclient.report.ReportCalls
import com.hfad.parkingfinder.ui.reportparkingstate.ReportParkingStateActivity
import com.hfad.parkingfinder.ui.reportparkingstate.mvp.ReportParkingStatePresenter
import dagger.Module
import dagger.Provides

@Module
class ReportParkingStateModule(private val activity: ReportParkingStateActivity) {
    @ReportParkingStateScope
    @Provides
    fun providePresenter(reportCalls: ReportCalls): ReportParkingStatePresenter {
        return ReportParkingStatePresenter(activity, reportCalls)
    }
}
