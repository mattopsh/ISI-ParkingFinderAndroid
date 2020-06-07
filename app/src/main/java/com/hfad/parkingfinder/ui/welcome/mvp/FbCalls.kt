package com.hfad.parkingfinder.ui.welcome.mvp

import com.hfad.parkingfinder.apicalls.auth.AuthCalls
import com.hfad.parkingfinder.apicalls.auth.dto.FbTokenDto
import com.hfad.parkingfinder.apicalls.auth.dto.TokensResponseDto
import io.reactivex.Single
import retrofit2.Response
import retrofit2.Retrofit

class FbCalls(private val retrofit: Retrofit) : WelcomeMVP.FbCallsModel {
    override fun sendFbAuthorizationData(fbToken: String): Single<Response<TokensResponseDto>> {
        return retrofit.create(AuthCalls::class.java)
                .logInFB(FbTokenDto(fbToken))
    }
}