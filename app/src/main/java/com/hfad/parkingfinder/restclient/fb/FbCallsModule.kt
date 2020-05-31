package com.hfad.parkingfinder.restclient.fb

import com.hfad.parkingfinder.restclient.dagger.RetrofitAuthorizationClient
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class FbCallsModule {
    @Singleton
    @Provides
    fun fbCalls(@RetrofitAuthorizationClient retrofitAuthorizationClient: Retrofit): FbCalls {
        return retrofitAuthorizationClient.create(FbCalls::class.java)
    }
}