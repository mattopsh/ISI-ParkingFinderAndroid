package com.hfad.parkingfinder.database.room.dagger

import android.content.Context
import com.hfad.parkingfinder.database.room.ParkingDb
import com.hfad.parkingfinder.database.room.dao.FavoriteParkingDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ParkingDbModule(context: Context) {
    private val database = ParkingDb.getInstance(context)

    @Singleton
    @Provides
    fun favoriteParkingDao(): FavoriteParkingDao {
        return database.favoriteParkingDao()
    }
}
