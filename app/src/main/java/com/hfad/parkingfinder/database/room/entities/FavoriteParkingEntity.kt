package com.hfad.parkingfinder.database.room.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class FavoriteParkingEntity(
        @PrimaryKey(autoGenerate = false)
        val parkingNodeId: Long,
        val parkingName: String,
        val parkingAddress: String,
        val parkingAttitude: Double,
        val parkingLongitude: Double
)