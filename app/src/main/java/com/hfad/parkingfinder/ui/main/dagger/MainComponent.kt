package com.hfad.parkingfinder.ui.main.dagger

import com.hfad.parkingfinder.ui.main.MainActivity
import dagger.Subcomponent

@MainScope
@Subcomponent(modules = [MainModule::class])
interface MainComponent {
    fun inject(mainActivity: MainActivity)
}
