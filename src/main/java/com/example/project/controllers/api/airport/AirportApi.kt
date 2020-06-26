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

@RestController
@CrossOrigin
@RequestMapping("/api/airports")
class AirportApi(
        private val airportRepository: AirportRepository,
        private val airportCreator: AirportCreator,
        private val airportDestructor: AirportDestructor,
        private val airportEditor: AirportEditor
) {
    //region GetMapping
    @GetMapping
    fun getAllAirportsDto() = airportRepository
            .findAll()
            .map { t -> AirportBasicDto(t) }

    @GetMapping("/{name}")
    fun getAirportByNameDto(@PathVariable(name = "name") name: String?) =
            getAirportByName(name)?.let { AirportBasicDto(it) }

    @GetMapping("/user/{name}")
    fun getAirportByUsernameDto(@PathVariable(name = "name")name: String): AirportBasicDto? {
        val airport = airportRepository.findAll().filter { t -> t.user.login == name }.getOrNull(0)
        return if(airport == null) null
        else AirportBasicDto(airport)
    }


    fun getAirportByName(name: String?): Airport? =
            airportRepository.findByName(name)
    //endregion

    @DeleteMapping("/{name}")
    fun deleteAirport(@PathVariable(name = "name") name: String) =
            airportDestructor.delete(name)

    @PatchMapping("/{name}")
    fun patchAirport(
            @PathVariable(name = "name") name: String,
            @RequestBody airportChange: AirportChangeDto
    ) = airportEditor.edit(name, airportChange)

    @PostMapping
    fun postNewAirport(@RequestBody newAirport: AirportNewDto) =
            airportCreator.create(newAirport)

    @PostMapping("/set/{name}")
    fun setActive(@PathVariable(name = "name") name: String) =
            airportEditor.setActivity(name)

}
