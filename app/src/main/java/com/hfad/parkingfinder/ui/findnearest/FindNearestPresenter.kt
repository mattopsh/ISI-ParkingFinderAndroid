package com.hfad.parkingfinder.ui.findnearest

import android.content.Intent
import android.net.Uri
import com.hfad.parkingfinder.apicalls.parking.ParkingCalls
import com.hfad.parkingfinder.apicalls.parking.dto.DataVeracity
import com.hfad.parkingfinder.apicalls.parking.dto.FreeSpaces
import com.hfad.parkingfinder.apicalls.parking.dto.ParkingByLocationDto
import com.hfad.parkingfinder.apicalls.report.dto.Cost
import com.hfad.parkingfinder.utils.location.LocationProvider
import com.hfad.parkingfinder.utils.mvp.BasePresenter
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class FindNearestPresenter(private val activity: FindNearestActivity, private val parkingCalls: ParkingCalls) : BasePresenter {
    fun findParking(pageNumber: Int,
                    pageSize: Int,
                    freeSpaces: FreeSpaces?,
                    dataVeracity: DataVeracity?,
                    cost: Cost?,
                    maxDistance: Int?,
                    append: Boolean = false) {

        activity.setLoading(true)
        compositeDisposable.addAll(
                Single.fromCallable { LocationProvider.getLocation(activity) }
                        .flatMap {
                            parkingCalls.findNearestParking(pageNumber, pageSize, freeSpaces, dataVeracity, cost, maxDistance, null, it.latitude, it.longitude)
                        }
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                {
                                    if (it.isSuccessful) {
                                        if (append){
                                            activity.appendParkingRV(it.body()!!)
                                        }else{
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

    fun selectAutomatically(parkingData: ArrayList<ParkingByLocationDto>) {
        val bestParking = AutoSelectParking.selectAutomatically(parkingData)
        if (bestParking !== null) {
            val intent = Intent(android.content.Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?daddr=${bestParking.attitude},${bestParking.longitude}"))
            activity.startActivity(intent)
        } else {
            activity.showToast("Cannot select parking for your criteria")
        }
    }

    override val compositeDisposable = CompositeDisposable()
}
