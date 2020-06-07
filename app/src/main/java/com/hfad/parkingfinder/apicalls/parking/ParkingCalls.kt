package com.hfad.parkingfinder.apicalls.parking

import com.hfad.parkingfinder.apicalls.parking.dto.*
import com.hfad.parkingfinder.apicalls.report.dto.Cost
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ParkingCalls {
    @GET("v1/parking/public/nearToLocation")
    fun findNearestParking(@Query("pageNumber") pageNumber: Int,
                           @Query("pageSize") pageSize: Int,
                           @Query("freeSpaces") freeSpaces: FreeSpaces?,
                           @Query("dataVeracity") dataVeracity: DataVeracity?,
                           @Query("cost") cost: Cost?,
                           @Query("maxDistance") maxDistance: Int?,
                           @Query("parkingNameOrAddress") parkingNameOrAddress: String?,
                           @Query("attitude") attitude: Double,
                           @Query("longitude") longitude: Double): Single<Response<List<ParkingByLocationDto>>>

    @GET("v1/parking/public/nearToProvider")
    fun findNearToProvider(@Query("pageNumber") pageNumber: Int,
                           @Query("pageSize") pageSize: Int,
                           @Query("freeSpaces") freeSpaces: FreeSpaces?,
                           @Query("dataVeracity") dataVeracity: DataVeracity?,
                           @Query("cost") cost: Cost?,
                           @Query("maxDistance") maxDistance: Int?,
                           @Query("userAttitude") userAttitude: Double,
                           @Query("userLongitude") userLongitude: Double,
                           @Query("providerAttitude") providerAttitude: Double,
                           @Query("providerLongitude") providerLongitude: Double): Single<Response<List<ParkingNearToProviderDto>>>

    @GET("v1/parking/public/favorite")
    fun getFavoriteParkingState(@Query("parkingNodesIds") parkingNodesIds: List<Long>): Single<Response<List<FavoriteParkingStateDto>>>

    @GET("v1/parking/public/history")
    fun getParkingStatePrediction(@Query("parkingNodeId") parkingNodeId: Long): Single<Response<List<ParkingHistoryPointDto>>>
}