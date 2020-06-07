package com.hfad.parkingfinder.ui.findneartoprovider.dagger

import com.hfad.parkingfinder.apicalls.parking.ParkingCalls
import com.hfad.parkingfinder.apicalls.provider.ProviderCalls
import com.hfad.parkingfinder.ui.findneartoprovider.FindNearToProviderActivity
import com.hfad.parkingfinder.ui.findneartoprovider.FindNearToProviderPresenter
import dagger.Module
import dagger.Provides

@Module
class FindNearToProviderModule(private val activity: FindNearToProviderActivity) {
    @FindNearToProviderScope
    @Provides
    fun providePresenter(parkingCalls: ParkingCalls): FindNearToProviderPresenter {
        return FindNearToProviderPresenter(activity, parkingCalls)
    }
}
