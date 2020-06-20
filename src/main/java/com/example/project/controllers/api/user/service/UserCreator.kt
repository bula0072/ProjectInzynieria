package com.example.project.controllers.api.user.service

import com.example.project.dto.UserChangeDto
import com.example.project.entity.User
import com.example.project.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserCreator(
        private val userRepository: UserRepository
) {


    fun createUser(newUser: UserChangeDto): Boolean {
        try {
            create(newUser)
        } catch (e: Exception) {
            println(e.message)
            return false
        }
        return true
    }

    fun create(newUser: UserChangeDto): User {
        if (newUser.email.isNullOrBlank() ||
                !EmailValidator().validate(newUser.email)) throw Exception("${newUser.email} = błąd zły format lub pusty")
        if (newUser.username.isNullOrBlank()) throw Exception("${newUser.username} = błąd pusty")
        if (newUser.password.isNullOrBlank()) throw Exception("${newUser.password} = błąd pusty")
        if (userRepository.existsByLogin(newUser.username)) throw Exception("${newUser.username} = użytkownik już istnieje")
        if (userRepository.existsByEmail(newUser.email)) throw Exception("${newUser.email} = email już istnieje")

        return userRepository.save(User(newUser.username, newUser.password, newUser.email))
    }
}
