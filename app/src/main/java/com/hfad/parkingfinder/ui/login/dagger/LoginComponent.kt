package com.hfad.parkingfinder.ui.login.dagger

import com.hfad.parkingfinder.ui.login.LoginActivity
import dagger.Subcomponent

@LoginScope
@Subcomponent(modules = [LoginModule::class])
interface LoginComponent {
    fun inject(loginActivity: LoginActivity)
}