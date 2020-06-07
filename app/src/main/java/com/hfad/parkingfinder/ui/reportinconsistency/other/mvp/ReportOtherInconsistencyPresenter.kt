package com.hfad.parkingfinder.ui.reportinconsistency.other.mvp

import com.hfad.parkingfinder.apicalls.report.ReportCalls
import com.hfad.parkingfinder.apicalls.report.dto.OtherInconsistencyDto
import com.hfad.parkingfinder.ui.reportinconsistency.other.ReportOtherInconsistencyActivity
import com.hfad.parkingfinder.utils.mvp.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ReportOtherInconsistencyPresenter(private val activity: ReportOtherInconsistencyActivity, private val reportCalls: ReportCalls) : BasePresenter {

    override val compositeDisposable = CompositeDisposable()

    fun reportOtherInconsistency(otherInconsistencyDto: OtherInconsistencyDto) {
        if (otherInconsistencyDto.description.isEmpty()) {
            activity.showEmptyErr()
        } else {
            compositeDisposable.addAll(
                    reportCalls.reportOtherInconsistency(otherInconsistencyDto)
                            .subscribeOn(Schedulers.newThread())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    {
                                        if (it.isSuccessful) {
                                            activity.showToast("Thanks for the info!")
                                            activity.hideKeyboard()
                                            activity.onBackPressed()
                                        } else if (!activity.handleHttpException(it.code())) {
                                            activity.showTryAgainDialog(otherInconsistencyDto)
                                        }
                                        activity.setLoading(false)
                                    },
                                    {
                                        activity.showTryAgainDialog(otherInconsistencyDto)
                                    }
                            )
            )
        }
    }
}
