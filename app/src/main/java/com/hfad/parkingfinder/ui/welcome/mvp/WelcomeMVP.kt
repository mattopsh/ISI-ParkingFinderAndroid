package com.hfad.parkingfinder.ui.welcome.mvp

import com.hfad.parkingfinder.apicalls.auth.dto.TokensResponseDto
import com.hfad.parkingfinder.utils.mvp.BasePresenter
import com.hfad.parkingfinder.utils.mvp.ViewException
import com.hfad.parkingfinder.utils.mvp.ViewLoad
import io.reactivex.Single
import retrofit2.Response

interface WelcomeMVP {
    interface View : ViewLoad, ViewException {
        fun tryAgainDialog(fbToken: String)
        fun startMainActivity()
    }

    interface Presenter : BasePresenter {
        fun sendFbAuthorizationData(fbToken: String)

    }

    interface FbCallsModel {
        fun sendFbAuthorizationData(fbToken: String): Single<Response<TokensResponseDto>>
    }
}