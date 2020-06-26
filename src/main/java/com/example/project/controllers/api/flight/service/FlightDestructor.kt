package com.example.project.controllers.api.flight.service

import com.example.project.dto.FlightBasicDto
import com.example.project.repository.FlightRepository
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class FlightDestructor(
        private val flightRepository: FlightRepository
) {
    fun delete(id: Long): Boolean {
        try {
            val flight = FlightBasicDto(flightRepository.findFlightsById(id))
            if (validate(flight)) flightRepository.deleteById(id)
        } catch (e: Exception) {
            return false
        }
        return true
    }

    fun deleteAll(): Boolean {
        try {
            flightRepository.deleteAll()
        } catch (e: Exception) {
            return false
        }
        return true
    }

    private fun validate(flightDto: FlightBasicDto): Boolean {
        val currentTime = Instant.now()
        if (currentTime.isAfter(flightDto.startDate.minusSeconds(1800))
                && currentTime.isBefore(flightDto.endDate.plusSeconds(1800)))
            return false
        return true
    }
}

