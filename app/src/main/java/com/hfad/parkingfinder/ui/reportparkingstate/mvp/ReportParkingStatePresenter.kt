package com.hfad.parkingfinder.ui.reportparkingstate.mvp


import android.location.LocationProvider
import com.hfad.parkingfinder.ui.reportparkingstate.ReportParkingStateActivity
import com.hfad.parkingfinder.utils.location.LocationProvider
import com.hfad.parkingfinder.utils.mvp.BasePresenter
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ReportParkingStatePresenter(private val activity: ReportParkingStateActivity, private val reportCalls: ReportCalls) : BasePresenter {

    override val compositeDisposable = CompositeDisposable()

    fun sendParkingState(freeSpaces: ParkingStateEnum) {
        activity.setLoading(true)
        var parkingStateDto: ParkingStateDto? = null
        compositeDisposable.addAll(
                Single.fromCallable { LocationProvider.getLocation(activity) }
                        .flatMap {
                            parkingStateDto = ParkingStateDto(it.latitude, it.longitude, freeSpaces.freeSpaces, null)
                            reportCalls.reportState(parkingStateDto!!)
                        }
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                {
                                    if (it.isSuccessful) {
                                        activity.showToast("Thanks for the info!")
                                        activity.onBackPressed()
                                    } else if (!activity.handleHttpException(it.code())) {
                                        activity.showTryAgainDialog(parkingStateDto!!)
                                    }
                                    activity.setLoading(false)
                                },
                                {
                                    activity.showTryAgainDialog(parkingStateDto!!)
                                    activity.setLoading(false)
                                }
                        )
        )
    }

    fun sendParkingState(parkingStateDto: ParkingStateDto) {
        activity.setLoading(true)
        compositeDisposable.addAll(
                reportCalls.reportState(ParkingStateDto(parkingStateDto.attitude, parkingStateDto.longitude, parkingStateDto.parkingState, null))
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                {
                                    if (it.isSuccessful) {
                                        activity.showToast("Thanks for the info!")
                                        activity.onBackPressed()
                                    } else if (!activity.handleHttpException(it.code())) {
                                        activity.showTryAgainDialog(parkingStateDto)
                                    }
                                    activity.setLoading(false)
                                },
                                {
                                    activity.showTryAgainDialog(parkingStateDto)
                                    activity.setLoading(false)
                                }
                        )
        )
    }
}
