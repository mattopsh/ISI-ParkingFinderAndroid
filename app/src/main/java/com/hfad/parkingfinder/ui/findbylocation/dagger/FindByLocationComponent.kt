package com.hfad.parkingfinder.ui.findbylocation.dagger

import com.hfad.parkingfinder.ui.findbylocation.FindByLocationActivity
import dagger.Subcomponent

@FindByLocationScope
@Subcomponent(modules = [FindByLocationModule::class])
interface FindByLocationComponent {
    fun inject(findByLocationActivity: FindByLocationActivity)
}
