package com.example.project.controllers.api.user.service

import com.example.project.repository.UserRepository
import org.springframework.stereotype.Service

/**
 * Usunięcie użytkownika
 */
@Service
class UserDestructor(
		private val userRepository: UserRepository
) {
	/**
	 * Usunięcie wszystkich użytkowników
	 * @return true jeżeli użytkownik został usunięty poprawnie
	 */
	fun deleteAllUser(): Boolean {
		try {
			userRepository.deleteAll()
		} catch (e: Exception) {
			return false
		}
		return true
	}

	/**
	 * Usunięcie konkretnego użytkownika
	 * @param username nazwa użytkownika
	 * @return true jeżeli użytkownik został usunięty poprawnie
	 */
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
