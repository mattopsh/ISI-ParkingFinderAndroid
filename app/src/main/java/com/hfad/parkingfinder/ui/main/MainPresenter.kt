package com.hfad.parkingfinder.ui.main

import android.graphics.Bitmap
import android.location.Location
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.hfad.parkingfinder.apicalls.fb.FbCalls
import com.hfad.parkingfinder.apicalls.parking.ParkingCalls
import com.hfad.parkingfinder.apicalls.parking.dto.ParkingByLocationDto
import com.hfad.parkingfinder.database.room.dao.FavoriteParkingDao
import com.hfad.parkingfinder.database.room.entities.FavoriteParkingEntity
import com.hfad.parkingfinder.utils.location.LocationProvider
import com.hfad.parkingfinder.utils.mvp.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainPresenter(private val mainActivity: MainActivity,
                    private val parkingCalls: ParkingCalls,
                    private val fbCalls: FbCalls,
                    private val favoriteParkingDao: FavoriteParkingDao) : BasePresenter {

    override val compositeDisposable = CompositeDisposable()

    fun getFavoriteParkingState() {
        mainActivity.setLoading(true)
        var favoriteParkingList: List<FavoriteParkingEntity> = emptyList()
        var userLocation: Location? = null
        compositeDisposable.addAll(favoriteParkingDao.getFavoriteParkingList()
                .flatMapSingle {
                    favoriteParkingList = it
                    userLocation = LocationProvider.getLocation(mainActivity)
                    parkingCalls.getFavoriteParkingState(favoriteParkingList.map { it.parkingNodeId })
                }
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            if (it.isSuccessful) {
                                mainActivity.updateParkingRV(favoriteParkingList.zip(it.body()!!).map {
                                    ParkingByLocationDto(
                                            it.second.parkingNodeId,
                                            it.first.parkingAttitude,
                                            it.first.parkingLongitude,
                                            it.first.parkingName,
                                            it.first.parkingAddress,
                                            LocationProvider.getDistance(userLocation, it.first.parkingAttitude, it.first.parkingLongitude)!!,
                                            it.second.parkingState,
                                            it.second.dataVeracity
                                    )
                                }.sortedBy { it.distance })
                            } else {
                                if (it.code() != 400) {
                                    mainActivity.showToast("No internet connection")
                                }
                            }
                            mainActivity.setLoading(false)
                        },
                        {
                            mainActivity.showToast("No internet connection")
                            mainActivity.setLoading(false)
                        }
                )
        )
    }

    fun getUserPhoto() {
        mainActivity.setLoading(true)
        compositeDisposable.addAll(fbCalls.getUserPicture()
                .map {
                    var bitmap: Bitmap? = null
                    try {
                        bitmap = Glide.with(mainActivity)
                                .asBitmap()
                                .load(it.body()?.picture?.data?.url)
                                .apply(RequestOptions.circleCropTransform())
                                .submit(48, 48)
                                .get()
                    } catch (ignored: Exception) {
                    }
                    bitmap
                }
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            if (it !== null) mainActivity.updateUserPhoto(it)
                            mainActivity.setLoading(false)
                        },
                        {
                            mainActivity.setLoading(false)
                        }
                )
        )
    }
}
