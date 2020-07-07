package com.example.project.controllers.api.flight

import com.example.project.controllers.api.flight.service.FlightCreator
import com.example.project.controllers.api.flight.service.FlightDestructor
import com.example.project.dto.FlightBasicDto
import com.example.project.dto.FlightNewDto
import com.example.project.entity.Flight
import com.example.project.repository.FlightRepository
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin
@RequestMapping("/api/flights")
class FlightApi(
		private val flightRepository: FlightRepository,
		private val flightCreator: FlightCreator,
		private val flightDestructor: FlightDestructor
) {

	@GetMapping
	fun getAllFlights() = flightRepository.findAll()
			.map { t -> FlightBasicDto(t) }

	@GetMapping("/{id}")
	fun getFlightByIdDto(@PathVariable(name = "id") id: Long) = FlightBasicDto(getFlightById(id)!!)

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
					.filter { it.startAirport.user.login == name }
					.map { t -> FlightBasicDto(t) }

	@GetMapping("/endAirport/{name}")
	fun getAllFlightsByEndAirport(@PathVariable(name = "name") name: String) =
			flightRepository
					.findAll()
					.filter { it.endAirport.user.login == name }
					.map { t -> FlightBasicDto(t) }

	@GetMapping("/airport/{name}")
	fun getAllFlightByAirport(@PathVariable(name = "name") name: String) =
			getAllFlightsByStartAirport(name)
					.plus(getAllFlightsByEndAirport(name))
					.sortedBy { t -> t.id }

	@PostMapping
	fun newFlight(@RequestBody newFlight: FlightNewDto): Boolean =
			flightCreator.create(newFlight)

	@DeleteMapping("/{id}")
	fun deleteFlight(@PathVariable("id") id: Long) =
			flightDestructor.delete(id)
}
