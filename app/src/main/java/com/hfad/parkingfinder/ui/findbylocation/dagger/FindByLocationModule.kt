package com.hfad.parkingfinder.ui.findbylocation.dagger

import com.hfad.parkingfinder.apicalls.parking.ParkingCalls
import com.hfad.parkingfinder.ui.findbylocation.FindByLocationActivity
import com.hfad.parkingfinder.ui.findbylocation.FindByLocationPresenter
import dagger.Module
import dagger.Provides

@Module
class FindByLocationModule(private val activity: FindByLocationActivity) {
    @FindByLocationScope
    @Provides
    fun providePresenter(parkingCalls: ParkingCalls): FindByLocationPresenter {
        return FindByLocationPresenter(activity, parkingCalls)
    }
}
