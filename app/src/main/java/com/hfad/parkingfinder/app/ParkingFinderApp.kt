package com.hfad.parkingfinder.app

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.facebook.appevents.AppEventsLogger

class ParkingFinderApp : Application() {
    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()
        AppEventsLogger.activateApp(this)
    }

    companion object {
        @SuppressLint("StaticFieldLeak")    //It is not a memory leak. ApplicationContext is never released.
        lateinit var instance: Context
    }
}