package com.hfad.parkingfinder.ui.splash

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.hfad.parkingfinder.R
import com.hfad.parkingfinder.ui.main.MainActivity
import com.hfad.parkingfinder.ui.welcome.WelcomeActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Fabric.with(this, Crashlytics())
        if (PreferencesManager.getRefreshToken().isNotEmpty()) {
            startActivity<MainActivity>()
        } else {
            startActivity<WelcomeActivity>()
        }
    }
}
