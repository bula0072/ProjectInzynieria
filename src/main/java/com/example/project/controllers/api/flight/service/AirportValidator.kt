package com.example.project.controllers.api.flight.service

import com.example.project.controllers.api.airport.AirportApi
import com.example.project.controllers.api.flight.FlightApi
import com.example.project.dto.FlightBasicDto
import com.example.project.dto.FlightNewDto
import com.example.project.repository.FlightRepository
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class AirportValidator(
        private val airportApi: AirportApi,
        private val flightRepository: FlightRepository
) {
    fun validate(flight: FlightNewDto): Boolean {
        try {
            combineTest(flight)
        } catch (e: Exception) {
            println(e.message)
            return false
        }
        return true
    }

    private fun combineTest(flight: FlightNewDto): String {
        Instant.parse(flight.startDate)
        Instant.parse(flight.endDate)

        if (flight.startAirport == flight.endAirport)
            throw Exception("start airport == end airport")
        if (flight.startDate!! >= flight.endDate!!)
            throw Exception("start date >= end date")
        if (flightCounter(flight,
                        StartOrEndAirport.Start) >= airportApi.getAirportByNameDto(flight.startAirport)?.capacity!!)
            throw Exception("start airport oblężone!")
        if (flightCounter(flight,
                        StartOrEndAirport.End) >= airportApi.getAirportByNameDto(flight.endAirport)?.capacity!!)
            throw Exception("end airport oblężone!")

        return "start airport counter = ${flightCounter(flight, StartOrEndAirport.Start)}" +
                "\n end airport counter = ${flightCounter(flight, StartOrEndAirport.End)}"
    }

    private fun flightCounter(flight: FlightNewDto, enum: StartOrEndAirport): Int {
        val airport = when (enum) {
            StartOrEndAirport.Start -> airportApi.getAirportByNameDto(flight.startAirport)
            StartOrEndAirport.End -> airportApi.getAirportByNameDto(flight.endAirport)
        }
        if (airport != null) {
            val allFlights = flightRepository.findAll()
                    .map { t -> FlightBasicDto(t) }.filter {
                ((it.startAirport.name == airport.name)
                        && ((it.startDate.epochSecond <= (Instant.parse(flight.endDate).epochSecond + 1800))
                        && (it.startDate.epochSecond >= (Instant.parse(flight.endDate).epochSecond - 1800)))
                        || ((it.endDate.epochSecond <= (Instant.parse(flight.endDate).epochSecond + 1800))
                        && (it.startDate.epochSecond >= (Instant.parse(flight.endDate).epochSecond - 1800))))
                        || ((it.endAirport.name == airport.name)
                        && ((it.startDate.epochSecond <= (Instant.parse(flight.endDate).epochSecond + 1800)
                        && it.startDate.epochSecond >= (Instant.parse(flight.endDate).epochSecond - 1800))
                        || (it.endDate.epochSecond <= (Instant.parse(flight.endDate).epochSecond + 1800)
                        && it.endDate.epochSecond >= (Instant.parse(flight.endDate).epochSecond - 1800))))
            }
            return allFlights.size
        }
        throw Exception("airport is null")
    }
}
