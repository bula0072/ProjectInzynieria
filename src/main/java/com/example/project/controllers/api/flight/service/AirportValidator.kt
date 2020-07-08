package com.example.project.controllers.api.flight.service

import com.example.project.controllers.api.airport.AirportApi
import com.example.project.dto.FlightBasicDto
import com.example.project.dto.FlightNewDto
import com.example.project.repository.FlightRepository
import org.springframework.stereotype.Service
import java.time.Instant

/**
 * Sprawdza poprawność portów lotniczych w kontekście lotu
 */
@Service
class AirportValidator(
        private val airportApi: AirportApi,
        private val flightRepository: FlightRepository
) {
    /**
     * Sprawdzenie poprawności portów lotniczych
     * @param flight parametry lotu
     * @return true jeżeli porty lotnicze mogą zostać użyte
     */
    fun validate(flight: FlightNewDto): Boolean {
        try {
            combine(flight)
        } catch (e: Exception) {
            println(e.message)
            return false
        }
        return true
    }

    /**
     * Sprawdza lotnisko startowe oraz lotnisko końcowe w aspekcie możliwości ich użycia.
     * Np. jeżeli lotnisko startowe i lotnisko końcowe będzie tym samym lotniskiem, zwróci błąd.
     * @param flight FlightNewDto
     * @return String
     */
    private fun combine(flight: FlightNewDto): String {
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

    /**
     * Sprawdza czy na konkretnym lotnisku (startowym lub końcowym) jest dostępne miejszcze
     * na kolejny samolot.
     * @param flight parametry lotu
     * @param enum wybór które lotnisko ma być sprawdzone (stawrowe czy końcowe)
     * @return ilość znalezionych samolotów będących w wyznaczonym czasie na danym lotnisku
     */
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
