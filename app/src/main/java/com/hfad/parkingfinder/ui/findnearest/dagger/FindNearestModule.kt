package com.hfad.parkingfinder.ui.findnearest.dagger

import com.hfad.parkingfinder.apicalls.parking.ParkingCalls
import com.hfad.parkingfinder.ui.findnearest.FindNearestActivity
import com.hfad.parkingfinder.ui.findnearest.FindNearestPresenter
import dagger.Module
import dagger.Provides

@Module
class FindNearestModule(private val activity: FindNearestActivity) {
    @FindNearestScope
    @Provides
    fun providePresenter(parkingCalls: ParkingCalls): FindNearestPresenter {
        return FindNearestPresenter(activity, parkingCalls)
    }
}
