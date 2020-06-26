package com.example.project.controllers.api.airline

import com.example.project.controllers.api.airline.service.AirlineCreator
import com.example.project.controllers.api.airline.service.AirlineDestructor
import com.example.project.controllers.api.airline.service.AirlineEditor
import com.example.project.controllers.api.user.UserApi
import com.example.project.dto.AirlineBasicDto
import com.example.project.dto.AirlineChangeDto
import com.example.project.dto.AirlineNewDto
import com.example.project.entity.Airline
import com.example.project.repository.AirlineRepository
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin
@RequestMapping("/api/airlines")
class AirlineApi(
        private val airlineRepository: AirlineRepository,
        private val airlineDestructor: AirlineDestructor, // brak konstruktora
        private val airlineEditor: AirlineEditor, // może powodować problemy / brak konstruktora
        private val airlineCreator: AirlineCreator // może powodować problemy
) {

    //region GetMapping
    @GetMapping
    fun getAllAirlinesDto() = airlineRepository
            .findAll()
            .map { t -> AirlineBasicDto(t) }

    @GetMapping("/{name}")
    fun getAirlineByNameDto(@PathVariable(name = "name") name: String) =
            getAirlineByName(name)?.let { AirlineBasicDto(it) }

    private fun isNameInDatabase(name: String?) =
            airlineRepository.existsByName(name)

    private fun getAirlineByName(name: String): Airline? =
            airlineRepository.findAirlineByName(name)
    //endregion

    @DeleteMapping("/{name}")
    fun deleteAirline(@PathVariable(name = "name") name: String) =
            airlineDestructor.delete(name)

    @PatchMapping("/{name}")
    fun patchAirline(
            @PathVariable(name = "name") name: String,
            @RequestBody airlineChange: AirlineChangeDto) =
            airlineEditor.edit(name, airlineChange)

    @PostMapping
    fun postNewAirline(@RequestBody newAirline: AirlineNewDto) =
            airlineCreator.create(newAirline)
}
