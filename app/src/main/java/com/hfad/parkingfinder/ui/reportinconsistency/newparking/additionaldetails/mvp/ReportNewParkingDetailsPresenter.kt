package com.hfad.parkingfinder.ui.reportinconsistency.newparking.additionaldetails.mvp

import com.hfad.parkingfinder.apicalls.report.ReportCalls
import com.hfad.parkingfinder.apicalls.report.dto.Cost
import com.hfad.parkingfinder.apicalls.report.dto.NewParkingReportDto
import com.hfad.parkingfinder.ui.reportinconsistency.newparking.additionaldetails.ReportNewParkingDetailsActivity
import com.hfad.parkingfinder.utils.mvp.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ReportNewParkingDetailsPresenter(private val activity: ReportNewParkingDetailsActivity, private val reportCalls: ReportCalls) : BasePresenter {

    override val compositeDisposable = CompositeDisposable()

    fun reportNewParking(attitude: Double, longitude: Double, capacity: Int?, stayCost: Cost?, otherInfo: String?) {
        activity.setLoadingDialog(true)
        compositeDisposable.addAll(
                reportCalls.reportNewParking(NewParkingReportDto(attitude, longitude, capacity, stayCost, otherInfo))
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                {
                                    if (it.isSuccessful) {
                                        activity.showToast("Thanks for the info!")
                                        activity.onBackPressed()
                                    } else if (!activity.handleHttpException(it.code())) {
                                        activity.showTryAgainDialog()
                                    }
                                    activity.setLoadingDialog(false)
                                },
                                {
                                    activity.showTryAgainDialog()
                                    activity.setLoadingDialog(false)
                                }
                        )
        )
    }

}
