package com.hfad.parkingfinder.ui.createaccount.mvp

import com.hfad.parkingfinder.apicalls.auth.dto.TokensResponseDto
import com.hfad.parkingfinder.apicalls.auth.dto.UserCredentialsDto
import com.hfad.parkingfinder.utils.mvp.BasePresenter
import com.hfad.parkingfinder.utils.mvp.ViewException
import com.hfad.parkingfinder.utils.mvp.ViewLoad
import io.reactivex.Single
import retrofit2.Response

interface CreateAccountMVP {
    interface View : ViewLoad, ViewException{
        fun startMainActivity()
        fun invalidEmail()
        fun invalidPassword()
        fun emailAlreadyExists()
    }

    interface Presenter : BasePresenter {
        fun createAccount(email: String, password: String)
    }

    interface CreateAccountCallsModel {
        fun createAccount(userCredentialsDto: UserCredentialsDto): Single<Response<TokensResponseDto>>
    }
}