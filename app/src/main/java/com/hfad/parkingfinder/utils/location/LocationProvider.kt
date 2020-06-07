package com.hfad.parkingfinder.utils.location

import android.content.Context
import android.location.Location
import android.location.LocationManager
import com.hfad.parkingfinder.ui.base.BaseActivity
import com.hfad.parkingfinder.utils.distance.SimpleDistanceCalculator

object LocationProvider {
    fun getLocation(activity: Context): Location? {
        val locationManager = activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        var location: Location? = null
        return try {
            for (provider in locationManager.allProviders) {
                val loc = locationManager.getLastKnownLocation(provider)
                if (location === null || loc !== null && loc.accuracy < location.accuracy) {
                    location = loc
                }
            }
            location
        } catch (e: SecurityException) {
            location
        }
    }

    fun getDistance(userLocation: Location?, parkingAttitude: Double?, parkingLongitude: Double?): Int? {
        return if (userLocation !== null && parkingAttitude !== null && parkingLongitude !== null) {
            SimpleDistanceCalculator.distance(userLocation.latitude, userLocation.longitude, parkingAttitude, parkingLongitude).toInt()
        } else {
            null
        }
    }
}