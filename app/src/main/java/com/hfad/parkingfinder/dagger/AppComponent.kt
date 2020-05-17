package com.hfad.parkingfinder.dagger

import com.hfad.parkingfinder.ui.login.dagger.LoginComponent
import com.hfad.parkingfinder.ui.login.dagger.LoginModule
import com.hfad.parkingfinder.ui.welcome.dagger.WelcomeComponent
import com.hfad.parkingfinder.ui.welcome.dagger.WelcomeModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [])
interface AppComponent {
    fun plus(welcomeModule: WelcomeModule): WelcomeComponent
    fun plus(loginModule: LoginModule): LoginComponent
}