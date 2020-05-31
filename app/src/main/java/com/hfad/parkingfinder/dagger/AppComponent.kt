package com.hfad.parkingfinder.dagger

import com.hfad.parkingfinder.restclient.auth.AuthCallsModule
import com.hfad.parkingfinder.restclient.fb.FbCallsModule
import com.hfad.parkingfinder.restclient.parking.ParkingCallsModule
import com.hfad.parkingfinder.restclient.provider.ProviderCallsModule
import com.hfad.parkingfinder.restclient.report.ReportCallsModule
import com.hfad.parkingfinder.ui.createaccount.dagger.CreateAccountComponent
import com.hfad.parkingfinder.ui.createaccount.dagger.CreateAccountModule
import com.hfad.parkingfinder.ui.login.dagger.LoginComponent
import com.hfad.parkingfinder.ui.login.dagger.LoginModule
import com.hfad.parkingfinder.ui.main.dagger.MainComponent
import com.hfad.parkingfinder.ui.main.dagger.MainModule
import com.hfad.parkingfinder.ui.welcome.dagger.WelcomeComponent
import com.hfad.parkingfinder.ui.welcome.dagger.WelcomeModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [WelcomeModule::class, LoginModule::class, MainModule::class, AuthCallsModule::class, FbCallsModule::class, ParkingCallsModule::class, ProviderCallsModule::class, ReportCallsModule::class])
interface AppComponent {
    fun plus(welcomeModule: WelcomeModule): WelcomeComponent
    fun plus(loginModule: LoginModule): LoginComponent
    fun plus(mainModule: MainModule): MainComponent
    fun plus(createAccountModule: CreateAccountModule): CreateAccountComponent
}