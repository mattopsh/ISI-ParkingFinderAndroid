package com.hfad.parkingfinder.dagger

interface IDaggerInjector<in T> {
    fun inject(target: T)
}