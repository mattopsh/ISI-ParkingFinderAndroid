package com.hfad.parkingfinder.restclient.parking.dto

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ParkingNearToProviderDto(val parkingNodeId: Long, val attitude: Double, val longitude: Double, val parkingName: String, val parkingAddress: String, val distanceToParking: Int, val distanceToProvider: Int, val freeSpaces: Int, val dataVeracity: Int) : Parcelable