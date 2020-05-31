package com.hfad.parkingfinder.restclient.fb

import com.hfad.parkingfinder.restclient.fb.dto.UserDetailsResponseDto
import com.hfad.parkingfinder.restclient.fb.dto.UserPictureResponseDto
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET

interface FbCalls {
    @GET("v1/user/secured/picture")
    fun getUserPicture(): Single<Response<UserPictureResponseDto>>

    @GET("v1/user/secured/userDetails")
    fun getUserDeatils(): Single<Response<UserDetailsResponseDto>>
}