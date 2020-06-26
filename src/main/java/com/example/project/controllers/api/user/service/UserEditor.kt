package com.example.project.controllers.api.user.service

import com.example.project.dto.UserChangeDto
import com.example.project.entity.User
import com.example.project.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserEditor(
        private val userRepository: UserRepository,
        private val passwordEncoder: PasswordEncoder
) {
    fun editUser(username: String, newValues: UserChangeDto): Boolean {
        try {
            edit(username, newValues)
        } catch (e: Exception) {
            println(e.message)
            return false
        }
        return true
    }


    private fun edit(username: String, newValues: UserChangeDto): User? {
        val user = userRepository.findByLogin(username)
        if (!newValues.username.isNullOrEmpty()) {
            if (user.login != newValues.username
                    && userRepository.existsByLogin(newValues.username))
                throw Exception("${newValues.username} = taki login jest w bazie")
            user.login = newValues.username
        }
        if (!newValues.email.isNullOrEmpty()) {
            if (user.email != newValues.email
                    && userRepository.existsByEmail(newValues.email))
                throw Exception("${newValues.email} = taki email jest w bazie")
            if (!EmailValidator().validate(newValues.email))
                throw Exception("${newValues.email} = login zły pattern")
            user.email = newValues.email
        }
        if (!newValues.password.isNullOrEmpty()) {
            user.password = passwordEncoder.encode(newValues.password)
        }
        return userRepository.save(user)
    }
}
