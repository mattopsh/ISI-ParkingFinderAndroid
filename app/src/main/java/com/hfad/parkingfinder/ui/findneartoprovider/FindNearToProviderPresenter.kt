package com.hfad.parkingfinder.ui.findneartoprovider

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

class FindNearToProviderPresenter(private val activity: FindNearToProviderActivity, private val parkingCalls: ParkingCalls) : BasePresenter {

    fun findParking(pageNumber: Int,
                    pageSize: Int,
                    freeSpaces: FreeSpaces?,
                    dataVeracity: DataVeracity?,
                    cost: Cost?,
                    maxDistance: Int?,
                    providerAttitude: Double,
                    providerLongitude: Double,
                    append: Boolean = false) {

        activity.setLoading(true)
        compositeDisposable.addAll(
                Single.fromCallable { LocationProvider.getLocation(activity) }
                        .flatMap {
                            parkingCalls.findNearToProvider(pageNumber, pageSize, freeSpaces, dataVeracity, cost, maxDistance, it.latitude, it.longitude, providerAttitude, providerLongitude)
                        }
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                {
                                    if (it.isSuccessful) {
                                        if (append) {
                                            activity.appendParkingRV(it.body()!!)
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

    override val compositeDisposable = CompositeDisposable()
}
