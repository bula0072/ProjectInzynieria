package com.example.project.controllers.api.airport.service

import com.example.project.repository.AirportRepository
import org.springframework.stereotype.Service

/**
 * Obsługuje system usuwania portu lotniczego
 */
@Service
class AirportDestructor(
        private val airportRepository: AirportRepository
) {
    /**
     * Usuwa port lotniczy z bazy danych
     * @param name nazwa portu lotniczego
     * @return true w przypadku braku błędów
     */
    fun delete(name: String?): Boolean {
        try {
            val airport = airportRepository.findAirportByName(name)
            airportRepository.delete(airport)
        } catch (e: Exception) {
            println(e.message)
            return false
        }
        return true
    }

    /**
     * Usuwa wrzystkie porty lotnicze z bazy danych
     * @return true w przypadku braku błędów
     */
    fun deleteAll(): Boolean {
        try {
            airportRepository.deleteAll()
        } catch (e: Exception) {
            println(e.message)
            return false
        }
        return true
    }

}
