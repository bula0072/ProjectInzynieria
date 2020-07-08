package com.example.project.controllers.api.airport

import com.example.project.controllers.api.airport.service.AirportCreator
import com.example.project.controllers.api.airport.service.AirportDestructor
import com.example.project.controllers.api.airport.service.AirportEditor
import com.example.project.dto.AirportBasicDto
import com.example.project.dto.AirportChangeDto
import com.example.project.dto.AirportNewDto
import com.example.project.entity.Airport
import com.example.project.repository.AirportRepository
import org.springframework.web.bind.annotation.*

/**
 * Rest Controller obsługujący zapytania pod adres /api/airports
 */
@RestController
@CrossOrigin
@RequestMapping("/api/airports")
class AirportApi(
		private val airportRepository: AirportRepository,
		private val airportCreator: AirportCreator,
		private val airportDestructor: AirportDestructor,
		private val airportEditor: AirportEditor
) {
	/**
	 * Pobiera dane wszystkich portów lotniczych z bazy danych
	 * @return List<AirportBasicDto>
	 */
	@GetMapping
	fun getAllAirportsDto() = airportRepository
			.findAll()
			.map { t -> AirportBasicDto(t) }

	/**
	 * Pobiera dane portu lotniczego o podanej nazwie
	 * @param name nazwa portu lotniczego
	 * @return AirportBasicDto
	 */
	@GetMapping("/{name}")
	fun getAirportByNameDto(@PathVariable(name = "name") name: String?) =
			getAirportByName(name)?.let { AirportBasicDto(it) }

	/**
	 * Pobiera dane portu lotniczego przypisanego do podanego użytkownika
	 * @param name nazwa użytkowniak do którego przypisany jest port lotniczy
	 * @return AirportBasicDto
	 */
	@GetMapping("/user/{name}")
	fun getAirportByUsernameDto(@PathVariable(name = "name") name: String): AirportBasicDto? {
		val airport = airportRepository.findAll().filter { t -> t.user.login == name }.getOrNull(0)
		return if (airport == null) null
		else AirportBasicDto(airport)
	}

	/**
	 * Pobiera dane portu lotniczego
	 * @param name nazwa portu lotniczego
	 * @return Airport
	 */
	fun getAirportByName(name: String?): Airport? =
			airportRepository.findByName(name)

	/**
	 * Usuwa port lotniczy z bazy danych
	 * @param name nazwa portu lotniczego
	 * @return true w przypadku braku błędów
	 */
	@DeleteMapping("/{name}")
	fun deleteAirport(@PathVariable(name = "name") name: String) =
			airportDestructor.delete(name)

	/**
	 * Edytuje dane portu lotniczego
	 * @param name stara nazwa portu lotniczego
	 * @param airportChange nowe dane portu lotniczego
	 * @return true w przypadku braku błędów
	 */
	@PatchMapping("/{name}")
	fun patchAirport(
			@PathVariable(name = "name") name: String,
			@RequestBody airportChange: AirportChangeDto
	) = airportEditor.edit(name, airportChange)

	/**
	 * Dodaje nowy port lotniczy do bazy danych
	 * @param newAirport dane nowego portu lotniczego
	 * @return true w przypadku braku błędów
	 */
	@PostMapping
	fun postNewAirport(@RequestBody newAirport: AirportNewDto) =
			airportCreator.create(newAirport)

	/**
	 * Zmienia wartość <code>active</code> portu lotniczego z true na false i odwrotnie
	 * @param name nazwa portu lotniczego
	 * @return true w przypadku braku błędów
	 */
	@PostMapping("/set/{name}")
	fun setActive(@PathVariable(name = "name") name: String) =
			airportEditor.setActivity(name)
}
