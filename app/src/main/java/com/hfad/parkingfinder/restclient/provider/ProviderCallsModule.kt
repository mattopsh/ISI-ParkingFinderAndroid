package com.hfad.parkingfinder.restclient.provider

import com.hfad.parkingfinder.restclient.dagger.RetrofitBasicClient
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class ProviderCallsModule {
    @Singleton
    @Provides
    fun providerCalls(@RetrofitBasicClient retrofitBasicClient: Retrofit): ProviderCalls {
        return retrofitBasicClient.create(ProviderCalls::class.java)
    }
}