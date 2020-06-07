package com.hfad.parkingfinder.ui.parkingdetails.dagger

import com.hfad.parkingfinder.apicalls.parking.ParkingCalls
import com.hfad.parkingfinder.database.room.ParkingDb
import com.hfad.parkingfinder.database.room.dao.FavoriteParkingDao
import com.hfad.parkingfinder.ui.parkingdetails.ParkingDetailsActivity
import com.hfad.parkingfinder.ui.parkingdetails.ParkingDetailsPresenter
import dagger.Module
import dagger.Provides

@Module
class ParkingDetailsModule(private val activity: ParkingDetailsActivity) {
    @ParkingDetailsScope
    @Provides
    fun providePresenter(parkingCalls: ParkingCalls): ParkingDetailsPresenter {
        val favoriteParkingDao = ParkingDb.getInstance(activity).favoriteParkingDao()
        return ParkingDetailsPresenter(activity, parkingCalls, favoriteParkingDao)
    }

}
