package com.hfad.parkingfinder.ui.createaccount.dagger

import com.hfad.parkingfinder.ui.createaccount.CreateAccountActivity
import dagger.Subcomponent

@CreateAccountScope
@Subcomponent(modules = [CreateAccountModule::class])
interface CreateAccountComponent {
    fun inject(createAccountActivity: CreateAccountActivity)
}