package com.hfad.parkingfinder.ui.welcome.dagger

import com.hfad.parkingfinder.restclient.dagger.RetrofitBasicClient
import com.hfad.parkingfinder.database.sharedpreferences.PreferencesManager
import com.hfad.parkingfinder.ui.welcome.WelcomeActivity
import com.hfad.parkingfinder.ui.welcome.mvp.FbCalls
import com.hfad.parkingfinder.ui.welcome.mvp.WelcomeMVP
import com.hfad.parkingfinder.ui.welcome.mvp.WelcomePresenter
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class WelcomeModule(private val activity: WelcomeActivity) {
    @WelcomeScope
    @Provides
    fun providePresenter(fbCalls: WelcomeMVP.FbCallsModel, preferencesManager: PreferencesManager): WelcomeMVP.Presenter {
        return WelcomePresenter(activity, fbCalls, preferencesManager)
    }

    @WelcomeScope
    @Provides
    fun fbCalls(@RetrofitBasicClient retrofit: Retrofit): WelcomeMVP.FbCallsModel {
        return FbCalls(retrofit)
    }
}