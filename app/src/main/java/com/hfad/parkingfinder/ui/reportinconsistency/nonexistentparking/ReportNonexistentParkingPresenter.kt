package com.hfad.parkingfinder.ui.reportinconsistency.nonexistentparking

import com.hfad.parkingfinder.apicalls.parking.ParkingCalls
import com.hfad.parkingfinder.apicalls.report.ReportCalls
import com.hfad.parkingfinder.utils.location.LocationProvider
import com.hfad.parkingfinder.utils.mvp.BasePresenter
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ReportNonexistentParkingPresenter(private val activity: ReportNonexistentParkingActivity, private val reportCalls: ReportCalls, private val parkingCalls: ParkingCalls) : BasePresenter {
    fun findParking(pageNumber: Int, pageSize: Int, parkingNameOrAddress: String?) {
        activity.setLoading(true)
        compositeDisposable.addAll(
                Single.fromCallable { LocationProvider.getLocation(activity) }
                        .flatMap {
                            parkingCalls.findNearestParking(pageNumber, pageSize, null, null, null, null, parkingNameOrAddress, it.latitude, it.longitude)
                        }
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                {
                                    if (it.isSuccessful) {
                                        activity.updateParkingRV(it.body()!!)
                                    } else {
                                        activity.showToast("No internet connection")
                                    }
                                    activity.setLoading(false)
                                },
                                {
                                    if (it is NullPointerException) {
                                        activity.showToast("Cannot retrieve location")
                                    } else {
                                        activity.showToast("No internet connection")
                                    }
                                    activity.setLoading(false)
                                }
                        )
        )
    }

    fun reportNonexistentParking(parkingNodeId: Long) {
        activity.setLoadingDialog(true)
        compositeDisposable.addAll(
                reportCalls.reportNonexistentParking(parkingNodeId)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                {
                                    if (it.isSuccessful) {
                                        activity.showToast("Thanks for the info!")
                                        activity.onBackPressed()
                                    } else if (!activity.handleHttpException(it.code())) {
                                        activity.showTryAgainDialog(parkingNodeId)
                                    }
                                    activity.setLoadingDialog(false)
                                },
                                {

                                    activity.showTryAgainDialog(parkingNodeId)
                                    activity.setLoadingDialog(false)
                                }
                        )
        )
    }

    override val compositeDisposable = CompositeDisposable()
}
