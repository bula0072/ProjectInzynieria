package com.example.project.controllers.api.airplane

import com.example.project.controllers.api.airplane.service.AirplaneCreator
import com.example.project.controllers.api.airplane.service.AirplaneDestructor
import com.example.project.dto.AirplaneBasicDto
import com.example.project.dto.AirplaneNewDto
import com.example.project.entity.Airplane
import com.example.project.repository.AirplaneRepository
import org.springframework.web.bind.annotation.*

/**
 * Rest Controller obsługujący zapytania pod adres /api/airplanes
 */
@RestController
@CrossOrigin
@RequestMapping("/api/airplanes")
class AirplaneApi(
		private val airplaneRepository: AirplaneRepository,
		private val airplaneDestructor: AirplaneDestructor,
		private val airplaneCreator: AirplaneCreator
) {
	/**
	 * Pobiera listę wszystkich samolotów z bazy danych
	 * @return List<AirplaneBasicDto>
	 */
	@GetMapping
	fun getAllAirplanesDto() = airplaneRepository
			.findAll()
			.map { t -> AirplaneBasicDto(t) }

	/**
	 * Pobiera jeden samolot
	 * @param id id samolotu
	 * @return AirplaneBasicDto
	 */
	@GetMapping("/{id}")
	fun getAirplaneByIdDto(@PathVariable(name = "id") id: Long) =
			getAirplaneById(id)?.let { AirplaneBasicDto(it) }

	/**
	 * Pobiera listę wszystkich samolotów należących do konkretnego użytkownika
	 * @param username nazwa użytkownika, którego samoloty mają być pobrane z bazy
	 * @return List<AirplaneBasicDto>
	 */
	@GetMapping("/user/{username}")
	fun getAirplanesByUsernameDto(@PathVariable(name = "username") username: String) =
			airplaneRepository
					.findAllByUser_Login(username)
					.map { t -> AirplaneBasicDto(t) }

	/**
	 * Usuwa samolot
	 * @param id id samolotu, który ma być usunięty
	 * @return true w przypadku braku błędów
	 */
	@DeleteMapping("/{id}")
	fun deleteAirplane(@PathVariable(name = "id") id: Long): Boolean =
			airplaneDestructor.delete(id)

	/**
	 * Dodaje nowy samolot do bazy danych
	 * @param newAirplane dame samolotu
	 * @return true w przypadku braku błędów
	 */
	@PostMapping
	fun postNewAirplane(@RequestBody newAirplane: AirplaneNewDto): Boolean =
			airplaneCreator.create(newAirplane)

	/**
	 * Pobiera listę samolotów
	 * @return MutableList<Airplane>
	 */
	fun getAllAirplanes(): MutableList<Airplane> = airplaneRepository.findAll()

	/**
	 * Pobiera samolot o podanym id
	 * @param id id samolotu, który ma być pobrany
	 * @return Airplane
	 */
	fun getAirplaneById(id: Long): Airplane? =
			airplaneRepository.findAirplaneById(id)
}
