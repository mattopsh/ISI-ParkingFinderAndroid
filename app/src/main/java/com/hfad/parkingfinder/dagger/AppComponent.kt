package com.hfad.parkingfinder.dagger

import com.hfad.parkingfinder.apicalls.auth.AuthCallsModule
import com.hfad.parkingfinder.apicalls.dagger.RetrofitClientModule
import com.hfad.parkingfinder.apicalls.fb.FbCallsModule
import com.hfad.parkingfinder.apicalls.parking.ParkingCallsModule
import com.hfad.parkingfinder.apicalls.provider.ProviderCallsModule
import com.hfad.parkingfinder.apicalls.report.ReportCallsModule
import com.hfad.parkingfinder.database.room.dagger.ParkingDbModule
import com.hfad.parkingfinder.database.sharedpreferences.dagger.PreferencesManagerModule
import com.hfad.parkingfinder.ui.createaccount.dagger.CreateAccountComponent
import com.hfad.parkingfinder.ui.createaccount.dagger.CreateAccountModule
import com.hfad.parkingfinder.ui.findbylocation.dagger.FindByLocationComponent
import com.hfad.parkingfinder.ui.findbylocation.dagger.FindByLocationModule
import com.hfad.parkingfinder.ui.findnearest.dagger.FindNearestComponent
import com.hfad.parkingfinder.ui.findnearest.dagger.FindNearestModule
import com.hfad.parkingfinder.ui.findneartoprovider.dagger.FindNearToProviderComponent
import com.hfad.parkingfinder.ui.findneartoprovider.dagger.FindNearToProviderModule
import com.hfad.parkingfinder.ui.findprovider.dagger.FindProviderComponent
import com.hfad.parkingfinder.ui.findprovider.dagger.FindProviderModule
import com.hfad.parkingfinder.ui.login.dagger.LoginComponent
import com.hfad.parkingfinder.ui.login.dagger.LoginModule
import com.hfad.parkingfinder.ui.main.dagger.MainComponent
import com.hfad.parkingfinder.ui.main.dagger.MainModule
import com.hfad.parkingfinder.ui.parkingdetails.dagger.ParkingDetailsComponent
import com.hfad.parkingfinder.ui.parkingdetails.dagger.ParkingDetailsModule
import com.hfad.parkingfinder.ui.reportinconsistency.newparking.additionaldetails.dagger.ReportNewParkingDetailsComponent
import com.hfad.parkingfinder.ui.reportinconsistency.newparking.additionaldetails.dagger.ReportNewParkingDetailsModule
import com.hfad.parkingfinder.ui.reportinconsistency.nonexistentparking.dagger.ReportNonexistentParkingComponent
import com.hfad.parkingfinder.ui.reportinconsistency.nonexistentparking.dagger.ReportNonexistentParkingModule
import com.hfad.parkingfinder.ui.reportinconsistency.other.dagger.ReportOtherInconsistencyComponent
import com.hfad.parkingfinder.ui.reportinconsistency.other.dagger.ReportOtherInconsistencyModule
import com.hfad.parkingfinder.ui.reportparkingstate.dagger.ReportParkingStateComponent
import com.hfad.parkingfinder.ui.reportparkingstate.dagger.ReportParkingStateModule
import com.hfad.parkingfinder.ui.settings.dagger.SettingsComponent
import com.hfad.parkingfinder.ui.settings.dagger.SettingsModule
import com.hfad.parkingfinder.ui.welcome.dagger.WelcomeComponent
import com.hfad.parkingfinder.ui.welcome.dagger.WelcomeModule
import com.hfad.parkingfinder.utils.validator.dagger.ValidatorModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [PreferencesManagerModule::class, RetrofitClientModule::class, ValidatorModule::class, ParkingDbModule::class, AuthCallsModule::class, FbCallsModule::class, ParkingCallsModule::class, ProviderCallsModule::class, ReportCallsModule::class])
interface AppComponent {
    fun plus(welcomeModule: WelcomeModule): WelcomeComponent
    fun plus(createAccountModule: CreateAccountModule): CreateAccountComponent
    fun plus(loginModule: LoginModule): LoginComponent
    fun plus(mainModule: MainModule): MainComponent
    fun plus(findByLocationModule: FindByLocationModule): FindByLocationComponent
    fun plus(findNearestModule: FindNearestModule): FindNearestComponent
    fun plus(findNearToProviderModule: FindNearToProviderModule): FindNearToProviderComponent
    fun plus(reportParkingStateModule: ReportParkingStateModule): ReportParkingStateComponent
    fun plus(settingsModule: SettingsModule): SettingsComponent
    fun plus(parkingDetailsModule: ParkingDetailsModule): ParkingDetailsComponent
    fun plus(reportNewParkingDetailsModule: ReportNewParkingDetailsModule): ReportNewParkingDetailsComponent
    fun plus(reportNonexistentParkingModule: ReportNonexistentParkingModule): ReportNonexistentParkingComponent
    fun plus(reportOtherInconsistencyModule: ReportOtherInconsistencyModule): ReportOtherInconsistencyComponent
    fun plus(findProviderModule: FindProviderModule): FindProviderComponent
}