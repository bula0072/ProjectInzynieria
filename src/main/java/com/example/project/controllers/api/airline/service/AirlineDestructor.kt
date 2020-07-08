package com.example.project.controllers.api.airline.service

import com.example.project.repository.AirlineRepository
import org.springframework.stereotype.Service

/**
 * Obsługuje system uduwania linii lotniczej z bazy
 */
@Service
class AirlineDestructor(
        private val airlineRepository: AirlineRepository
) {
    /**
     * Usuwa konkretną linię
     * @param name nazwa linii lotniczej
     * @return true w przypadku braku błędów
     */
    fun delete(name: String?): Boolean {
        try {
            val airline = airlineRepository.findAirlineByName(name)
            airlineRepository.delete(airline)
        } catch (e: Exception) {
            return false
        }
        return true
    }

    /**
     * Usuwa wszystkie linie
     * @return true w przypadku braku błędów
     */
    fun deleteAll(): Boolean {
        try {
            airlineRepository.deleteAll()
        } catch (e: Exception) {
            return false
        }
        return true
    }
}
