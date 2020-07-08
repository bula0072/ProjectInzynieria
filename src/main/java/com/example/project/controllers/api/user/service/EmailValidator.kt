package com.example.project.controllers.api.user.service

import java.util.regex.Pattern.compile

/**
 * Weryfikowanie porawności emaila
 * @property emailRegex pattern emaila
 */
class EmailValidator {
    private val emailRegex = compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    )

    /**
     * Sprawdzenie poprawności emaila
     * @param email String
     * @return true jeżelu email jest poprawny
     */
    fun validate(email: String): Boolean {
        return emailRegex.matcher(email).matches()
    }

}
