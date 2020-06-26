package com.example.project.controllers.api.flight.service

import com.example.project.controllers.api.airplane.AirplaneApi
import com.example.project.controllers.api.airport.AirportApi
import com.example.project.dto.AirportBasicDto
import com.example.project.dto.FlightNewDto
import org.springframework.stereotype.Service
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

@Service
class PathFinder(
        private val airportApi: AirportApi,
        private val airplaneApi: AirplaneApi
) {
    fun isDistanceCorrect(flight: FlightNewDto): Boolean {
        val airplane = airplaneApi.getAirplaneById(flight.airplane)
        if (airplane != null) {
            return calculateInKm(flight) < airplane.maxDistance
        }
        return false
    }

    private fun calculateDistanceInDegrees(flight: FlightNewDto): Double {
        val startAirportLatitude = (airportApi.getAirportByNameDto(flight.startAirport) as AirportBasicDto).latitude
        val endAirportLatitude = (airportApi.getAirportByNameDto(flight.endAirport) as AirportBasicDto).latitude
        val startAirportLongitude = (airportApi.getAirportByNameDto(flight.startAirport) as AirportBasicDto).longitude
        val endAirportLongitude = (airportApi.getAirportByNameDto(flight.endAirport) as AirportBasicDto).longitude

        return calculateFromLatitudeAndLongitude(
                startAirportLatitude,
                startAirportLongitude,
                endAirportLatitude,
                endAirportLongitude)
    }

    private fun calculateFromLatitudeAndLongitude(
            startLatitude: Double,
            startLongitude: Double,
            endLatitude: Double,
            endLongitude: Double): Double {
        val latitude = if ((startLatitude < 0 && endLatitude < 0)
                || (startLatitude > 0 && endLatitude > 0)) {
            abs(startLatitude - endLatitude)
        } else {
            abs(startLatitude) + abs(endLatitude)
        }

        val longitude = if ((startLongitude < 0 && endLongitude < 0)
                || (startLongitude > 0 && endLongitude > 0)) {
            abs(startLongitude - endLongitude)
        } else if (abs(startLongitude) + abs(endLongitude) <= 180) {
            abs(startLongitude) + abs(endLongitude)
        } else {
            abs(startLongitude) + abs(endLongitude) - 360
        }

        return sqrt(longitude.pow(2.0) + latitude.pow(2.0))
    }

    private fun calculateInKm(flight: FlightNewDto): Double {
        return calculateDistanceInDegrees(flight) * 111.139
    }

    private fun calculateInKm(startLatitude: Double,
                              startLongitude: Double,
                              endLatitude: Double,
                              endLongitude: Double): Double {
        return calculateFromLatitudeAndLongitude(startLatitude,
                startLongitude,
                endLatitude,
                endLongitude) * 111.139
    }
}
