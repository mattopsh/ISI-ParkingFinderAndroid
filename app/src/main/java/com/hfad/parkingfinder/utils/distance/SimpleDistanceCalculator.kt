package com.hfad.parkingfinder.utils.distance

object SimpleDistanceCalculator {
    fun distance(attitude1: Double, longitude1: Double, attitude2: Double, longitude2: Double): Double {
        val R = 6371000
        val attitudeDistance = Math.toRadians(attitude2 - attitude1)
        val longitudeDistance = Math.toRadians(longitude2 - longitude1)
        val a = Math.sin(attitudeDistance / 2) * Math.sin(attitudeDistance / 2) + (Math.cos(Math.toRadians(attitude1)) * Math.cos(Math.toRadians(attitude2))
                * Math.sin(longitudeDistance / 2) * Math.sin(longitudeDistance / 2))
        val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
        return R.toDouble() * c;
    }
}
