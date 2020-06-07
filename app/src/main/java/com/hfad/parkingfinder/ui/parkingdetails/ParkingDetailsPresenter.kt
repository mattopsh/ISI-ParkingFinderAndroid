package com.hfad.parkingfinder.ui.parkingdetails

import android.content.Intent
import android.net.Uri
import android.os.Handler
import android.util.Log
import com.hfad.parkingfinder.apicalls.parking.ParkingCalls
import com.hfad.parkingfinder.database.room.dao.FavoriteParkingDao
import com.hfad.parkingfinder.database.room.entities.FavoriteParkingEntity
import com.hfad.parkingfinder.utils.mvp.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class ParkingDetailsPresenter(private val activity: ParkingDetailsActivity, private val parkingCalls: ParkingCalls, private val favoriteParkingDao: FavoriteParkingDao) : BasePresenter {

    override val compositeDisposable = CompositeDisposable()

    fun requestChartData(parkingId: Long) {
        activity.setLoading(true)
        compositeDisposable.addAll(
                parkingCalls.getParkingStatePrediction(parkingId)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                {
                                    if (it.isSuccessful) {
                                        activity.updateChart(it.body()!!)
                                        activity.setLoading(false)
                                    } else {
                                        Handler().postDelayed({
                                            if (!compositeDisposable.isDisposed) {
                                                requestChartData(parkingId)
                                            }
                                        }, 1000L)
                                    }
                                },
                                {
                                    Handler().postDelayed({
                                        if (!compositeDisposable.isDisposed) {
                                            requestChartData(parkingId)
                                        }
                                    }, 1000L)
                                }
                        )
        )
    }

    fun startNavigation(attitude: Double, longitude: Double) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?daddr=$attitude,$longitude"))
        activity.startActivity(intent)
    }

    fun getStarButtonState(parkingNodeId: Long) {
        activity.setStarButtonEnabled(false)
        compositeDisposable.addAll(
                favoriteParkingDao.getFavoriteParkingList()
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                {
                                    if (it.find { favoriteParking -> parkingNodeId == favoriteParking.parkingNodeId } !== null) {
                                        activity.setStartButtonChecked(true)
                                    } else {
                                        activity.setStartButtonChecked(false)
                                    }
                                    activity.setStarButtonEnabled(true)
                                },
                                {
                                    Log.e("DBError", "${it.message}")
                                }
                        )
        )
    }

    fun addToFavorites(parkingNodeId: Long, parkingName: String, parkingAddress: String, parkingAttitude: Double, parkingLongitude: Double) {
        activity.setStarButtonEnabled(false)
        doAsync {
            favoriteParkingDao.insertFavoriteParking(FavoriteParkingEntity(parkingNodeId, parkingName, parkingAddress, parkingAttitude, parkingLongitude))
            uiThread {
                activity.setStarButtonEnabled(true)
            }
        }
    }

    fun removeFromFavorites(parkingNodeId: Long, parkingName: String, parkingAddress: String, parkingAttitude: Double, parkingLongitude: Double) {
        activity.setStarButtonEnabled(false)
        doAsync {
            favoriteParkingDao.delete(FavoriteParkingEntity(parkingNodeId, parkingName, parkingAddress, parkingAttitude, parkingLongitude))
            uiThread {
                activity.setStarButtonEnabled(true)
            }
        }
    }
}
