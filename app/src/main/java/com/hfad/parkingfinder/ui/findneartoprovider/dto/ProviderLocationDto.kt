package com.hfad.parkingfinder.ui.findneartoprovider.dto

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProviderLocationDto(val providerName: String, val attitude: Double, val longitude: Double) : Parcelable