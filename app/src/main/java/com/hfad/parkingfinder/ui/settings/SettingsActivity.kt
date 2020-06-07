package com.hfad.parkingfinder.ui.settings

import android.os.Bundle
import com.hfad.parkingfinder.R
import com.hfad.parkingfinder.ui.base.BaseActivity
import com.hfad.parkingfinder.ui.settings.dagger.SettingsInjector
import com.hfad.parkingfinder.ui.settings.mvp.SettingsPresenter
import javax.inject.Inject

class SettingsActivity : BaseActivity() {

    @Inject
    lateinit var presenter: SettingsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        SettingsInjector().inject(this)
    }

    override fun onDestroy() {
        presenter.disposeRX()
        super.onDestroy()
    }
}
