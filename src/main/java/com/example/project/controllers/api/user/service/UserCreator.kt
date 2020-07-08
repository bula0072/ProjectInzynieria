package com.example.project.controllers.api.user.service

import com.example.project.dto.UserChangeDto
import com.example.project.entity.User
import com.example.project.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

/**
 * Dodawanie użytkownika do bazy danych
 */
@Service
class UserCreator(
        private val userRepository: UserRepository,
        private val passwordEncoder: PasswordEncoder
) {

    /**
     * Dodanie nowego użytkownika do bazy danych
     * @param newUser dane nowego użytkownika
     * @return true jeżeli użytkownik został dodany poprawny
     */
    fun createUser(newUser: UserChangeDto): Boolean {
        try {
            create(newUser)
        } catch (e: Exception) {
            println(e.message)
            return false
        }
        return true
    }

    /**
     * Sprawdza poprawność danych wysłanych przez formularz rejstracyjny.
     * Jeżeli dane będą poprawne, zostanie dodany nowy użytkownik do bazy danych.
     * Jeżeli dane są niepoprawne, wyrzucany jest odpowiedni błąd
     * @param newUser dane nowego użytkownika
     * @return User
     */
    fun create(newUser: UserChangeDto): User {
        if (newUser.email.isNullOrBlank() ||
                !EmailValidator().validate(newUser.email)) throw Exception("${newUser.email} = błąd zły format lub pusty")
        if (newUser.username.isNullOrBlank()) throw Exception("${newUser.username} = błąd pusty")
        if (newUser.password.isNullOrBlank()) throw Exception("${newUser.password} = błąd pusty")
        if (userRepository.existsByLogin(newUser.username)) throw Exception("${newUser.username} = użytkownik już istnieje")
        if (userRepository.existsByEmail(newUser.email)) throw Exception("${newUser.email} = email już istnieje")

        return userRepository.save(User(newUser.username, passwordEncoder.encode(newUser.password), newUser.email))
    }
}
