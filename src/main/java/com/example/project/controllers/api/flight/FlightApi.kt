package com.example.project.controllers.api.flight

import com.example.project.controllers.api.flight.service.FlightCreator
import com.example.project.controllers.api.flight.service.FlightDestructor
import com.example.project.dto.FlightBasicDto
import com.example.project.dto.FlightNewDto
import com.example.project.entity.Flight
import com.example.project.repository.FlightRepository
import org.springframework.web.bind.annotation.*
/**
 * Rest Controller obsługujący zapytania pod adres /api/flights
 */
@RestController
@CrossOrigin
@RequestMapping("/api/flights")
class FlightApi(
		private val flightRepository: FlightRepository,
		private val flightCreator: FlightCreator,
		private val flightDestructor: FlightDestructor
) {
	/**
	 * Pobiera dane wszystkich lotów z bazy danych
	 * @return List<FlightBasicDto>
	 */
	@GetMapping
	fun getAllFlights() = flightRepository.findAll()
			.map { t -> FlightBasicDto(t) }

	/**
	 * Pobiera dane konkretnego lotu z bazy
	 * @param id id lotu
	 * @return FlightBasicDto
	 */
	@GetMapping("/{id}")
	fun getFlightByIdDto(@PathVariable(name = "id") id: Long) = FlightBasicDto(getFlightById(id)!!)

	/**
	 * Pobiera dane konkretnego lotu z bazy
	 * @param id id lotu
	 * @return Flight
	 */
	fun getFlightById(id: Long): Flight? = flightRepository.findFlightsById(id)

	/**
	 * Pobiera dane wszystkich lotów konkretnej linii lotniczej
	 * @param name nazwa linii lotniczej
	 * @return List<FlightBasicDto>
	 */
	@GetMapping("/airline/{name}")
	fun getAllFlightsByAirline(@PathVariable(name = "name") name: String) =
			flightRepository
					.findAll()
					.filter { it.airplane.user.login == name }
					.map { t -> FlightBasicDto(t) }

	/**
	 * Pobiera dane wszystkich lotów z konkretnym startowym portem lotniczym
	 * @param name nazwa właściciela portu lotniczego
	 * @return List<FlightBasicDto>
	 */
	@GetMapping("/startAirport/{name}")
	fun getAllFlightsByStartAirport(@PathVariable(name = "name") name: String) =
			flightRepository
					.findAll()
					.filter { it.startAirport.user.login == name }
					.map { t -> FlightBasicDto(t) }

	/**
	 * Pobiera dane wszystkich lotów z konkretnym końcowym portem lotniczym
	 * @param name nazwa właściciela portu lotniczego
	 * @return List<FlightBasicDto>
	 */
	@GetMapping("/endAirport/{name}")
	fun getAllFlightsByEndAirport(@PathVariable(name = "name") name: String) =
			flightRepository
					.findAll()
					.filter { it.endAirport.user.login == name }
					.map { t -> FlightBasicDto(t) }

	/**
	 * Pobiera dane wszystkich lotów, które lądują lub startują w danym porcie lotniczym
	 * @param name nazwa właśicicela portu lotniczego
	 * @return List<FlightBasicDto>
	 */
	@GetMapping("/airport/{name}")
	fun getAllFlightByAirport(@PathVariable(name = "name") name: String) =
			getAllFlightsByStartAirport(name)
					.plus(getAllFlightsByEndAirport(name))
					.sortedBy { t -> t.id }

	/**
	 * Dodaje nowy lot
	 * @param newFlight parametry nowego lotu
	 * @return true w przypadku braku błędów
	 */
	@PostMapping
	fun newFlight(@RequestBody newFlight: FlightNewDto): Boolean =
			flightCreator.create(newFlight)

	/**
	 * Usuwa konkretny lot
	 * @param id id lotu, który ma być usunięty
	 * @return true w przypadku braku błędów
	 */
	@DeleteMapping("/{id}")
	fun deleteFlight(@PathVariable("id") id: Long) =
			flightDestructor.delete(id)
}
