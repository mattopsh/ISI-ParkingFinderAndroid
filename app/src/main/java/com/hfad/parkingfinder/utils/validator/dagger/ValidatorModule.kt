package com.hfad.parkingfinder.utils.validator.dagger

import com.hfad.parkingfinder.utils.validator.Validator
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ValidatorModule {

    @Singleton
    @Provides
    fun provideVaildator() = Validator

}