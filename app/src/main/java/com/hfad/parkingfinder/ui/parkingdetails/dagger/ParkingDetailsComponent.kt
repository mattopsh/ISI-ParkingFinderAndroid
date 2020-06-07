package com.hfad.parkingfinder.ui.parkingdetails.dagger

import com.hfad.parkingfinder.ui.parkingdetails.ParkingDetailsActivity
import dagger.Subcomponent

@ParkingDetailsScope
@Subcomponent(modules = [ParkingDetailsModule::class])
interface ParkingDetailsComponent {
    fun inject(parkingDetailsActivity: ParkingDetailsActivity)
}
