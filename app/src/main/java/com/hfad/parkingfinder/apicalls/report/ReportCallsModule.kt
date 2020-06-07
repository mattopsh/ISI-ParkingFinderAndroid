package com.hfad.parkingfinder.apicalls.report

import com.hfad.parkingfinder.apicalls.dagger.RetrofitAuthorizationClient
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class ReportCallsModule {
    @Singleton
    @Provides
    fun providerCalls(@RetrofitAuthorizationClient
                      retrofitAuthorizationClient: Retrofit): ReportCalls {
        return retrofitAuthorizationClient.create(ReportCalls::class.java)
    }
}