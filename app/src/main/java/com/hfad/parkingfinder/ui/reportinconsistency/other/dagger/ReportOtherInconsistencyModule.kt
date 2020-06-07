package com.hfad.parkingfinder.ui.reportinconsistency.other.dagger

import com.hfad.parkingfinder.apicalls.report.ReportCalls
import com.hfad.parkingfinder.ui.reportinconsistency.other.ReportOtherInconsistencyActivity
import com.hfad.parkingfinder.ui.reportinconsistency.other.mvp.ReportOtherInconsistencyPresenter
import dagger.Module
import dagger.Provides

@Module
class ReportOtherInconsistencyModule(private val activity: ReportOtherInconsistencyActivity) {
    @ReportOtherInconsistencyScope
    @Provides
    fun providePresenter(reportCalls: ReportCalls): ReportOtherInconsistencyPresenter {
        return ReportOtherInconsistencyPresenter(activity, reportCalls)
    }
}
