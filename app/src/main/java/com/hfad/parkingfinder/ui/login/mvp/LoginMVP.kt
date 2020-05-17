package com.hfad.parkingfinder.ui.login.mvp

import com.hfad.parkingfinder.restclient.auth.dto.TokensResponseDto
import com.hfad.parkingfinder.restclient.auth.dto.UserCredentialsDto
import com.hfad.parkingfinder.utils.mvp.BasePresenter
import com.hfad.parkingfinder.utils.mvp.ViewException
import com.hfad.parkingfinder.utils.mvp.ViewLoad
import io.reactivex.Single
import retrofit2.Response

interface LoginMVP {
    interface View : ViewLoad, ViewException {
        fun startMainActivity()
        fun invalidEmailOrPassword()
    }

    interface Presenter : BasePresenter {
        fun loginEmail(email: String, password: String)
    }

    interface LoginCallsModel {
        fun loginEmail(userCredentialsDto: UserCredentialsDto): Single<Response<TokensResponseDto>>
    }
}