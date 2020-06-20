package com.example.project.controllers.api.airline

import com.example.project.controllers.api.airplane.AirplaneApi
import com.example.project.controllers.api.airport.AirportApi
import com.example.project.dto.FlightBasicDto
import com.example.project.dto.FlightNewDto
import com.example.project.entity.Flight
import com.example.project.repository.FlightRepository
import org.springframework.web.bind.annotation.*
import java.time.Instant

@RestController
@CrossOrigin
@RequestMapping("/api/flights")
class FlightApi(
        private val flightRepository: FlightRepository,
        private val airportApi: AirportApi,
        private val airplaneApi: AirplaneApi
) {

    @GetMapping
    fun getAllFlights() = flightRepository
            .findAll()
            .map { t -> FlightBasicDto(t) }

    @GetMapping("/{id}")
    fun getFlightByIdDto(id: Long) = FlightBasicDto(getFlightById(id) as Flight)

    fun getFlightById(id: Long): Flight? = flightRepository.findFlightsById(id)


    @GetMapping("/airline/{name}")
    fun getAllFlightsByAirline(@PathVariable(name = "name") name: String) =
            flightRepository
                    .findAll()
                    .filter { it.airplane.user.login == name }
                    .map { t -> FlightBasicDto(t) }

    @GetMapping("/startAirport/{name}")
    fun getAllFlightsByStartAirport(@PathVariable(name = "name") name: String) =
            flightRepository
                    .findAll()
                    .filter { it.startAirport.name == name }
                    .map { t -> FlightBasicDto(t) }

    @GetMapping("/endAirport/{name}")
    fun getAllFlightsByEndAirport(@PathVariable(name = "name") name: String) =
            flightRepository
                    .findAll()
                    .filter { it.endAirport.name == name }
                    .map { t -> FlightBasicDto(t) }

    //TODO nie można dodać lotu, jeżeli w podanym czasie dany samolot już posiada lot
    // oraz jeżeli lotniska będą zajęte
    @PostMapping
    fun newFlight(@RequestBody newFlight: FlightNewDto) {
        val flight = Flight(
                newFlight.cost,
                // yyyy-mm-ddThh:mm:ss.00Z  -  T i Z musi być, inaczej się nie sparsuje
                Instant.parse(newFlight.startDate),
                Instant.parse(newFlight.endDate),
                airportApi.getAirportByName(newFlight.startAirport),
                airportApi.getAirportByName(newFlight.endAirport),
                airplaneApi.getAirplaneById(newFlight.airplane)
        )
        flightRepository.save(flight)
    }
}
