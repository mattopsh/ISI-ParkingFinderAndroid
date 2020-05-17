package com.hfad.parkingfinder.utils.validator

import java.util.regex.Pattern

object Validator {

    private const val EMAIL_REGEX = "^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$"
    fun validateEmail(email: String): Boolean {
        return Pattern.compile(EMAIL_REGEX).matcher(email).matches()
    }

    fun validatePassword(password: String): Boolean {
        return password.length in 8..60
    }
}