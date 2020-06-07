package com.hfad.parkingfinder.apicalls.provider

import com.hfad.parkingfinder.apicalls.dagger.RetrofitBasicClient
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