package com.hfad.parkingfinder.apicalls.report.dto

data class NewParkingReportDto(val attitude: Double, val longitude: Double, val capacity: Int?, val stayCost: Cost?, val otherInformation: String?)

enum class Cost {
    PAID,
    LIMITED,
    FREE
}