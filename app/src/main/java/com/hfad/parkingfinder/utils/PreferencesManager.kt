package com.hfad.parkingfinder.utils

import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.hfad.parkingfinder.app.ParkingFinderApp

object PreferencesManager {

    private const val ACCESS_TOKEN = "ACCESS_TOKEN"
    private const val REFRESH_TOKEN = "REFRESH_TOKEN"

    private val sharedPref: SharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(ParkingFinderApp.instance)

    fun saveAccessToken(token: String) {
        sharedPref.edit()
                .putString(ACCESS_TOKEN, token)
                .apply()
    }

    fun getAccessToken(): String {
        return sharedPref.getString(ACCESS_TOKEN, "")
    }

    fun saveRefreshToken(token: String) {
        sharedPref.edit()
                .putString(REFRESH_TOKEN, token)
                .apply()
    }

    fun getRefreshToken(): String {
        return sharedPref.getString(REFRESH_TOKEN, "")
    }

}
