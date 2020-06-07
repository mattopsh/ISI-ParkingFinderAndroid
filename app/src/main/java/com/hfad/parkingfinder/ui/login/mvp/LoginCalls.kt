package com.hfad.parkingfinder.ui.login.mvp


import com.hfad.parkingfinder.apicalls.auth.AuthCalls
import com.hfad.parkingfinder.apicalls.auth.dto.TokensResponseDto
import com.hfad.parkingfinder.apicalls.auth.dto.UserCredentialsDto
import io.reactivex.Single
import retrofit2.Response
import retrofit2.Retrofit

class LoginCalls(retrofit: Retrofit) : LoginMVP.LoginCallsModel {
    private val authCalls = retrofit.create(AuthCalls::class.java)

    override fun loginEmail(userCredentialsDto: UserCredentialsDto): Single<Response<TokensResponseDto>> {
        return authCalls.logInEmail(userCredentialsDto)
    }
}
