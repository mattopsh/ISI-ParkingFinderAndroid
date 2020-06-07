package com.hfad.parkingfinder.apicalls.auth

import com.hfad.parkingfinder.apicalls.dagger.RetrofitBasicClient
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class AuthCallsModule {
    @Singleton
    @Provides
    fun authCalls(@RetrofitBasicClient retrofitBasicClient: Retrofit): AuthCalls {
        return retrofitBasicClient.create(AuthCalls::class.java)
    }
}