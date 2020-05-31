package com.hfad.parkingfinder.restclient.provider

import com.hfad.parkingfinder.restclient.provider.dto.ProviderDto
import com.hfad.parkingfinder.restclient.provider.dto.ProviderType
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

@JvmSuppressWildcards
interface ProviderCalls {
    @GET("v1/provider/public/provider")
    fun findProviders(@Query("pageNumber") pageNumber: Int,
                      @Query("pageSize") pageSize: Int,
                      @Query("maxDistance") maxDistance: Int?,
                      @Query("providerNameOrAddress") providerNameOrAddress: String?,
                      @Query("providerTypes") providerTypes: List<ProviderType>,
                      @Query("attitude") attitude: Double,
                      @Query("longitude") longitude: Double): Single<Response<List<ProviderDto>>>
}