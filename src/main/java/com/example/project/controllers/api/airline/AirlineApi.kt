package com.example.project.controllers.api.airline

import com.example.project.controllers.api.airline.service.AirlineCreator
import com.example.project.controllers.api.airline.service.AirlineDestructor
import com.example.project.controllers.api.airline.service.AirlineEditor
import com.example.project.controllers.api.user.UserApi
import com.example.project.dto.AirlineBasicDto
import com.example.project.dto.AirlineChangeDto
import com.example.project.dto.AirlineNewDto
import com.example.project.dto.AirportBasicDto
import com.example.project.entity.Airline
import com.example.project.repository.AirlineRepository
import org.springframework.web.bind.annotation.*

/**
 * Rest Controller obsługujący zapytania pod adres /api/airlines
 */
@RestController
@CrossOrigin
@RequestMapping("/api/airlines")
class AirlineApi(
        private val airlineRepository: AirlineRepository,
        private val airlineDestructor: AirlineDestructor,
        private val airlineEditor: AirlineEditor,
        private val airlineCreator: AirlineCreator
) {
    /**
     * Pobiera z bazy listę wszystkich linii lotniczych
     * @return List<AirlineBasicDto>
     */
    @GetMapping
    fun getAllAirlinesDto() = airlineRepository
            .findAll()
            .map { t -> AirlineBasicDto(t) }

    /**
     * Pobiera bazy linię lotniczą przypisaną do konkretnego użytkownika
     * @param name nazwa użytkownika (właściciela linii lotniczej)
     * @return AirlineBasicDto
     */
    @GetMapping("/user/{name}")
    fun getAirlineByUsernameDto(@PathVariable(name = "name")name: String): AirlineBasicDto? {
        val airline = airlineRepository.findAll().filter { t -> t.user.login == name }.getOrNull(0)
        return if(airline == null) null
        else AirlineBasicDto(airline)
    }

    /**
     * Pobiera z bazy linię lotniczą o podanej nazwie
     * @param name nazwa linii lotniczej
     * @return AirlineBasicDto
     */
    @GetMapping("/{name}")
    fun getAirlineByNameDto(@PathVariable(name = "name") name: String) =
            getAirlineByName(name)?.let { AirlineBasicDto(it) }

    /**
     * Pobiera z bazy linię lotniczą o podanej nazwie
     * @param name nazwa linii lotniczej
     * @return Airline
     */
    private fun getAirlineByName(name: String): Airline? =
            airlineRepository.findAirlineByName(name)

    /**
     * Usuwa linię lotniczą o podanej nazwie
     * @param name nazwa linii lotniczej
     * @return true w przypadku braku błędów
     */
    @DeleteMapping("/{name}")
    fun deleteAirline(@PathVariable(name = "name") name: String) =
            airlineDestructor.delete(name)

    /**
     * Edytuje dane linii lotniczej
     * @param name stara nazwa linii lotniczej
     * @param airlineChange nowa nazwa linii lotniczej
     * @return true w przypadku braku błędów
     */
    @PatchMapping("/{name}")
    fun patchAirline(
            @PathVariable(name = "name") name: String,
            @RequestBody airlineChange: AirlineChangeDto) =
            airlineEditor.edit(name, airlineChange)

    /**
     * Dodaje nową linię lotniczą
     * @param newAirline nazwa linii lotniczej
     * @return true w przypadku braku błędów
     */
    @PostMapping
    fun postNewAirline(@RequestBody newAirline: AirlineNewDto) =
            airlineCreator.create(newAirline)
}
