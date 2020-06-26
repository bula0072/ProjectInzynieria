package com.example.project.controllers.api.flight.service

import com.example.project.controllers.api.airplane.AirplaneApi
import com.example.project.controllers.api.airport.AirportApi
import com.example.project.dto.FlightNewDto
import com.example.project.entity.Flight
import com.example.project.repository.FlightRepository
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class FlightCreator(
        private val airportValidator: AirportValidator,
        private val airplaneValidator: AirplaneValidator,
        private val pathFinder: PathFinder,
        private val flightRepository: FlightRepository,
        private val airportApi: AirportApi,
        private val airplaneApi: AirplaneApi
) {
    fun create(flight: FlightNewDto): Boolean {
        try {
            creator(flight)
        } catch (e: Exception) {
            println(e.message)
            return false
        }
        return true
    }

    private fun creator(flight: FlightNewDto): Flight {
        if(flight.cost == null) throw Exception("Koszt jest null")
        if (flight.cost < 0) throw Exception("Koszt mniejszy od zera")
        if (!airportValidator.validate(flight)) throw Exception("Nie można wykożystać podanego portu")
        if (!airplaneValidator.validate(flight)) throw Exception("Ten samolot ma wtedy inny lot")
        if (!pathFinder.isDistanceCorrect(flight)) throw Exception("Samolot tyle nie poleci")
        val newFlight = Flight(
                flight.cost,
                Instant.parse(flight.startDate!!),
                Instant.parse(flight.endDate),
                airportApi.getAirportByName(flight.startAirport),
                airportApi.getAirportByName(flight.endAirport),
                airplaneApi.getAirplaneById(flight.airplane)
        )
        return flightRepository.save(newFlight)
    }
}
