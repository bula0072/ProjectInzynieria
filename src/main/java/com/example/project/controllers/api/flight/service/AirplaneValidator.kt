package com.example.project.controllers.api.flight.service

import com.example.project.controllers.api.airplane.AirplaneApi
import com.example.project.dto.FlightBasicDto
import com.example.project.dto.FlightNewDto
import com.example.project.repository.FlightRepository
import org.springframework.stereotype.Service
import java.time.Instant

/**
 * Sprawdzenie czy dany samolot może być w danym czasie użyty
 */
@Service
class AirplaneValidator(
        private val airplaneApi: AirplaneApi,
        private val flightRepository: FlightRepository
) {
    /**
     * Sprawdza możliwość użycia samolotu
     * @param flight parametry lotu
     * @return true, jeżeli samolot może zostać użyty
     */
    fun validate(flight: FlightNewDto) =
            howManyFlightsThisPlaneHasAtThatMoment(flight) == 0

    /**
     * Sprawdza ile razy w danym czasie używany jest samolot
     * @param flight paramtry lotu
     * @return ilośc lotów, które w tym czasie używają wybrany samolot
     */
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
