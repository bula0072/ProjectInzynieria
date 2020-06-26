package com.example.project.controllers.api.user.service

import java.util.regex.Pattern.compile

class EmailValidator {
    fun validate(email: String): Boolean {
        return emailRegex.matcher(email).matches()
    }

    private val emailRegex = compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    )
}
