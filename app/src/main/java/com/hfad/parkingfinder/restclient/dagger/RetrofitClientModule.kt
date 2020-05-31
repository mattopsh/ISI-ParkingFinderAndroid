package com.hfad.parkingfinder.restclient.dagger

import com.hfad.parkingfinder.restclient.RetrofitClient
import com.hfad.parkingfinder.restclient.dagger.RetrofitAuthorizationClient
import com.hfad.parkingfinder.restclient.dagger.RetrofitBasicClient
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RetrofitClientModule {

    @Singleton
    @RetrofitBasicClient
    @Provides
    fun provideRetrofitClient() = RetrofitClient.getClient()

    @Singleton
    @RetrofitAuthorizationClient
    @Provides
    fun provideRetrofitAuthorizationClient() = RetrofitClient.getAuthorizationClient()

}