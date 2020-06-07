package com.hfad.parkingfinder.apicalls.dagger

import com.hfad.parkingfinder.apicalls.RetrofitClient
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