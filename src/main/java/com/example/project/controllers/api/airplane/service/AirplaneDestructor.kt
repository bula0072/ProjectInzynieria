package com.example.project.controllers.api.airplane.service

import com.example.project.entity.Airplane
import com.example.project.repository.AirplaneRepository
import org.springframework.stereotype.Service

/**
 * Obsługuje usuwanie samolotu z bazy danych
 */
@Service
class AirplaneDestructor(
        private val airplaneRepository: AirplaneRepository
) {
    /**
     * Usuwa konktretny samolot
     * @param id id samolotu którego użytkownik chce usunąć
     * @return true w przypadku braku błędów
     */
    fun delete(id: Long?): Boolean {
        return when {
            id == null -> false
            !airplaneRepository.existsById(id) -> false
            else -> {
                airplaneRepository.delete(airplaneRepository.findAirplaneById(id) as Airplane)
                true
            }
        }
    }

    /**
     * Usuwa wszystkie samoloty z bazy danych
     * @return true w przypadku braku błędów
     */
    fun deleteAll(): Boolean {
        try {
            airplaneRepository.deleteAll()
        } catch (e: Exception) {
            return false
        }
        return true
    }
}
