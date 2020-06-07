package com.hfad.parkingfinder.apicalls.provider.dto

data class ProviderDto(val providerNodeId: Long,
                       val providerName: String,
                       val providerDetails: String,
                       val attitude: Double,
                       val longitude: Double,
                       val distance: Int,
                       val openingHours: String?,
                       val providerType: ProviderType?,
                       val exactProviderType: String?,
                       val url: String?)

enum class ProviderType {
    FOOD,
    DIY,
    CLOTHES,
    BEAUTY,
    INTERIOR,
    ELECTRONICS,
    SPORT,
    CAR,
    ENTMT
}