package com.hfad.parkingfinder.restclient.auth

import com.hfad.parkingfinder.restclient.dagger.RetrofitBasicClient
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