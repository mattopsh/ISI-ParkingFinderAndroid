package com.hfad.parkingfinder.ui.findbylocation

import com.hfad.parkingfinder.apicalls.parking.ParkingCalls
import com.hfad.parkingfinder.apicalls.parking.dto.DataVeracity
import com.hfad.parkingfinder.apicalls.parking.dto.FreeSpaces
import com.hfad.parkingfinder.apicalls.report.dto.Cost
import com.hfad.parkingfinder.utils.location.LocationProvider
import com.hfad.parkingfinder.utils.mvp.BasePresenter
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class FindByLocationPresenter(private val activity: FindByLocationActivity, private val parkingCalls: ParkingCalls) : BasePresenter {

    override val compositeDisposable = CompositeDisposable()

    fun findParking(pageNumber: Int,
                    pageSize: Int,
                    freeSpaces: FreeSpaces?,
                    dataVeracity: DataVeracity?,
                    cost: Cost?,
                    maxDistance: Int?,
                    parkingNameOrAddress: String?,
                    append: Boolean = false) {

        activity.setLoading(true)
        compositeDisposable.addAll(
                Single.fromCallable { LocationProvider.getLocation(activity) }
                        .flatMap {
                            parkingCalls.findNearestParking(pageNumber, pageSize, freeSpaces, dataVeracity, cost, maxDistance, parkingNameOrAddress, it.latitude, it.longitude)
                        }
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                {
                                    if (it.isSuccessful) {
                                        if (append) {
                                            activity.appendToParkingRV(it.body()!!)
                                        } else {
                                            activity.updateParkingRV(it.body()!!)
                                        }
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
}
