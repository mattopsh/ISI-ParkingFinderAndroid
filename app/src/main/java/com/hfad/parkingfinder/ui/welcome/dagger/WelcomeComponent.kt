package com.hfad.parkingfinder.ui.welcome.dagger

import com.hfad.parkingfinder.ui.welcome.WelcomeActivity
import dagger.Subcomponent

@WelcomeScope
@Subcomponent(modules = [WelcomeModule::class])
interface WelcomeComponent {
    fun inject(welcomeActivity: WelcomeActivity)
}