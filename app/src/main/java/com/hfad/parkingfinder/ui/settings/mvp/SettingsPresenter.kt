package com.hfad.parkingfinder.ui.settings.mvp

import com.hfad.parkingfinder.ui.settings.SettingsActivity
import com.hfad.parkingfinder.utils.mvp.BasePresenter
import io.reactivex.disposables.CompositeDisposable

class SettingsPresenter(private val activity: SettingsActivity) : BasePresenter {

    override val compositeDisposable = CompositeDisposable()
}
