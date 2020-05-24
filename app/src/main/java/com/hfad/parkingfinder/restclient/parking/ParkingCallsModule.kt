package com.hfad.parkingfinder.restclient.parking

import com.hfad.parkingfinder.apicalls.dagger.RetrofitBasicClient
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class ParkingCallsModule {
    @Singleton
    @Provides
    fun parkingCalls(@RetrofitBasicClient retrofitBasicClient: Retrofit): ParkingCalls {
        return retrofitBasicClient.create(ParkingCalls::class.java)
    }
}