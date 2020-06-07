package com.hfad.parkingfinder.apicalls.fb

import com.hfad.parkingfinder.apicalls.dagger.RetrofitAuthorizationClient
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