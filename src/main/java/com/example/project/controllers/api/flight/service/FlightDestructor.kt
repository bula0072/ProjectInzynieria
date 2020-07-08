package com.example.project.controllers.api.flight.service

import com.example.project.dto.FlightBasicDto
import com.example.project.repository.FlightRepository
import org.springframework.stereotype.Service
import java.time.Instant

/**
 * Obsługuje system usuwania lotów z bazy danych
 */
@Service
class FlightDestructor(
        private val flightRepository: FlightRepository
) {
    /**
     * Usuwa lot z bazy danych
     * @param id id lotu, który ma być usunięty
     * @return true w przypadku braku błędów
     */
    fun delete(id: Long): Boolean {
        try {
            val flight = FlightBasicDto(flightRepository.findFlightsById(id))
            if (validate(flight)) flightRepository.deleteById(id)
        } catch (e: Exception) {
            return false
        }
        return true
    }

    /**
     * Usuwa wszystkie loty z bazy danych
     * @return true w przypadku braku błędów
     */
    fun deleteAll(): Boolean {
        try {
            flightRepository.deleteAll()
        } catch (e: Exception) {
            return false
        }
        return true
    }

    /**
     * Sprawdza, czy lot można usunąć. Jeżeli lot jest obecnie
     * wykonywany (+-30minut) to nie ma możliwości usunięcia go
     * @param flightDto parametry lotu
     * @return true jeżel można usunąć lot
     */
    private fun validate(flightDto: FlightBasicDto): Boolean {
        val currentTime = Instant.now()
        if (currentTime.isAfter(flightDto.startDate.minusSeconds(1800))
                && currentTime.isBefore(flightDto.endDate.plusSeconds(1800)))
            return false
        return true
    }
}

