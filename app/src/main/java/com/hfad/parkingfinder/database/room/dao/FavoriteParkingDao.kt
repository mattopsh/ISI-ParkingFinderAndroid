package com.hfad.parkingfinder.database.room.dao

import android.arch.persistence.room.*
import com.hfad.parkingfinder.database.room.entities.FavoriteParkingEntity
import io.reactivex.Flowable
import io.reactivex.Observable


@Dao
interface FavoriteParkingDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavoriteParking(favoriteParkingEntity: FavoriteParkingEntity)

    @Query("SELECT * FROM FavoriteParkingEntity")
    fun getFavoriteParkingList(): Flowable<List<FavoriteParkingEntity>>

    @Delete
    fun delete(favoriteParkingEntity: FavoriteParkingEntity)
}
