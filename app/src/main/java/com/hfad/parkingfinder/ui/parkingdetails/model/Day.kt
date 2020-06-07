package com.hfad.parkingfinder.ui.parkingdetails.model

import java.security.InvalidParameterException

enum class Day(val dayOfWeek: Int) {
    MONDAY(0),
    TUESDAY(1),
    WEDNESDAY(2),
    THURSDAY(3),
    FRIDAY(4),
    SATURDAY(5),
    SUNDAY(6);

    companion object {
        fun fromDayOfWeekNumber(dayOfWeek: Int): Day {
            return when (dayOfWeek) {
                0 -> SUNDAY
                1 -> MONDAY
                2 -> TUESDAY
                3 -> WEDNESDAY
                4 -> THURSDAY
                5 -> FRIDAY
                6 -> SATURDAY
                else -> throw InvalidParameterException("day of week must be in range 0-6")
            }
        }
    }
}