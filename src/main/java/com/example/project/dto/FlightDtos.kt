package com.example.project.dto

import com.example.project.entity.Flight
import java.time.Instant

class FlightBasicDto(
        private val flight: Flight
) {
    val id: Long = flight.id
    val cost: Double = flight.cost
    val startDate: Instant = flight.startDate
    val endDate: Instant = flight.endDate
    val startAirport: AirportBasicDto = AirportBasicDto(flight.startAirport)
    val endAirport: AirportBasicDto = AirportBasicDto(flight.endAirport)
    val airplane: AirplaneBasicDto = AirplaneBasicDto(flight.airplane)
}

class FlightNewDto(
        val cost: Double,
        // yyyy-mm-dd hh:mm:ss[.fffffffff]
        val startDate: String,
        val endDate: String,
        val startAirport: String,
        val endAirport: String,
        val airplane: Long
)
