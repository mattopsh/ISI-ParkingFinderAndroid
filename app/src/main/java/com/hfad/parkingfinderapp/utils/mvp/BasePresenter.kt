package com.hfad.parkingfinderapp.utils.mvp

import io.reactivex.disposables.CompositeDisposable

interface BasePresenter {
    val compositeDisposable: CompositeDisposable

    fun disposeRX(){
        compositeDisposable.dispose()
    }
}