package com.hfad.parkingfinder.restclient.report

import com.hfad.parkingfinder.restclient.report.dto.NewParkingReportDto
import com.hfad.parkingfinder.restclient.report.dto.OtherInconsistencyDto
import com.hfad.parkingfinder.restclient.report.dto.ParkingStateDto
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface ReportCalls {
    @POST("v1/inconsistency/secured/new")
    fun reportNewParking(@Body newParkingReportDto: NewParkingReportDto): Single<Response<Void>>

    @POST("v1/inconsistency/secured/nonexistent/{parkingNodeId}")
    fun reportNonexistentParking(@Path("parkingNodeId") parkingNodeId: Long): Single<Response<Void>>

    @POST("v1/inconsistency/secured/other")
    fun reportOtherInconsistency(@Body otherInconsistencyDto: OtherInconsistencyDto): Single<Response<Void>>

    @POST("v1/report/secured/state")
    fun reportState(@Body parkingStateDto: ParkingStateDto): Single<Response<Void>>
}