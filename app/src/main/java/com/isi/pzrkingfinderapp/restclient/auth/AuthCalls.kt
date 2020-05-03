package com.isi.pzrkingfinderapp.restclient.auth

import com.isi.pzrkingfinderapp.restclient.auth.dto.AccessTokenResponseDto
import com.isi.pzrkingfinderapp.restclient.auth.dto.FbTokenDto
import com.isi.pzrkingfinderapp.restclient.auth.dto.TokensResponseDto
import com.isi.pzrkingfinderapp.restclient.auth.dto.UserCredentialsDto
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.*

interface AuthCalls {
    @POST("v1/auth/public/login/email")
    fun logInEmail(@Body userCredentialsDto: UserCredentialsDto): Single<Response<TokensResponseDto>>

    @POST("v1/auth/public/login/fb")
    fun logInFB(@Body fbTokenDto: FbTokenDto): Single<Response<TokensResponseDto>>

    @POST("v1/auth/public/registration")
    fun createAccount(@Body userCredentialsDto: UserCredentialsDto): Single<Response<TokensResponseDto>>

    @GET("v1/auth/public/accessToken")
    fun getNewAccessToken(@Header("refreshToken") refreshToken: String): Single<Response<AccessTokenResponseDto>>
}