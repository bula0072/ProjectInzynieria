package com.example.project.controllers.api.flight.service

import com.example.project.controllers.api.airplane.AirplaneApi
import com.example.project.dto.FlightBasicDto
import com.example.project.dto.FlightNewDto
import com.example.project.repository.FlightRepository
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class AirplaneValidator(
        private val airplaneApi: AirplaneApi,
        private val flightRepository: FlightRepository
) {
    fun validate(flight: FlightNewDto) =
            howManyFlightsThisPlaneHasAtThatMoment(flight) == 0

    private fun howManyFlightsThisPlaneHasAtThatMoment(flight: FlightNewDto): Int {
        val plane = airplaneApi.getAirplaneByIdDto(flight.airplane)
        if (plane != null) {
            val allFlight = flightRepository.findAll()
                    .map { t -> FlightBasicDto(t) }.filter {
                        (it.airplane.id == plane.id //loty z tym samolotem
                                && ((it.startDate.epochSecond > Instant.parse(flight.startDate).epochSecond - 1800
                                && it.startDate.epochSecond < Instant.parse(flight.startDate).epochSecond + 1800) //it.startDate był w przedziale flight.startDate +/- 30 minut
                                || (it.endDate.epochSecond > Instant.parse(flight.startDate).epochSecond - 1800
                                && it.endDate.epochSecond < Instant.parse(flight.startDate).epochSecond + 1800) //it.endDate był w przedziale flight.startDate +/- 30 minut
                                || (it.startDate.epochSecond > Instant.parse(flight.endDate).epochSecond - 1800
                                && it.startDate.epochSecond < Instant.parse(flight.endDate).epochSecond + 1800) //it.startDate był w przedziale flight.endDate +/- 30 minut
                                || (it.endDate.epochSecond > Instant.parse(flight.endDate).epochSecond - 1800
                                && it.endDate.epochSecond < Instant.parse(flight.endDate).epochSecond + 1800))) //it.endDate był w przedziale flight.endDate +/- 30 minut
                    }
            return allFlight.size
        }
        throw Exception("brak takiego samolotu")
    }
}
