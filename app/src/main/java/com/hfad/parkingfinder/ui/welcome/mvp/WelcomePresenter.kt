package com.hfad.parkingfinder.ui.welcome.mvp

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class WelcomePresenter(private val view: WelcomeMVP.View, private val fbCallsModel: WelcomeMVP.FbCallsModel, private val preferencesManager: PreferencesManager) : WelcomeMVP.Presenter {

    override val compositeDisposable = CompositeDisposable()

    override fun sendFbAuthorizationData(fbToken: String) {
        view.setLoading(true)
        compositeDisposable.add(fbCallsModel.sendFbAuthorizationData(fbToken)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            view.setLoading(false)
                            if (it.isSuccessful) {
                                preferencesManager.saveAccessToken(it.body()!!.accessToken)
                                preferencesManager.saveRefreshToken(it.body()!!.refreshToken)
                                view.startMainActivity()
                            } else {
                                view.tryAgainDialog(fbToken)
                            }
                        },
                        {
                            view.setLoading(false)
                            view.tryAgainDialog(fbToken)
                        }

                )
        )
    }

}