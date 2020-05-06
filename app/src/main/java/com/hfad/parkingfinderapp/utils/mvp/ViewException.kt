package com.hfad.parkingfinderapp.utils.mvp

interface ViewException {
    fun handleHttpException(code: Int): Boolean
    fun handleException(exception: Throwable)
}