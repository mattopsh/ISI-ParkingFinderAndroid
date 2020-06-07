package com.hfad.parkingfinder.ui.createaccount.mvp

import com.hfad.parkingfinder.apicalls.auth.AuthCalls
import com.hfad.parkingfinder.apicalls.auth.dto.TokensResponseDto
import com.hfad.parkingfinder.apicalls.auth.dto.UserCredentialsDto
import io.reactivex.Single
import retrofit2.Response
import retrofit2.Retrofit

class CreateAccountCalls(retrofit: Retrofit) : CreateAccountMVP.CreateAccountCallsModel {

    private val authCalls = retrofit.create(AuthCalls::class.java)

    override fun createAccount(userCredentialsDto: UserCredentialsDto): Single<Response<TokensResponseDto>> {
        return authCalls.createAccount(userCredentialsDto)
    }
}