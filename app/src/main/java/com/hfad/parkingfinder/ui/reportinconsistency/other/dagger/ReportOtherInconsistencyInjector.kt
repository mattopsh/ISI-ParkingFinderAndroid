package com.hfad.parkingfinder.ui.reportinconsistency.other.dagger

import com.hfad.parkingfinder.dagger.DaggerAppComponent
import com.hfad.parkingfinder.dagger.IDaggerInjector
import com.hfad.parkingfinder.ui.reportinconsistency.other.ReportOtherInconsistencyActivity

class ReportOtherInconsistencyInjector : IDaggerInjector<ReportOtherInconsistencyActivity> {
    override fun inject(target: ReportOtherInconsistencyActivity) {
        val appComponent = DaggerAppComponent.builder()
                .build()

        appComponent.plus(ReportOtherInconsistencyModule(target))
                .inject(target)
    }
}
