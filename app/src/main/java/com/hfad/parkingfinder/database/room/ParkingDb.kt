package com.hfad.parkingfinder.database.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.hfad.parkingfinder.database.room.dao.FavoriteParkingDao
import com.hfad.parkingfinder.database.room.entities.FavoriteParkingEntity

@Database(entities = [FavoriteParkingEntity::class], version = 1)
abstract class ParkingDb : RoomDatabase() {

    abstract fun favoriteParkingDao(): FavoriteParkingDao

    companion object {

        @Volatile
        private var INSTANCE: ParkingDb? = null

        fun getInstance(context: Context): ParkingDb =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
                }

        private fun buildDatabase(context: Context) =
                Room.databaseBuilder(context.applicationContext,
                        ParkingDb::class.java, "ParkingDb.db")
                        .build()

    }
}