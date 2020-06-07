package com.hfad.parkingfinder.ui.reportinconsistency.other.dagger

import com.hfad.parkingfinder.ui.reportinconsistency.other.ReportOtherInconsistencyActivity
import dagger.Subcomponent

@ReportOtherInconsistencyScope
@Subcomponent(modules = [ReportOtherInconsistencyModule::class])
interface ReportOtherInconsistencyComponent {
    fun inject(reportOtherInconsistencyActivity: ReportOtherInconsistencyActivity)
}
