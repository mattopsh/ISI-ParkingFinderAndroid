package com.hfad.parkingfinder.ui.findnearest

import com.hfad.parkingfinder.apicalls.parking.dto.ParkingByLocationDto
import com.hfad.parkingfinder.apicalls.parking.dto.ParkingNearToProviderDto

object AutoSelectParking {
    fun selectAutomatically(parkingData: ArrayList<ParkingByLocationDto>): ParkingByLocationDto? {
        val bestParking = parkingData
                .filter { it.freeSpaces > 0 }
                .sortedBy { (it.distance * (1 - it.dataVeracity)) }
        return if (bestParking.isNotEmpty()) {
            bestParking.last()
        } else {
            if (parkingData.isEmpty()) {
                null
            } else {
                parkingData.first()
            }
        }
    }

    fun selectAutomaticallyNearToProvider(parkingData: ArrayList<ParkingNearToProviderDto>): ParkingNearToProviderDto? {
        val bestParking = parkingData
                .filter { it.freeSpaces > 0 }
                .sortedBy { (it.distanceToProvider * (1 - it.dataVeracity)) }
        return if (bestParking.isNotEmpty()) {
            bestParking.last()
        } else {
            if (parkingData.isEmpty()) {
                null
            } else {
                parkingData.first()
            }
        }
    }
}