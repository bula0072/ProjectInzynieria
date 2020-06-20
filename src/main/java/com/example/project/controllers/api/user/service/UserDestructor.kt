package com.example.project.controllers.api.user.service

import com.example.project.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserDestructor(
        private val userRepository: UserRepository
) {
    fun deleteAllUser(): Boolean {
        try {
            userRepository.deleteAll()
        } catch (e: Exception) {
            return false
        }
        return true
    }

    fun deleteUserByUsername(username: String): Boolean {
        return when (val user = userRepository.findByLogin(username)) {
            null -> false
            else -> {
                userRepository.delete(user)
                return true
            }
        }
    }


}
