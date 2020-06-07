package com.hfad.parkingfinder.ui.findnearest.dagger

import com.hfad.parkingfinder.ui.findnearest.FindNearestActivity
import dagger.Subcomponent

@FindNearestScope
@Subcomponent(modules = [FindNearestModule::class])
interface FindNearestComponent {
    fun inject(findNearestActivity: FindNearestActivity)
}
